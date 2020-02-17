/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {

  // Intake Controllers
  private WPI_VictorSPX upper = new WPI_VictorSPX(Constants.UI);
  private WPI_VictorSPX lower = new WPI_VictorSPX(Constants.LI);

  // Solenoids
  private DoubleSolenoid left = new DoubleSolenoid(Constants.LS_FRONT, Constants.LS_BACK);
  private DoubleSolenoid right = new DoubleSolenoid(Constants.RS_FRONT, Constants.RS_BACK);

  // Status
  private String status;
  private boolean isDeployed;

  /**
   * Creates a new IntakeSubsystem.
   */
  public IntakeSubsystem() {
    // Retract Pistons
    left.set(DoubleSolenoid.Value.kOff);
    right.set(DoubleSolenoid.Value.kOff);
    isDeployed = false;
    // Invert the bottom motors
    lower.setInverted(true);
    status = "Off";
  }

  /**
   * Sucks the balls in
   */
  public void succ() {
    if(!isDeployed)
      deploy();
    upper.set(Constants.SUCSPEED);
    lower.set(Constants.SUCSPEED);
    status = "Succing";
  }

  /**
   * Stop Sucking
   */
  public void stap() {
    upper.stopMotor();
    lower.stopMotor();
    status = "Off";
  }

  /**
   * Deploys the intake stuff
   * Auto run when succ() is used and not deployed
   */
  public void deploy() {
    // Prevent Multi-use
    if(isDeployed)
      return;
    left.set(DoubleSolenoid.Value.kForward);
    right.set(DoubleSolenoid.Value.kForward);
    isDeployed = true;
  }

  /**
   * Undeploy 
   */
  public void retract() {
    // Prevent Multi-use
    if(!isDeployed)
      return;
    left.set(DoubleSolenoid.Value.kReverse);
    right.set(DoubleSolenoid.Value.kReverse);
    isDeployed = false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler 
    SmartDashboard.putString("Intake Status", (upper.isAlive() == lower.isAlive())? status : "Broken");
  }
}
