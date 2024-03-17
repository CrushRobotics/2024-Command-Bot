package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkBase.IdleMode;

public class ArmSubsystem extends PIDSubsystem {
    
    private final CANSparkMax armLeader;
    private final CANSparkMax armFollower;
    private final RelativeEncoder encoder;
    private double target;

    public ArmSubsystem()
    {
        super(new PIDController(0, 0, 0));
        
        armLeader = new CANSparkMax(5, MotorType.kBrushless);
        armFollower = new CANSparkMax(6, MotorType.kBrushless);

        // Reset to factory defaults
        armLeader.restoreFactoryDefaults();
        armFollower.restoreFactoryDefaults();

        armFollower.follow(armLeader);

        // Setup brake mode to keep them from coasting
        armLeader.setIdleMode(IdleMode.kBrake);
        armFollower.setIdleMode(IdleMode.kBrake);

        encoder = armLeader.getEncoder();
        encoder.setPosition(0);
    }

    @Override
    public void periodic()
    {
        double currentPosition = encoder.getPosition();
        double targetSpeed = 0.3;

        if (Math.abs(currentPosition - target) < 5)
        {
            targetSpeed = 0.1;
        }

        if (currentPosition < target)
        {
            armLeader.set(targetSpeed);
        } 
        else if (currentPosition > target)
        {
            armLeader.set(targetSpeed * -1.0);
        }
        else 
        {
            armLeader.set(0);
        }
    }

    // Set target for PID control. This target value maps to an encoder
    // position.
    public void setTarget(double target)
    {
       this.target = target; 
    }

    public void stop()
    {
        armLeader.setVoltage(0);
    }

    public void moveToShootPosition()
    {
        setTarget(25);
    }

    @Override
    protected void useOutput(double output, double setpoint) {
        // Use voltage from PID controller
        armLeader.setVoltage(output);
    }

    @Override
    protected double getMeasurement() {
        // Return position measurement from encoder. We're not 
        // currently doing any calculations to change the units.
        return encoder.getPosition();
    }
}
