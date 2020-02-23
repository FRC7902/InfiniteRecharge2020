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
  private WPI_VictorSPX intakeMotor = new WPI_VictorSPX(Constants.Intake.kIntake);

  // Solenoids
  private DoubleSolenoid soleIntake = new DoubleSolenoid(Constants.Intake.kFrontSolenoid, Constants.Intake.kBackSolenoid);

  // Status
  private String status;
  private boolean isDeployed;

  /**
   * Creates a new IntakeSubsystem.
   */
  public IntakeSubsystem() {
    // Retract Pistons
    soleIntake.set(DoubleSolenoid.Value.kOff);
    isDeployed = false;
    // Set Status
    status = "Off";
  }

  /**
   * Sucks the balls in
   */
  public void suck() {
    if(!isDeployed)
      deploy();
    intakeMotor.set(Constants.Intake.kSpeed);
    status = "Succing";
  }

  /**
   * Stop Sucking
   */
  public void stop() {
    intakeMotor.stopMotor();
    status = "Off";
  }

  /**
   * Deploys the intake stuff
   * Auto run when suck() is used and not deployed
   */
  public void deploy() {
    // Prevent Multi-use
    if(isDeployed)
      return;
    soleIntake.set(DoubleSolenoid.Value.kForward);
    isDeployed = true;
  }

  /**
   * Undeploy 
   */
  public void retract() {
    // Prevent Multi-use
    if(!isDeployed)
      return;
    soleIntake.set(DoubleSolenoid.Value.kReverse);
    isDeployed = false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler 
    SmartDashboard.putString("Intake Status", (intakeMotor.isAlive())? status : "Broken");
    SmartDashboard.putString("Intake Deployment", isDeployed? "Deployed" : "Retracted");
  }
}
