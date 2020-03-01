/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Constants;
import frc.robot.subsystems.ShootSubsystem;
import frc.robot.subsystems.StorageSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class Shoot extends SequentialCommandGroup {
  /**
   * Creates a new Shoot.
   */
  public Shoot(ShootSubsystem shootSubsystem, StorageSubsystem storageSubsystem) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(

      // Runs the reversal of everything in parallel
      new InstantCommand(
        () -> shootSubsystem.reverse(), shootSubsystem
      ),
      new InstantCommand(
        () -> shootSubsystem.reverseTransfer(), shootSubsystem
      ),
      new InstantCommand(
        () -> storageSubsystem.reverse(), storageSubsystem
      ),
      // Waits 0.5 Seconds
      new WaitCommand(Constants.Shooter.kTimeout),
      // Starts preparations
      new InstantCommand(
        () -> storageSubsystem.stop(), storageSubsystem
      ),
      new InstantCommand(
        () -> shootSubsystem.stopTransfer(), shootSubsystem
      ),
      new InstantCommand(
        () -> shootSubsystem.shoot(), shootSubsystem
      ),
      // Wait Until Shooter is Full Power
      new WaitUntilCommand(() -> shootSubsystem.isPowered()),
      // Activate
      new InstantCommand(
        () -> shootSubsystem.transfer(), shootSubsystem
      ),
      new InstantCommand(
        () -> storageSubsystem.store(), storageSubsystem
      )
    );
  }
}
