/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.CameraSubsystem;
import frc.robot.subsystems.DriveSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class Adjustment extends SequentialCommandGroup {

  /**
   * The Subsystem
   */
  private CameraSubsystem cameraSubsystem;
  private DriveSubsystem driveSubsystem;
  /**
   * Creates a new Adjustment.
   */
  public Adjustment(CameraSubsystem cameraSubsystem, DriveSubsystem driveSubsystem) {
    // Subsystem
    this.cameraSubsystem = cameraSubsystem;
    this.driveSubsystem = driveSubsystem;
    // Adding Commands
    addCommands(
      // Align Y
      alignY(),
      // Align X
      alignX()
    );
  }

  /**
   * Align the X
   */
  private Command alignX() {
    return new RunCommand(() -> {
      // Out of View or Above Desired Coord
      if(cameraSubsystem.getDistX() > 0 || cameraSubsystem.getX() == -1.0)
        driveSubsystem.driveRaw(-Constants.Camera.kSpeed, -Constants.Camera.kSpeed);
      // Below Desired Coord
      else if(cameraSubsystem.getDistX() < 0)
        driveSubsystem.driveRaw(Constants.Camera.kSpeed, Constants.Camera.kSpeed);
    })
    // Give Up after a while
    .withTimeout(Constants.Camera.kTimeout)
    .withInterrupt(() -> cameraSubsystem.getDistX() > -Constants.Camera.kRange && cameraSubsystem.getDistX() < Constants.Camera.kRange);
  }

  /**
   * Align the Y
   */
  private Command alignY() {
    return new RunCommand(() -> {
      // Out of View
      for(int i = 1; cameraSubsystem.getY() == -1.0 && i <= 15; i += 2) {
        // A function that Calculates Angle to Turn to
        driveSubsystem.turn(((i * Math.PI/8) + (Math.PI/8)) * Math.sin((Math.PI/2) * i));
        // Test if in Picture
        if(cameraSubsystem.getY() != -1.0)
          break;
      }
      // Left Desired Coord
      if(cameraSubsystem.getDistY() > 0 || cameraSubsystem.getY() == -1.0)
        driveSubsystem.driveRaw(-Constants.Camera.kSpeed, Constants.Camera.kSpeed);
      // Below Desired Coord
      else if(cameraSubsystem.getDistY() < 0)
        driveSubsystem.driveRaw(Constants.Camera.kSpeed, -Constants.Camera.kSpeed);
    })
    // Give Up after a while
    .withTimeout(Constants.Camera.kTimeout)
    .withInterrupt(() -> cameraSubsystem.getDistX() > -Constants.Camera.kRange && cameraSubsystem.getDistX() < Constants.Camera.kRange);
  }
}
