package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {
    
    CANSparkMax intakeArm;
    CANSparkMax intakeWheels;

    public void init() {
        intakeArm = new CANSparkMax(7, MotorType.kBrushless);
        intakeWheels = new CANSparkMax(8, MotorType.kBrushless);
    }
}
