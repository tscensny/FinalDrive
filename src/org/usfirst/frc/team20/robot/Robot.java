package org.usfirst.frc.team20.robot;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */

	Joystick joy;
	DriveTrain d;
	AHRS ahrs;
	AutoModes auto;
	VisionTargeting vt;
	boolean arcade, reach, cross, high, low;
	double angle,a;
	int mode;
	REVDigitBoard board;
	String autoSelected;
	SendableChooser chooser;

	public void robotInit() {
		joy = new Joystick(0);
		d = new DriveTrain(4);
		ahrs = new AHRS(SerialPort.Port.kMXP);
		auto = new AutoModes(d, ahrs);
		vt = new VisionTargeting();
	}

	public void autonomousInit() {
		reach = false;
		cross = false;
		high = false;
		mode = (int) a;
		low = false;
	}

	public void teleoperatedInit() {
		arcade = true;
	}

	// This function is called periodically during autonomous

	public void autonomousPeriodic() {
		switch (mode) {
		case 1:
			auto.reach();
			break;
		case 2:
			auto.cross();
			break;
		case 3:
			auto.findLine();
			break;
		case 4:
			auto.lowG(3);
			break;
		case 5:
			auto.highG(3);
			break;
		}
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		double straightl = joy.getRawAxis(1);
		double right = joy.getRawAxis(3);
		double left = joy.getRawAxis(2);
		double straightr = joy.getRawAxis(5);
		if (arcade)
			d.arcadeDrive(straightl, right, left);
		else if (!arcade)
			d.tankDrive(straightl, straightr);
		if (joy.getRawButton(8)) // If your a fool and want tank drive you can
									// press the pause button
			arcade = !arcade;
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void disabledInit() {
		a = 0;
	}

	public void disabledPeriodic() {
		board.display(a);
		if (board.getButtonA()) {
			a += 1;
		}
		if (board.getButtonB()) {
			a -= 1;
		}

	}

	public void testPeriodic() {

	}

}