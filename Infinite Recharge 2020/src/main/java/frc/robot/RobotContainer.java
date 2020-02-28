/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.commands.AutonomousSequence;
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
  public static ColourSubsystem colourSubsystem = new ColourSubsystem();
  public static ShootSubsystem shootSubsystem = new ShootSubsystem();
  public static StorageSubsystem storageSubsystem = new StorageSubsystem();
  public static ClimbSubsystem climbSubsystem = new ClimbSubsystem();

  //Declaring Joysticks
  private static XboxController driverStick = new XboxController(Constants.JOY),
                          operatorStick = new XboxController(Constants.OP);

  //Autonomous Routine
  private final Command autonomousSequence = new AutonomousSequence(driveSubsystem, intakeSubsystem, shootSubsystem, storageSubsystem);
  SendableChooser<Command> m_chooser = new SendableChooser<>();
  //Chooser for Colours
  SendableChooser<Color> colorChooser = new SendableChooser<>();
  
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
    climbSubsystem.setDefaultCommand(
      //The Climb command
      new RunCommand(() ->
        // Rise & Fall using Left Y Axis
        climbSubsystem.climb(operatorStick.getRawAxis(Constants.LY)), 
        climbSubsystem
      )
    );

    //Add Commands to the autonomous command chooser
    m_chooser.setDefaultOption("The Auto", autonomousSequence);
    //Add Colors to chooser
    colorChooser.addOption("Red", colourSubsystem.kRedTarget);
    colorChooser.addOption("Yellow", colourSubsystem.kYellowTarget);
    colorChooser.addOption("Green", colourSubsystem.kGreenTarget);
    colorChooser.addOption("Blue", colourSubsystem.kBlueTarget);

    //Put the Chooser on Dashboard
    Shuffleboard.getTab("Autonomous").add(m_chooser);
    Shuffleboard.getTab("SmartDashboard").add(colorChooser);
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    //When Left Bumper is pressed, Sucks Stuff
    new JoystickButton(operatorStick, Constants.LB)
      .whenPressed(() -> {
        intakeSubsystem.suck();
        storageSubsystem.store();
      }, intakeSubsystem)
      .whenReleased(() -> {
        intakeSubsystem.stop();
        storageSubsystem.stop();
      });

    //When Right Bumper is pressed, shoot stuff
    new JoystickButton(operatorStick, Constants.RB)
      .whenPressed(
        // Creates a new sequence
        new SequentialCommandGroup(
          // Runs the reversal of everything in parallel
          new ParallelCommandGroup(
            new InstantCommand(
              () -> shootSubsystem.reverse(), shootSubsystem
            ),
            new InstantCommand(
              () -> shootSubsystem.reverseTransfer(), shootSubsystem
            ),
            new InstantCommand(
              () -> storageSubsystem.reverse(), storageSubsystem
            )
          ),
          // Waits 0.5 Seconds
          new WaitCommand(Constants.Shooter.kTimeout),
          // Starts preparations
          new ParallelCommandGroup(
            new InstantCommand(
              () -> storageSubsystem.stop(), storageSubsystem
            ),
            new InstantCommand(
              () -> shootSubsystem.stopTransfer(), shootSubsystem
            ),
            new InstantCommand(
              () -> shootSubsystem.shoot(), shootSubsystem
            )
          ),
          // Wait Until Shooter is Full Power
          new WaitUntilCommand(() -> shootSubsystem.isPowered()),
          // Activate
          new ParallelCommandGroup(
            new InstantCommand(
              () -> shootSubsystem.transfer(), shootSubsystem
            ),
            new InstantCommand(
              () -> storageSubsystem.store(), storageSubsystem
            )
          )
      ), true)
      .whenReleased(() -> {
        // Stop everything when released
        storageSubsystem.stop();
        shootSubsystem.stap();
      });

    //When X is pressed, deploy Intake 
    // TODO Check if this works. I dont wanna make another cmd file just for this small thing
    new JoystickButton(operatorStick, Constants.X)
      .toggleWhenPressed(new CommandBase() {
        // When CMD is running, deploy
        @Override
        public void execute() {
          intakeSubsystem.deploy();
        }   
        // When interrupted (Toggled off) retract
        @Override
        public void end(boolean interrupted) {
          intakeSubsystem.retract();
        }
      // true = yes please interrupt
      }, true);

    //When Y is pressed, deploy colour arm
    // TODO Check if this works. I dont wanna make another cmd file just for this small thing
    new JoystickButton(operatorStick, Constants.Y)
      .toggleWhenPressed(new CommandBase() {
        // When CMD is running, deploy
        @Override
        public void execute() {
          colourSubsystem.rise();
        }   
        // When interrupted (Toggled off) retract
        @Override
        public void end(boolean interrupted) {
          colourSubsystem.fall();
        }
      // true = yes please interrupt
      }, true);

    //When A is pressed, spin to colour
    new JoystickButton(operatorStick, Constants.X)
      .whenPressed(() -> {
        // Convert i to int
        int i = (int) SmartDashboard.getNumber("Spin Number", 0.0);
        // If 0 or less, spin to colour
        if(i <= 0)
          colourSubsystem.spinTo(colorChooser.getSelected());
        // else, do a num of spin
        else
          colourSubsystem.spin(i);
      }, colourSubsystem);
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