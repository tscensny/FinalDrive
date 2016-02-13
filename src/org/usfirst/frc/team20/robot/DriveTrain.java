package org.usfirst.frc.team20.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;

public class DriveTrain {
	public CANTalon[]motors;

	public DriveTrain(int m){					//m = amount of CIMs on the drive train
		motors = new CANTalon[m];
		for (int i  = 0; i < motors.length; i++){
			motors[i].setFeedbackDevice(FeedbackDevice.QuadEncoder);
		}
	}
	public void assignPort(int i, int p){		
		System.out.println(i);					//i = motor (numbered from left to right,
		motors[i] = new CANTalon(p);			//top to bottom), p = port number
	}
	public void drive(int m, double s){			//m = motor number (i)
		motors[m].set(s);						//s = speed of motor
	}
	public void arcadeDrive(double s, double r, double l){	//s = speed, r = right, l = left
		for(int i = 0; i < motors.length; i++){		//negates values in order to drive
			if(i%2 == 0){
				motors[i].set(-(s-r+l));
			}else{
				motors[i].set(s-l+r);
			}	
		}		
	}
	public void tankDrive(double l, double r){		//l = left, r = right
		for(int i = 0; i < motors.length; i++){		//negates values in order to drive
			if(i%2 == 0){
				motors[i].set(-l);
			}else{
				motors[i].set(r);
			}
		}
	}
	
	
}