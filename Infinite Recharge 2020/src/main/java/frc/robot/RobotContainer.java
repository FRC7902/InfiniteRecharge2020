/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.subsystems.*;
/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

  //Declaring Subsystems
  public static DriveSubsystem driveSubsystem = new DriveSubsystem();

  //Declaring Command
  public static DriveCommand driveCommand = new DriveCommand(driveSubsystem);

  //Declaring Joysticks
  XboxController driverStick = new XboxController(0);


  public XboxController getDriverStick(){
    return driverStick;
  }

  //Autonomous Routines

  //Auto #1
  private final Command auto1 = new StartEndCommand(
    //start driving forward at the start of the command
    () -> driveSubsystem.driveRaw(0.5, 0.5),
    //Stop driving at the end of the command
    () -> driveSubsystem.stop(),
    //Requires drive subsystem
    driveSubsystem)
    //Times out after 1 second
    .withTimeout(1);

  /**
   * This is the dropbox that will display all the auto functions
   */
  SendableChooser<Command> m_chooser = new SendableChooser<>();
  
  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    driveSubsystem.setDefaultCommand(
      //The Arcade command
      new RunCommand(() -> driveSubsystem
        .driveJoystick(driverStick.getY(GenericHID.Hand.kLeft), 
                       driverStick.getX(GenericHID.Hand.kRight)), 
        driveSubsystem));


    // new RunCommand(() -> driveSubsystem.driveJoystick(driver, rightX); ), requirements)
    //Add Commands to the autonomous command chooser
    m_chooser.addOption("Auto1", auto1);

    //m_chooser.setDefaultOption("Auto1", auto1);

    //Put the Chooser on Dashboard
    Shuffleboard.getTab("Autonomous").add(m_chooser);
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // (Example) When right bumper is pressed, halves top speed
    new JoystickButton(driverStick, Button.kBumperRight.value)
      .whenPressed(() -> driveSubsystem.setMaxOutput(0.5))
      .whenReleased(() -> driveSubsystem.setMaxOutput(1));
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
