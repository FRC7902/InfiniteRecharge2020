/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj2.command.CommandBase;
// import edu.wpi.first.wpilibj2.command.CommandScheduler;
// import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
// import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
// import edu.wpi.first.wpilibj2.command.WaitCommand;
// import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.AutonomousSequence;
import frc.robot.commands.Deployment;
import frc.robot.commands.DumpLoad;
import frc.robot.commands.ShootHigh;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  public static DriveSubsystem driveSubsystem = new DriveSubsystem();
  public static IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
  public static ShootSubsystem shootSubsystem = new ShootSubsystem();
  public static StorageSubsystem storageSubsystem = new StorageSubsystem();
  // public static ClimbSubsystem climbSubsystem = new ClimbSubsystem();

  // Declaring Joysticks
  private static XboxController driverStick = new XboxController(Constants.JOY),
                                operatorStick = new XboxController(Constants.OP);

  // Autonomous Routine
  private final Command forward = new AutonomousSequence(driveSubsystem, intakeSubsystem, shootSubsystem, storageSubsystem);
  private final Command intake = new Deployment(driveSubsystem, intakeSubsystem, storageSubsystem);
  private final Command low = new DumpLoad(driveSubsystem, storageSubsystem, shootSubsystem);
  private final Command high = new ShootHigh(shootSubsystem, storageSubsystem, driveSubsystem);

  // Auto Chooser
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  // Timer
  private Timer time = new Timer();
  
  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    // Default Drive Sub CMD
    driveSubsystem.setDefaultCommand(
      //The Arcade command
      new RunCommand(() -> 
        driveSubsystem.driveJoystick(
          -driverStick.getRawAxis(Constants.LY)*Constants.Drive.kDriveSpeed*Constants.Drive.kLimit, 
          driverStick.getRawAxis(Constants.RX)*Constants.Drive.kTurnSpeed*Constants.Drive.kLimit), 
        driveSubsystem
      )
    );

    // Default Climb Sub CMD
    // climbSubsystem.setDefaultCommand(
    //   //The Climb command
    //   new RunCommand(() ->
    //     // Rise & Fall using Left Y Axis
    //     climbSubsystem.climb(operatorStick.getRawAxis(Constants.LY)), 
    //     climbSubsystem
    //   )
    // );

    //Add Commands to the autonomous command chooser
    m_chooser.setDefaultOption("Forward", forward);
    m_chooser.addOption("Intake", intake);
    m_chooser.addOption("Low Port", low);
    m_chooser.addOption("High Port", high);
    
    //Put the Chooser on Dashboard
    SmartDashboard.putData(m_chooser);
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    // Right Bumper on Driver Stick ~ Limit Speed
    new JoystickButton(driverStick, Constants.RB)
      .whenPressed(() -> driveSubsystem.setMax(Constants.Drive.kDriveLimit))
      .whenReleased(() -> driveSubsystem.setMax(1));

    // Left Bumper on Operator Stick ~ Sucks Balls
    new JoystickButton(operatorStick, Constants.LB)
      .whenPressed(() -> {
        intakeSubsystem.suck();
        storageSubsystem.storeIntake();
        driveSubsystem.setMaxOutput(Constants.Drive.kIntakeDriveSpeed);
      }, intakeSubsystem)
      .whenReleased(() -> {
        intakeSubsystem.stop();
        storageSubsystem.stop();
        driveSubsystem.setMaxOutput(1);
      });

    // Right Bumper on Operator Stick, Shoot Balls
    new JoystickButton(operatorStick, Constants.RB)
      .whenPressed(() -> {
        // Reverse Everything
        shootSubsystem.reverse();
        shootSubsystem.reverseTransfer();
        storageSubsystem.reverse();
        // Wait 0.5 second
        time.start();
        while(time.get() <= 0.5);
        // Prep
        shootSubsystem.stopTransfer();
        storageSubsystem.stop();
        shootSubsystem.shoot();
        // Wait
        while(!shootSubsystem.isPowered());
        // Shoot
        shootSubsystem.transfer();
        storageSubsystem.store();
      })
      .whenReleased(() -> {
        storageSubsystem.stop();
        shootSubsystem.stap();
        intakeSubsystem.stop();
        time.stop();
        time.reset();
      });

    // B on Operator Stick ~ Reserve All
    new JoystickButton(operatorStick, Constants.B)
    .whenPressed(() -> {
      shootSubsystem.reverse();
      shootSubsystem.reverseTransfer();
      storageSubsystem.reverse();
    })
    .whenReleased(() -> {
      shootSubsystem.stap();
      storageSubsystem.stop();
    });

    // Y on Operator Stick ~ Dumps Load
    new JoystickButton(operatorStick,Constants.Y)
    .whenPressed(() -> {
      shootSubsystem.dumpShoot();
      shootSubsystem.dumpTransfer();
      storageSubsystem.store();
    })
    .whenReleased(() -> {
      shootSubsystem.stap();
      storageSubsystem.stop();
    });

    // //When X is pressed, deploy Intake 
    // new JoystickButton(operatorStick, Constants.X)
    //   .toggleWhenPressed(new CommandBase() {
    //     // When CMD is running, deploy
    //     @Override
    //     public void execute() {
    //       intakeSubsystem.deploy();
    //     }   
    //     // When interrupted (Toggled off) retract
    //     @Override
    //     public void end(boolean interrupted) {
    //       intakeSubsystem.retract();
    //     }
    //   // true = yes please interrupt
    //   }, true);

    //When Y is pressed, deploy colour arm
    // new JoystickButton(operatorStick, Constants.Y)
    //   .toggleWhenPressed(new CommandBase() {
    //     // When CMD is running, deploy
    //     @Override
    //     public void execute() {
    //       colourSubsystem.rise();
    //     }   
    //     // When interrupted (Toggled off) retract
    //     @Override
    //     public void end(boolean interrupted) {
    //       colourSubsystem.fall();
    //     }
    //   // true = yes please interrupt
    //   }, true);

    //When A is pressed, invert intake
    new JoystickButton(operatorStick, Constants.A)
      .whenPressed(() -> 
        intakeSubsystem.reverse()
      )
      .whenReleased(() ->
        intakeSubsystem.normal()
      );

    // When press menu stART  traners
    new JoystickButton(operatorStick, Constants.M)
      .whenPressed(() -> shootSubsystem.transfer(), shootSubsystem)
      .whenReleased(() -> shootSubsystem.stopTransfer(), shootSubsystem);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_chooser.getSelected();
  }
}