package entryPoint;

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
	
	public List<String> displaySensorInformation(List<SensorObj> dataArray) {
		
		List<String> UIArray = new ArrayList<String>();
		
		System.out.println("Current Time"+"\t"+"Vehicle Speed"+"\t"+"SteerAngle"+
				"\t"+"YawRate"+"\t"+"LatAccel"+"\t"+"LongAccel"+"\t"+"GPS Lat/Long");
		for(SensorObj obj : dataArray) {
			currentTime = obj.getTimeOffset();
			if(obj.getType().equals("Displa vehicle speed(km/hr): ")) {
				vehicleSpeed = obj.getValue();
			}
			if(obj.getType().equals("Steering wheel angle(degrees): ")) {
				steerAngle = obj.getValue();
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
			System.out.println(currentTime+"\t"+vehicleSpeed+"\t"+steerAngle+
					"\t"+yawRate+"\t"+latAccel+"\t"+longAccel+"\t"+gpsLatLong+"\r");
			System.out.print("\r");
			String s = currentTime+"\t"+vehicleSpeed+"\t"+steerAngle+
					"\t"+yawRate+"\t"+latAccel+"\t"+longAccel+"\t"+gpsLatLong;
			
			UIArray.add(s);
			
		}
		return UIArray;
	}
}
