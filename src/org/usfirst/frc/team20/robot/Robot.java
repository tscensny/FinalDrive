package org.usfirst.frc.team20.robot;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.*;

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
	double num, angle;
	int position, mode;

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
		
		num = 1234; 					// TODO This is where the number from the digital display board will go
		String n = Double.toString(num);// the goal here is to get the 2nd digit and the 4th digit from the board
		String p = "";
		for (int k = 4; k < 2; k++) {
			p += n.substring(k + 1, k + 2);
		}
		position = Integer.parseInt(p);		
		String m = "";
		for (int i = 4; i > 3; i--) {
			m += n.substring(i - 1);
		}
		mode = Integer.parseInt(m);
		low = false;
	}

	public void teleoperatedInit() {
		arcade = true;
	}

	// This function is called periodically during autonomous

	public void autonomousPeriodic() {
		angle = vt.getAngle();
		switch (position) {
		case 1:
			switch (mode) {
			case 1:
				auto.reach();//TODO The first angles in lowG and highG are preliminary
				break;				//They will be later replaced with angles from the camera (hopefully)
			case 2:
				auto.cross();
				break;
			case 3:
				auto.findLine();
				break;
			case 4:
				auto.lowG(angle, 3);
				break;
			case 5:
				auto.highG(angle - 180, 3);
				break;
			}
		case 2:
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
				auto.lowG(angle , 3);
				break;
			case 5:
				auto.highG(angle - 180, 3);
				break;
			}
		case 3:
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
				auto.lowG(angle, 3);
				break;
			case 5:
				auto.highG(angle - 180, 3);
				break;
			}
		case 4:
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
				auto.lowG(angle, 3);
				break;
			case 5:
				auto.highG(angle + 180, 3);
				break;
			}
		case 5:
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
				auto.lowG(angle, 3);
				break;
			case 5:
				auto.highG(angle + 180, 3);
				break;
			}
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
			d.arcadeDrive(straightr, right, left);
		else if (!arcade)
			d.tankDrive(straightl, straightr);
		if (joy.getRawButton(8)) // If your a fool and want tank drive you can
								 // press the pause button
			arcade = !arcade;
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {

	}

}