/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimbSubsystem extends SubsystemBase {

  // Controllers
  private WPI_VictorSPX left = new WPI_VictorSPX(Constants.Climber.CL);
  private WPI_VictorSPX right = new WPI_VictorSPX(Constants.Climber.CR);
  // Speed Group
  private SpeedControllerGroup climber;

  /**
   * Creates a new ClimbSubsystem.
   */
  public ClimbSubsystem() {
    // Set Inverse
    right.setInverted(true);
    // Group Together
    climber = new SpeedControllerGroup(left, right);
  }

  // TODO Set auto stop somehow
  public void climb(double speed) {
    climber.set(speed);
  }

  public void stop() {
    climber.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
