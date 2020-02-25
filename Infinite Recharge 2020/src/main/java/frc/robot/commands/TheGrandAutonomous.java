/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootSubsystem;
import frc.robot.subsystems.StorageSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class TheGrandAutonomous extends SequentialCommandGroup {
  /**
   * Creates a new TheGrandAutonomous.
   * TODO dont know if runCMD works
   */
  public TheGrandAutonomous(DriveSubsystem driveSubsystem, IntakeSubsystem intakeSubsystem, ShootSubsystem shootSubsystem, StorageSubsystem storageSubsystem) {
    addCommands(
      new ParallelRaceGroup(
      
        //shoot
        new RunCommand(()-> shootSubsystem.shoot(), driveSubsystem)
        //stop after 5 seconds
        .withTimeout(5),

        //move the storage
        new RunCommand(()-> storageSubsystem.store(), storageSubsystem)
      
      ),

      //turn 120 on Point 1
      new InstantCommand(() -> driveSubsystem.turn(Math.toRadians(120.0)), driveSubsystem),
    
      //TODO change the distance to what's right
      new InstantCommand(() -> driveSubsystem.travel(10), driveSubsystem),

      //turn 60 on Point 2
      new InstantCommand(() -> driveSubsystem.turn(Math.toRadians(60.0)), driveSubsystem),

      
      new ParallelRaceGroup(
        //drive forward to Point 3 (should stop when drive is finished)
        new InstantCommand(() -> driveSubsystem.travel(10), driveSubsystem),
        //deploy the intake
        new InstantCommand(() -> intakeSubsystem.deploy(), intakeSubsystem),
        //run the intake
        new RunCommand(() -> intakeSubsystem.suck(), intakeSubsystem),
        //
        new RunCommand(() -> storageSubsystem.store(), storageSubsystem)
      ),

      //DONE Added support to negative distances
      new InstantCommand(() -> driveSubsystem.travel(-10), driveSubsystem),

      //turn 120 on Point 2
      new InstantCommand(() -> driveSubsystem.turn(Math.toRadians(120.0)), driveSubsystem),

      //drive back to Point 1
      new InstantCommand(() -> driveSubsystem.travel(10), driveSubsystem),

      //turn on Point 1
      new InstantCommand(() -> driveSubsystem.turn(Math.toRadians(60.0)), driveSubsystem),

      new ParallelRaceGroup(
      
      //shoot
      new RunCommand(()-> {
        shootSubsystem.shoot();
      }, driveSubsystem)
      //stop after 5 seconds
      .withTimeout(5),

      //move the storage
      new RunCommand(()-> {
        shootSubsystem.stap();
        storageSubsystem.store();
      }, storageSubsystem)
      
      )
    );
  }
}
