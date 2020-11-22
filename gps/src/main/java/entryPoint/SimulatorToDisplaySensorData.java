package entryPoint;

import java.io.IOException;
import java.util.List;

public class SimulatorToDisplaySensorData {
	
	private String currentTime = "-";
	private String vehicleSpeed = "-";
	private String steerAngle = "-";
	private String yawRate = "-";
	private String latAccel = "-";
	private String longAccel = "-";
	private String gpsLatLong = "-";
	
	public void displaySensorInformation(List<SensorObj> dataArray) {
		
		int flag = 0;
		System.out.println("CurrentTime"+"\t"+"VehicleSpeed"+"\t"+"SteerAngle"+
				"\t"+"YawRate"+"\t\t"+"LatAccel"+"\t"+"LongAccel"+"\t"+"GPS Lat/Long");
		for(SensorObj obj : dataArray) {
			currentTime = obj.getTimeOffset();
			if(obj.getType().equals("Displa vehicle speed(km/hr): ")) {
				vehicleSpeed = obj.getValue();
			}
			if(obj.getType().equals("Steering wheel angle(degrees): ")) {
				
				String newValue = obj.getValue();
				
				Float newValueOfSteerAngle = Float.parseFloat(newValue);
//				Float oldValueOfSteerAngle = steerAngle.equals("-") ? newValueOfSteerAngle : Float.parseFloat(steerAngle);
				
				if(newValueOfSteerAngle > 9 && flag == 0) {
					flag = 1;
					System.out.println("Right turn Started"+"||"+currentTime+"||"+newValueOfSteerAngle);
				}
				if(flag == 1 && newValueOfSteerAngle < 9) {
					flag = 0;
					System.out.println("Right turn Ended"+"||"+currentTime+"||"+newValueOfSteerAngle);
				}
				if(newValueOfSteerAngle < -9 && flag == 0) {
					flag = -1;
					System.out.println("left turn Started"+"||"+currentTime+"||"+newValueOfSteerAngle);
				}
				if(flag == -1 && newValueOfSteerAngle > -9) {
					flag = 0;
					System.out.println("left turn Ended"+"||"+currentTime+"||"+newValueOfSteerAngle);
				}
				steerAngle = newValue;
			}
			if(obj.getType().equals("Vehicle yaw rate(degree/sec):  ")) {
				yawRate = obj.getValue();
			}
			if(obj.getType().equals("Vehicle lat accele(m/sec^2): ")) {
				latAccel = obj.getValue();
			}
			if(obj.getType().equals("Vehicle longi accele(m/sec^2): ")) {
				longAccel = obj.getValue();
			}
			if(obj.getType().equals("GPS data(Latitude:Longitude)")) {
				gpsLatLong = obj.getValue();
			}

//			System.out.print(currentTime+"\t\t"+vehicleSpeed+"\t\t"+steerAngle+
//					"\t\t"+yawRate+"\t"+latAccel+"\t"+longAccel+"\t"+gpsLatLong+"\r");
			
			
		}
	}
}
