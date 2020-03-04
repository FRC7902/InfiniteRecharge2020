/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
//import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ShootSubsystem;
import frc.robot.subsystems.StorageSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class ShootHigh extends SequentialCommandGroup {
  /**
   * Creates a new ShootHigh.
   */
  public ShootHigh(ShootSubsystem shootSubsystem, StorageSubsystem storageSubsystem, DriveSubsystem driveSubsystem) {
    // Add Commands
    super(
      // Shoot Powerup
      new RunCommand(() -> {
        shootSubsystem.shoot();
      }, shootSubsystem)
      .withInterrupt(() -> shootSubsystem.isPowered()),
      // Activate
      new RunCommand(() -> {
        shootSubsystem.transfer();
        storageSubsystem.store();
      })
      .withTimeout(2),
      // Stop Everything
      new RunCommand(() -> {
        storageSubsystem.stop();
        shootSubsystem.stopTransfer();
        shootSubsystem.stop();
      }).withTimeout(0.5),
      // Powerup
      new RunCommand(() -> {
        shootSubsystem.shoot();
      }, shootSubsystem)
      .withInterrupt(() -> shootSubsystem.isPowered()),
      // Shoot Again
      new RunCommand(() -> {
        shootSubsystem.transfer();
        storageSubsystem.store();
      })
      .withTimeout(2),
      // Stop Again
      new InstantCommand(() -> {
        storageSubsystem.stop();
        shootSubsystem.stopTransfer();
        shootSubsystem.stop();
      }),
      // Drive Forward
      new RunCommand(() -> driveSubsystem.driveRaw(-0.3, -0.3), driveSubsystem)
      .beforeStarting(driveSubsystem::resetTimer, driveSubsystem)
      .beforeStarting(driveSubsystem::startTimer, driveSubsystem)
      .withInterrupt(() -> driveSubsystem.getTimer() >= 5)
    );
  }
}