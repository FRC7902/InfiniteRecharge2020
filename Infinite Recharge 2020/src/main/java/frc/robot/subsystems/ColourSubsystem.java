/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ColourSubsystem extends SubsystemBase {

  /**
   * Change the I2C port below to match the connection of your color sensor
   */
  private final I2C.Port i2cPort = I2C.Port.kOnboard;

  /**
   * A Rev Color Sensor V3 object is constructed with an I2C port as a 
   * parameter. The device will be automatically initialized with default 
   * parameters.
   */
  private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);

  /**
   * A Rev Color Match object is used to register and detect known colors. This can 
   * be calibrated ahead of time or during operation.
   * 
   * This object uses a simple euclidian distance to estimate the closest match
   * with given confidence range.
   */
  private final ColorMatch m_colorMatcher = new ColorMatch();

  /**
   * The colours of the wheel of misfortune
   */
  public final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
  public final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
  public final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
  public final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

  /**
   * Solenoid Piston thing
   */
  private final DoubleSolenoid piston = new DoubleSolenoid(Constants.Colour.kFrontSolenoid, Constants.Colour.kBackSolenoid);

  /**
   * Motor
   */
  private final WPI_VictorSPX spinner = new WPI_VictorSPX(Constants.Colour.kSpinner);

  /**
   * Creates a new ColourSubsystem.
   */
  public ColourSubsystem() {
    // Off
    piston.set(DoubleSolenoid.Value.kOff);
    // Add Colours
    m_colorMatcher.addColorMatch(kBlueTarget);
    m_colorMatcher.addColorMatch(kGreenTarget);
    m_colorMatcher.addColorMatch(kRedTarget);
    m_colorMatcher.addColorMatch(kYellowTarget);  
  }

  /**
   * Raise the arm
   */
  public void rise() {
    piston.set(DoubleSolenoid.Value.kForward);
  }

  /**
   * Lowers the arm
   */
  public void fall() {
    piston.set(DoubleSolenoid.Value.kReverse);
  }

  /**
   * Spin the Lazy Susan n time
   * @param n
   * Spin Amount
   */
  public void spin(int n) {
    // Track First Color
    Color firstColour = m_colorSensor.getColor();
    //Start Motor
    spinner.set(Constants.Colour.kSpinSpeed);
    //Track
    for(int i = 0; i < n*2;) {
      // Checks color and confidence
      if(firstColour == m_colorSensor.getColor() && m_colorMatcher.matchClosestColor(m_colorSensor.getColor()).confidence > Constants.Colour.kMinConfidence)
        i++;
    }
    // Stop
    spinner.stopMotor();
  }

  /**
   * Spin to Color
   * @param c
   * The color
   */
  public void spinTo(Color c) {
    // Check Color & confidence
    // If no, spin
    while(!(c == m_colorSensor.getColor() && m_colorMatcher.matchClosestColor(m_colorSensor.getColor()).confidence > Constants.Colour.kMinConfidence)) 
      spinner.set(Constants.Colour.kSpinSpeed);
    // Stop Motor
    spinner.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    /**
     * This gets the color sensed from the sensor
     */
    Color detectedColor = m_colorSensor.getColor();

    /**
     * Run the color match algorithm on our detected color
     */
    String colorString = "N/A";
    ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);

    // Test for colour
    if(match.confidence > 0.8)
      if (match.color == kBlueTarget)
        colorString = "Blue";
      else if (match.color == kRedTarget)
        colorString = "Red";
      else if (match.color == kGreenTarget)
        colorString = "Green";
      else if (match.color == kYellowTarget)
        colorString = "Yellow";
      else
        colorString = "Unknown";
    else
      colorString = "Unknown";
    // SmartDashboard Output
    SmartDashboard.putNumber("Confidence", match.confidence);
    SmartDashboard.putString("Detected Color", colorString);
  }
}
