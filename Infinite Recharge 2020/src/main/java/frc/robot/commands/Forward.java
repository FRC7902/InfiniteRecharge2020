/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

// import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
// import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootSubsystem;
import frc.robot.subsystems.StorageSubsystem;


// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class Forward extends SequentialCommandGroup {
  /**
   * Creates a new Forward.
   */
  public Forward(DriveSubsystem driveSubsystem, IntakeSubsystem IntakeSubsystem, ShootSubsystem shootSubsystem, StorageSubsystem storageSubsystem) {
    // Add Commands
    super(
      // Drive 2 meters
      new RunCommand(() -> driveSubsystem.driveRaw(1, 1))
      .beforeStarting(driveSubsystem::resetTimer, driveSubsystem)
      .beforeStarting(driveSubsystem::startTimer, driveSubsystem)
      .withInterrupt(() -> driveSubsystem.getTimer() > 0.2)
      
      //SOLUTION 1
      // new RunCommand(() -> driveSubsystem.driveRaw(0.3, 0.3), driveSubsystem)
      // .withTimeout(5)
      // new RunCommand(() -> driveSubsystem.driveRaw(0.1, 0.1), driveSubsystem)

      //SOLUTION 2
      // new RunCommand(() -> driveSubsystem.driveRaw(0.3, 0.3))
      // .beforeStarting(driveSubsystem::resetEnc, driveSubsystem)
      // .withInterrupt(() -> driveSubsystem.checkIfDist(2))

      //SOLUTION 3
      // new RunCommand(() -> driveSubsystem.driveRaw(-0.3, -0.3))
      // .beforeStarting(driveSubsystem::resetTimer, driveSubsystem)
      // .beforeStarting(driveSubsystem::startTimer, driveSubsystem)
      // .withInterrupt(() -> driveSubsystem.getTimer() > 5)
      // new DumpShot(shootSubsystem, storageSubsystem)
      // .withInterrupt(() -> (System.nanoTime() >= startT + 10d * Math.pow(10, 9))),
      // new RunCommand(() -> driveSubsystem.driveRaw(0.6, 0.6))
      // .beforeStarting(driveSubsystem::resetTimer, driveSubsystem)
      // .beforeStarting(driveSubsystem::startTimer, driveSubsystem)
      // .withInterrupt(() -> driveSubsystem.getTimer() > 5)

      //SOLUTION 4 
      // new InstantCommand(() -> driveSubsystem.turn(Math.toRadians(90.0)), driveSubsystem)
      // new RunCommand(() -> driveSubsystem.travel(2), driveSubsystem),
      // // Turn 90 Degrees to the right
      // new RunCommand(() -> driveSubsystem.turn(Math.toRadians(90.0)), driveSubsystem)
    );
  }
}
