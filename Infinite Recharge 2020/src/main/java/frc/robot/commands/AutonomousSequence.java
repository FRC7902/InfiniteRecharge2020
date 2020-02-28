/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
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
    
    addCommands(
      // TODO Get Auto to drive robot forward
      // Drive 2 meters
      
      // new RunCommand(() -> driveSubsystem.driveRaw(0.3, 0.3))
      // .withTimeout(5)

      //SOLUTION 2
      new RunCommand(() -> driveSubsystem.driveRaw(0.3, 0.3))
      .beforeStarting(driveSubsystem::resetEnc, driveSubsystem)
      .withInterrupt(() -> driveSubsystem.checkIfDist(2))

      //SOLUTION 3
      // new RunCommand(() -> driveSubsystem.driveRaw(0.3, 0.3))
      // .beforeStarting(driveSubsystem::resetTimer, driveSubsystem)
      // .beforeStarting(driveSubsystem::startTimer, driveSubsystem)
      // .withInterrupt(() -> driveSubsystem.getTimer() > 2)

      //SOLUTION 4
      // new CommandBase() {
      //   @Override
      //   public void initialize() {
      //     driveSubsystem.resetTimer();
      //     driveSubsystem.startTimer();
      //   }
      //   @Override
      //   public void execute() {
      //     driveSubsystem.driveRaw(0.3, 0.3);
      //   }
      //   @Override
      //   public void end(boolean isFinished) {
      //     driveSubsystem.stop();
      //   }
      //   @Override
      //   public boolean isFinished(){
      //     return driveSubsystem.getTimer() > 2;
      //   }
      // }
      
      // new InstantCommand(() -> driveSubsystem.turn(Math.toRadians(90.0)), driveSubsystem)
      
      // new RunCommand(() -> driveSubsystem.travel(2), driveSubsystem),
      // // Turn 90 Degrees to the right
      // new RunCommand(() -> driveSubsystem.turn(Math.toRadians(90.0)), driveSubsystem)
    );
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    //super();
  }
}
