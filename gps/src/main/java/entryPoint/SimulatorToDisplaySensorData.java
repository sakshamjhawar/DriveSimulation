package entryPoint;

import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

public class SimulatorToDisplaySensorData {
	
	private String currentTime = "-";
	private String vehicleSpeed = "-";
	private String steerAngle = "-";
	private String yawRate = "-";
	private String latAccel = "-";
	private String longAccel = "-";
	private String gpsLatLong = "-";
	
	public CurveSensorPojo displaySensorInformation(List<SensorObj> dataArray) {
		
		List<String> UIArray = new ArrayList<String>();
		List<CurveInfo> curveData = new ArrayList<CurveInfo>();
		CurveSensorPojo finalData = new CurveSensorPojo();
		int flag = 0;
		System.out.println("CurrentTime"+"\t"+"VehicleSpeed"+"\t"+"SteerAngle"+
				"\t"+"YawRate"+"\t\t"+"LatAccel"+"\t"+"LongAccel"+"\t"+"GPS Lat/Long");
		CurveInfo curveDetected = null;
		Float speedSum = 0F;
		Float speedCounter = 0F;
		for(SensorObj obj : dataArray) {
			currentTime = obj.getTimeOffset();
			if(obj.getType().equals("Displa vehicle speed(km/hr): ")) {
				
				if(flag == 1 || flag == -1) {
					speedSum = speedSum + Float.parseFloat(vehicleSpeed); 
					speedCounter += 1;
				}
				vehicleSpeed = obj.getValue();
			}
			if(obj.getType().equals("Steering wheel angle(degrees): ")) {
				
				String newValue = obj.getValue();
				
				Float newValueOfSteerAngle = Float.parseFloat(newValue);
//				Float oldValueOfSteerAngle = steerAngle.equals("-") ? newValueOfSteerAngle : Float.parseFloat(steerAngle);
				
				if(newValueOfSteerAngle > 9 && flag == 0) {
					flag = 1;
					
					curveDetected = new CurveInfo();
					curveDetected.setGpsLatLongStart(gpsLatLong);
					curveDetected.setDirection(false);
					curveDetected.setTimeOffset(currentTime);
					speedSum = speedSum + Float.parseFloat(vehicleSpeed); 
					speedCounter += 1;
//					System.out.println("Right turn Started"+"||"+currentTime+"||"+newValueOfSteerAngle);
				}
				if(flag == 1 && newValueOfSteerAngle < 9) {
					flag = 0;
					curveDetected.setGpsLatLongEnd(gpsLatLong);
					float averageSpeed = (speedSum/speedCounter);
					curveDetected.setAverageVehicleSpeed(averageSpeed+"");
					if(averageSpeed > 50) {
						curveDetected.setspeedflag(true);
					} else {
						curveDetected.setspeedflag(false);
					}
					curveData.add(curveDetected);
					speedSum = 0F;
					speedCounter = 0F;
//					System.out.println("Right turn Ended"+"||"+currentTime+"||"+newValueOfSteerAngle);
				}
				if(newValueOfSteerAngle < -9 && flag == 0) {
					flag = -1;
					curveDetected = new CurveInfo();
					curveDetected.setGpsLatLongStart(gpsLatLong);
					curveDetected.setDirection(true);
					curveDetected.setTimeOffset(currentTime);
					speedSum = speedSum + Float.parseFloat(vehicleSpeed); 
					speedCounter += 1;
//					System.out.println("left turn Started"+"||"+currentTime+"||"+newValueOfSteerAngle);
				}
				if(flag == -1 && newValueOfSteerAngle > -9) {
					flag = 0;
					curveDetected.setGpsLatLongEnd(gpsLatLong);
					float averageSpeed = (speedSum/speedCounter);
					curveDetected.setAverageVehicleSpeed(averageSpeed+"");
					if(averageSpeed > 50) {
						curveDetected.setspeedflag(true);
					} else {
						curveDetected.setspeedflag(false);
					}
					curveData.add(curveDetected);
					speedSum = 0F;
					speedCounter = 0F;
//					System.out.println("left turn Ended"+"||"+currentTime+"||"+newValueOfSteerAngle);
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

			String s = currentTime+"\t"+vehicleSpeed+"\t"+steerAngle+
					"\t"+yawRate+"\t"+latAccel+"\t"+longAccel+"\t"+gpsLatLong;
			System.out.print(s+"\r");
			UIArray.add(s);
		}
		if(flag == 1 || flag == -1) {
			curveDetected.setGpsLatLongEnd(gpsLatLong);
			float averageSpeed = (speedSum/speedCounter);
			curveDetected.setAverageVehicleSpeed(averageSpeed+"");
			if(averageSpeed > 50) {
				curveDetected.setspeedflag(true);
			} else {
				curveDetected.setspeedflag(false);
			}
			curveData.add(curveDetected);
			speedSum = 0F;
			speedCounter = 0F;
		}
		finalData.setCurveData(curveData);
		finalData.setUIArray(UIArray);
		return finalData;
	}
}
