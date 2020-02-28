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
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootSubsystem;
import frc.robot.subsystems.StorageSubsystem;


// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class AutonomousSequence extends SequentialCommandGroup {
  /**
   * Creates a new AutonomousSequence.
   */
  public AutonomousSequence(DriveSubsystem driveSubsystem, IntakeSubsystem IntakeSubsystem, ShootSubsystem shootSubsystem, StorageSubsystem storageSubsystem) {
    // TODO Test Auto to see functionality
    addCommands(
      // Drive 2 meters
      new RunCommand(() -> driveSubsystem.driveRaw(1, 1))
      .beforeStarting(driveSubsystem::resetEnc, driveSubsystem)
      .withInterrupt(() -> driveSubsystem.getAvgEncDist() >= 2)
      // new RunCommand(() -> driveSubsystem.travel(2), driveSubsystem),
      // // Turn 90 Degrees to the right
      // new RunCommand(() -> driveSubsystem.turn(Math.toRadians(90.0)), driveSubsystem)
    );
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    //super();
  }
}
