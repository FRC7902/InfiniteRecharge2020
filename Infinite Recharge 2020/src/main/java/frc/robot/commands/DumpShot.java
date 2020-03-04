/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.subsystems.ShootSubsystem;
import frc.robot.subsystems.StorageSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class DumpShot extends ParallelCommandGroup {
  /**
   * Creates a new DumpShot.
   */
  public DumpShot(ShootSubsystem shootSubsystem, StorageSubsystem storageSubsystem) {
    // Add Commands
    super(
      // Dump Balls
      new RunCommand(() -> shootSubsystem.dumpShoot(), shootSubsystem),
      new RunCommand(() -> shootSubsystem.dumpTransfer(), shootSubsystem),
      new RunCommand(() -> storageSubsystem.store(), storageSubsystem)
    );
  }
}
