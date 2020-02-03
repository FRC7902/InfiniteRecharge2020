/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class AutonomousSequence extends SequentialCommandGroup {
  /**
   * Creates a new AutonomousSequence.
   */
  public AutonomousSequence(DriveSubsystem driveSubsystem) {
    addCommands(
      new RunCommand(
        //start driving forward at the start of the command
        () -> driveSubsystem.driveRaw(0.5, 0.5),
        //Stop driving at the end of the command
        // () -> driveSubsystem.stop(),
        //Requires drive subsystem
        driveSubsystem)
        //Times out after 1 second
        .withTimeout(1),

      new RunCommand(
        //start driving forward at the start of the command
        () -> driveSubsystem.driveRaw(-0.5, 0.5),
        //Stop driving at the end of the command
        // () -> driveSubsystem.stop(),
        //Requires drive subsystem
        driveSubsystem)
        //Times out after 1 second
        .withTimeout(1)


    );
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    //super();
  }
}
