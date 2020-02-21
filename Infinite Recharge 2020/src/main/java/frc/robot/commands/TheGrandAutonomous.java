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
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShootSubsystem;
import frc.robot.subsystems.StorageSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class TheGrandAutonomous extends SequentialCommandGroup {
  /**
   * Creates a new TheGrandAutonomous.
   */
  public TheGrandAutonomous(DriveSubsystem driveSubsystem, IntakeSubsystem IntakeSubsystem, ShootSubsystem shootSubsystem, StorageSubsystem storageSubsystem) {
    addCommands(
      //shoot
      new RunCommand(()-> shootSubsystem.shoot(), driveSubsystem)
      //stop shooting after 5 seconds
      .withTimeout(5),

      //move the storage
      new RunCommand(()-> storageSubsystem.store(), storageSubsystem)
      .withTimeout(5), //stop storing after 5 seconds

      new InstantCommand(() -> driveSubsystem.turn(Math.toRadians(120.0)), driveSubsystem),
    
      //TODO change the distance to what's right
      new InstantCommand(() -> driveSubsystem.travel(10), driveSubsystem)


    
    );
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    //super();
  }
}
