/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ShootSubsystem;
import frc.robot.subsystems.StorageSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class DumpLoad extends SequentialCommandGroup {
  /**
   * Creates a new DumpLoad.
   */
  public DumpLoad(DriveSubsystem driveSubsystem, StorageSubsystem storageSubsystem, ShootSubsystem shootSubsystem) {
    // Add Commands
    super(
      // Drive Towards Low Port
      new RunCommand(() -> driveSubsystem.driveRaw(0.5, 0.5))
      .beforeStarting(driveSubsystem::resetTimer, driveSubsystem)
      .beforeStarting(driveSubsystem::startTimer, driveSubsystem)
      .withInterrupt(() -> driveSubsystem.getTimer() >= 4),
      // Deposit
      new RunCommand(() -> {
        driveSubsystem.stop();
        shootSubsystem.shoot();
        shootSubsystem.transfer();
        storageSubsystem.store();
      })
      .beforeStarting(driveSubsystem::resetTimer, driveSubsystem)
      .beforeStarting(driveSubsystem::startTimer, driveSubsystem)
      .withInterrupt(() -> driveSubsystem.getTimer() >= 2),
      // Stop to Allow Rest
      new RunCommand(() -> {
        shootSubsystem.stop();
        storageSubsystem.stop();
      })
      .beforeStarting(driveSubsystem::resetTimer, driveSubsystem)
      .beforeStarting(driveSubsystem::startTimer, driveSubsystem)
      .withInterrupt(() -> driveSubsystem.getTimer() >= 0.5),
      // Re-deposit
      new RunCommand(() -> {
        shootSubsystem.shoot();
        shootSubsystem.transfer();
        storageSubsystem.store();
      })
      .beforeStarting(driveSubsystem::resetTimer, driveSubsystem)
      .beforeStarting(driveSubsystem::startTimer, driveSubsystem)
      .withInterrupt(() -> driveSubsystem.getTimer() >= 2),
      // Stop
      new InstantCommand(() -> {
        shootSubsystem.stop();
        shootSubsystem.stopTransfer();
        storageSubsystem.stop();
      })
    );
  }
}
