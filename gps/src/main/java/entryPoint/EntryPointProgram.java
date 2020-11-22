package entryPoint;

import java.util.List;

public class EntryPointProgram {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//To get base system directory on any system
//		String pathToDirectory = System.getProperty("user.dir");
		
		//getting can messages file as arguments 
		String canMessages = args[0];
		
		//getting GPS file as arguments 
		String gpsTrack = args[1];
		
		//creating the object of our parser class
		Parser parse = new Parser();
		
		//call method to parse our input files

		List<SensorObj> dataArray = parse.inputFiles(canMessages, gpsTrack);
		SimulatorToDisplaySensorData simulatorToDisplaySensorData = new SimulatorToDisplaySensorData();
		CurveSensorPojo finalData = simulatorToDisplaySensorData.displaySensorInformation(dataArray);
		UIDesign graphicalInterface = new UIDesign();
		graphicalInterface.display(finalData);
//		System.out.println(canMessages);
//		System.out.println(gpsTrack);
	}

//	Error: Could not find or load main class

}
