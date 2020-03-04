/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.StorageSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class Deployment extends SequentialCommandGroup {
  /**
   * Creates a new Deployment.
   */
  public Deployment(DriveSubsystem driveSubsystem, IntakeSubsystem intakeSubsystem, StorageSubsystem storageSubsystem) {
    // Add Commands
    super(
      // Sketch Deployment
      new RunCommand(() -> driveSubsystem.driveRaw(1, 1))
      .beforeStarting(driveSubsystem::resetTimer, driveSubsystem)
      .beforeStarting(driveSubsystem::startTimer, driveSubsystem)
      .withInterrupt(() -> driveSubsystem.getTimer() > 0.2),
      // Wait 1 Second
      new WaitCommand(1),
      // Drive Forward and Suck
      new RunCommand(() -> {
        driveSubsystem.driveRaw(-0.4, - 0.4);
        intakeSubsystem.suck();
        storageSubsystem.store();
      })
      .beforeStarting(driveSubsystem::resetTimer, driveSubsystem)
      .beforeStarting(driveSubsystem::startTimer, driveSubsystem)
      .withInterrupt(() -> driveSubsystem.getTimer() > 5)
    );
  }
}
