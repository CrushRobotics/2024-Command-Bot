package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import org.opencv.imgproc.GeneralizedHoughBallard;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.LimelightHelpers;

public class DefaultDriveCommand extends Command {
    
    final private DriveSubsystem driveSubsystem; 
    final CommandXboxController controller;
    private boolean arcadeMode;
    private GenericEntry arcadeDriveEntry;
    private double DEFAULT_MULTIPLIER;
    private double SLOW_MULTIPLIER;

    public DefaultDriveCommand(CommandXboxController controller, DriveSubsystem driveSubsystem) {
        this.controller = controller;
        this.driveSubsystem = driveSubsystem;
        this.addRequirements(driveSubsystem);
        DEFAULT_MULTIPLIER = .8;
        SLOW_MULTIPLIER = .5;
        arcadeDriveEntry = Shuffleboard.getTab("Drive").add("Arcade Drive", false).withWidget(BuiltInWidgets.kToggleSwitch).getEntry();
        
    }

    public void setArcadeDrive(boolean mode){
        arcadeMode = mode;
        Shuffleboard.getTab("Drive").add("Arcade Drive", mode);
    }
    public boolean getArcadeDrive(){
        return arcadeDriveEntry.getBoolean(false);
    }

    @Override
    public void execute() {
        arcadeMode = getArcadeDrive();

        var multiplier = DEFAULT_MULTIPLIER;

        if (controller.leftBumper().getAsBoolean()) {
            multiplier = SLOW_MULTIPLIER;
        }

        if(arcadeMode) {
            double speed = controller.getLeftY() * multiplier;
            double rot = controller.getRightX() * multiplier;

            //driveSubsystem.arcadeDrive(speed, rot);
        }
        else {
            
            double left = controller.getLeftY();
            double right = controller.getRightY();
            
            driveSubsystem.drive(left, right);
        }
        
        

        
    }
}
