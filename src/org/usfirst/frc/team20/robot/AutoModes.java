package org.usfirst.frc.team20.robot;

import org.usfirst.frc.team20.robot.DriveTrain;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.*;

public class AutoModes { // practlas uses roll instead of pitch
	DigitalInput lineSensor;
	AnalogInput ultrasonic;
	AHRS navx;
	private boolean gotAngle, turned = false, seeLine = false;
	private double origAngle;
	private boolean gotPitch;
	private double origPitch;
	DriveTrain drive = new DriveTrain(4);
	VisionTargeting vt = new VisionTargeting();
	AnalogInput rightEncoder;
	AnalogInput leftEncoder;
	private boolean crossed = false;
	private boolean flat = false;
	private boolean gotTime = false, gotVAngle = false;
	private double origTime, angle;

	public AutoModes(DriveTrain drive, AHRS navx) {
		drive.assignPort(0, 2); // TODO port numbers for DriveTrain
		drive.assignPort(1, 5);
		drive.assignPort(2, 3);
		drive.assignPort(3, 11);
	}

	public void driveDist(double distance, double speed) { // TODO encoders
		System.out.println(drive.motors[0].getPosition());
	}
	public void driveTime(double time, double speed) {
		if (!gotTime)
			origTime = DriverStation.getInstance().getMatchTime();
		
		if (DriverStation.getInstance().getMatchTime() < time + origTime) {
			drive.drive(0, -speed); // TODO negate proper side (straight)
			drive.drive(1, speed);
			drive.drive(2, -speed);
			drive.drive(3, speed);
		}
	}
	

	public void lowG(double tolerance) {
		if (!crossed)
			cross();
		
		if (!seeLine && crossed)
			findLine();
		
		if (seeLine && !turned && !gotVAngle) {
			angle = vt.getAngle();
			gotVAngle = true;
		}
		
		if (!turned && seeLine && gotVAngle) 
			turnToAngle(angle, tolerance);
		
		if (turned) 
			reach();
	}

	public void highG(double tolerance) {
		if(!crossed)
			cross();

		if(!seeLine && crossed)
			findLine();
		
		if (seeLine && !turned && !gotVAngle) {
			angle = vt.getAngle();
			gotVAngle = true;
		}

		if(!turned && seeLine && gotVAngle)
			turnToAngle(angle, tolerance);
		
		if(turned)
			shoot();
	}

	public void drive(double speed) {
		drive.drive(0, -speed); // TODO negate proper side (straight)
		drive.drive(1, speed);
		drive.drive(2, -speed);
		drive.drive(3, speed);
	}

	public void stop() {
		drive.drive(0, 0);
		drive.drive(1, 0);
		drive.drive(2, 0);
		drive.drive(3, 0);
	}

	public void findLine() {
		if (!lineSensor.get()) {
			drive.drive(0, 1);
			drive.drive(1, -1);
			drive.drive(2, 1);
			drive.drive(3, -1);
		} else if (lineSensor.get()) {
			drive.drive(0, 0);
			drive.drive(1, 0);
			drive.drive(2, 0);
			drive.drive(3, 0);
			seeLine = true;
		}
	}

	public void turnToAngle(double angle, double tolerance) { // robot turn to
																// the inserted
																// angle
		if (!gotAngle) {
			origAngle = navx.getAngle();
			gotAngle = true;
		}
		if (navx.getAngle() > origAngle + angle + tolerance
				&& navx.getAngle() < origAngle + 180) {
			drive.drive(0, 1); // TODO negate proper side
			drive.drive(1, 1);
			drive.drive(2, 1);
			drive.drive(3, 1);
		}
		if (navx.getAngle() < origAngle + angle - tolerance
				&& navx.getAngle() > origAngle + 180) {
			drive.drive(0, -1); // TODO negate proper side
			drive.drive(1, -1);
			drive.drive(2, -1);
			drive.drive(3, -1);
		} else {
			turned = true;
		}
	}

	public void shoot() {
		//TODO SHOOT
	}

	public void cross() {
		if (!gotPitch) {
			origPitch = navx.getPitch();
			gotPitch = true;
		}
		if (navx.getPitch() > origPitch + 10) {
			System.out.println("Crossing");
			flat = true;
		}
		if (flat && navx.getPitch() <= origPitch + .2) {
			stop();
			crossed = true;
		} else {
			drive(1);
		}

	}

	public void reach() {
		if (!gotPitch) {
			origPitch = navx.getPitch();
			gotPitch = true;
		}
		if (navx.getPitch() > origPitch + 10) {
			stop();
		} else {
			drive(1);
		}
	}
}