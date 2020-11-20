package entryPoint;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Parser {
	
	//global custom data structure to store all the data from can messages and GPS track file
	private List<SensorObj> dataArray = new ArrayList<SensorObj>();
	
	//base function to call subsequent functions
	public List<SensorObj> inputFiles(String canMessages, String gpsTrack) {
		
		//first parsing can messages
		readAndParseCanMessages(canMessages);
		//then parsing gps track file
		readAndParseGpsTrack(gpsTrack);
		//sorting all the data as per time
		sortTheListAsPerTime();
		//displaying the data and writing it to output file
//		displayData();
		UIDesign graphicalInterface = new UIDesign();
		graphicalInterface.display();
		return dataArray;
	}
	
	private void displayData() {
		// TODO Auto-generated method stub
		
		//creating a output file where we can store our output
		File file = null;
	    BufferedWriter writer = null;
		try {
			
			//getting path of the specified file
			URL path = Parser.class.getResource("output.txt");
			file = new File(path.getFile());
			//creating new file and attaching it with bufferwriter
			file.createNewFile();
			writer = new BufferedWriter(new FileWriter(file));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			writer.write("TimeOfSet" + "\t" + "\t" + "\t" + "Sensor Data" + "\t" + "\t"
		+ "\t" + "\t" + "Values" + "\n"+ "\n");
		
		//traversing through the list using foreach loop
			for(SensorObj data : dataArray) {
				
				//outputing the data on console
				
//				System.out.print(data.toString());
				//writing the data to file
				writer.write(data.toString());
			}
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//function to sort out final list based on timeoffset
	private void sortTheListAsPerTime() {
		
		// TODO Auto-generated method stub
		
		//declaring our custom comparator to compare sensorObj
		Comparator<SensorObj> comp = new Comparator<SensorObj>() {
			
			//comparing the two object
			public int compare(SensorObj firstValue, SensorObj secondValue) {
				float floatOneTime = Float.parseFloat(firstValue.getTimeOffset());
				float floatSecondTime = Float.parseFloat(secondValue.getTimeOffset());
				if(floatOneTime == floatSecondTime) {
					return 0;
				}
				return floatOneTime > floatSecondTime ? 1 : -1;
			}
		};
		
		//using inbuilt sort and providing it with list and comparator
		Collections.sort(dataArray, comp);
	}
	
	//method to parse our gps data
	private void readAndParseGpsTrack(String gpsTrack) {
		// TODO Auto-generated method stub
		
		File file = null; 
		  
		try {
			URL path = Parser.class.getResource("19_GPS_Track.htm");
			file = new File(path.getFile());
			BufferedReader br = new BufferedReader(new FileReader(file));
			
			// eachline will be used as variable to store each line from file
			String eachLine;
			
			/* timestamp to attache gps data with received time starting at 0ms and then incrementing 
			 * at 1000 ms*/
			int timeStamp = 0;
			try {
				
				//loop that traverse through every line of the file
				while ((eachLine = br.readLine()) != null) {
					
					//trimming the space at the beginning of line
					eachLine = eachLine.trim();
					
					/* we only want line which contain latitude longitude so using contains method to
					 * check that*/
					if(eachLine.contains("new GLatLng( ")) {
						
						/*after fitering the lines with lat long we use regex to divide the lines 
						 * in 3 parts based on opening brackets and closing brackets so now
						 * our array with first index will contain both latitude and longitude*/
						String coordinates = eachLine.split("\\(|\\)")[1];
						
						//splitting the latitude and longitude on ,
						String latLong[] = coordinates.trim().split(",");
						
						//getting latitude at 0 index
						String latitude = latLong[0].trim();
						//getting longitude at 1 index
						String longitude = latLong[1].trim();
						
						//variable with lat long
						String concatedLatLong = latitude + " : " + longitude;
						
						//for formatting reasons no logic
						String timeStampString = timeStamp+"";
						
						if(timeStampString.length() < 2) {
							timeStampString = timeStampString + "000";
						}
						
						//custom data object to store latitude longitude and time
						SensorObj obj = new SensorObj(timeStampString, concatedLatLong, "GPS data(Latitude:Longitude)");
						//adding it to the list
						dataArray.add(obj);
						
						//timestamp increment by 1000
						timeStamp += 1000;
//						System.out.println(latitude+":"+longitude);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	//method to parse can messages data
	private void readAndParseCanMessages(String canMessages) {
		
		File file = null; 
		  
		try {
			
			//getting the file path of canmessages file put in current working directory
			URL path = Parser.class.getResource("19_CANmessages.trc");
			file = new File(path.getFile());
			BufferedReader br = new BufferedReader(new FileReader(file));
			String eachLine; 
			try {
				
				//counter to ingnore first 13 lines
				int ignoreLineCounter = 0;
				while ((eachLine = br.readLine()) != null) {
					++ignoreLineCounter;
					
					//condition to ignore lines
					if(ignoreLineCounter <= 13) {
						continue;
					}
					
					//splitting the input based on spaces
				    String inputData[] = eachLine.trim().split("\\s+");
//				    System.out.println(inputData[0]);
				    
				    //calling different methods based on frame if
				    if(inputData[3].equals("0003")) {
				    	
				    	//if 0003 call steering wheel methods
				    	steeringWheelAngle(inputData);
				    } else if(inputData[3].equals("019F")) {
				    	
				    	//if 019F call vehicle speed method
				    	vehicleSpeed(inputData);
				    } else if(inputData[3].equals("0245")) {
				    	
				    	////if 0245 call yaw rate data, longitudinal acceleration and lateral accele
				    	vehicleYawRate(inputData);
				    	longitudinalAcceleration(inputData);
				    	lateralAcceleration(inputData);
				    }
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	/*lateral acceleration method task would be the first extract all hex data bytes from input data
	 * array obtained from file and call stringToFinalValue method to get the final answer in float
	 * after doing multiplication with step size and subtraction with value range*/
	private void lateralAcceleration(String[] inputData) {
		// TODO Auto-generated method stub
		String firstByte = inputData[10];
		String secondByte = "";
		
		String totalBytes = firstByte + secondByte;
		
		/*method need 4 variables first is the extracted bytes, second is the value with which we will
		 * AND our bytes and then the multiplication value and subtraction value*/
		Float finalValue = stringToFinalValue(totalBytes, 255, 0.08f, 10.24f);
		
		/* This method will take time, float value and textinfo as input and will generate object of 
		 * our custom datatype and add it to arraylist*/ 
		objectGenerator(finalValue, inputData[1], "Vehicle lat accele(m/sec^2): ");
		
//		System.out.println(finalValue);
	}
	
	// same method for vehicle speed
	private void vehicleSpeed(String[] inputData) {
		// TODO Auto-generated method stub
		String firstByte = inputData[5];
		String secondByte = inputData[6];
		
		String totalBytes = firstByte + secondByte;
		
		/*method need 4 variables first is the extracted bytes, second is the value with which we will
		 * AND our bytes and then the multiplication value and subtraction value*/
		Float finalValue = stringToFinalValue(totalBytes, 4095, 0.1f, 0f);
		
		/* This method will take time, float value and textinfo as input and will generate object of 
		 * our custom datatype and add it to arraylist*/ 
		objectGenerator(finalValue, inputData[1], "Displa vehicle speed(km/hr): ");
		
//		System.out.println(finalValue);
	}
	
	// same method for longitudinal acceleration
	private void longitudinalAcceleration(String[] inputData) {
		// TODO Auto-generated method stub
		String firstByte = inputData[9];
		String secondByte = "";
		
		String totalBytes = firstByte + secondByte;
		
		/*method need 4 variables first is the extracted bytes, second is the value with which we will
		 * AND our bytes and then the multiplication value and subtraction value*/
		Float finalValue = stringToFinalValue(totalBytes, 255, 0.08f, 10.24f);
		
		/* This method will take time, float value and textinfo as input and will generate object of 
		 * our custom datatype and add it to arraylist*/ 
		objectGenerator(finalValue, inputData[1], "Vehicle longi accele(m/sec^2): ");
		
//		System.out.println(finalValue);
	}

	// same method for vehicle yaw rate
	private void vehicleYawRate(String[] inputData) {
		// TODO Auto-generated method stub
		String firstByte = inputData[5];
		String secondByte = inputData[6];
		
		String totalBytes = firstByte + secondByte;
		
		/*method need 4 variables first is the extracted bytes, second is the value with which we will
		 * AND our bytes and then the multiplication value and subtraction value*/
		Float finalValue = stringToFinalValue(totalBytes, 65535, 0.01f, 327.68f);
		
		/* This method will take time, float value and textinfo as input and will generate object of 
		 * our custom datatype and add it to arraylist*/ 
		objectGenerator(finalValue, inputData[1], "Vehicle yaw rate(degree/sec):  ");
		
//		System.out.println(finalValue);
	}
	
	// same method for steering wheel angle
	private void steeringWheelAngle(String[] inputData) {
		// TODO Auto-generated method stub
		String firstByte = inputData[5];
		String secondByte = inputData[6];
		
		String totalBytes = firstByte + secondByte;
		
		/*method need 4 variables first is the extracted bytes, second is the value with which we will
		 * AND our bytes and then the multiplication value and subtraction value*/
		Float finalValue = stringToFinalValue(totalBytes, 16383, 0.5f, 2048f);
		
		/* This method will take time, float value and textinfo as input and will generate object of 
		 * our custom datatype and add it to arraylist*/ 
		objectGenerator(finalValue, inputData[1], "Steering wheel angle(degrees): ");
		
//		System.out.println(finalValue);
	}
	
	/* this method AND's the padder with totalbytes and then multiply the stepsize and subtracts
	 * value range */
	private Float stringToFinalValue(String totalBytes, Integer padder, float stepSize, float valueRange) {
		
		Integer convertedDecimal = Integer.parseInt(totalBytes,16);
		Integer afterRemovingBits = convertedDecimal & padder;
		Float finalValue = afterRemovingBits * stepSize - valueRange;
		
		return finalValue;
	}
	
	// This method generate custom data type object and add then to the list
	private void objectGenerator(Float finalValue, String timeOffset, String type) {
		
		String valueInString = finalValue.toString();
		
		//Just for formating purpose no logic here
		if(timeOffset.length() < 4) {
			timeOffset = timeOffset + "0";
		}
		
		SensorObj obj = new SensorObj(timeOffset, valueInString, type);
		dataArray.add(obj);
	}
}
