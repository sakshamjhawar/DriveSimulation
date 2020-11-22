package entryPoint;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.*;

public class UIDesign {

	private List<SensorObj> dataArray = new ArrayList<SensorObj>();
	private List<String> UIArray = new ArrayList<String>();
	SwingWorker<String, String> worker = null;

	void display(CurveSensorPojo finalData) {

//		this.dataArray = dataArray;
		List<CurveInfo> curveData = finalData.getCurveData();
		for(CurveInfo curveInfo : curveData) {

			System.out.println("TimeOffset: "+curveInfo.getTimeOffset());
			System.out.println("TimeOffsetEnd: "+curveInfo.getTimeOffsetEnd());
			System.out.println("averageVehicleSpeed: "+curveInfo.getAverageVehicleSpeed());
			System.out.println("gpsLatLongStart: "+curveInfo.getGpsLatLongStart());
			System.out.println("gpsLatLongEnd: "+curveInfo.getGpsLatLongEnd());
			System.out.println("SpeedFlag: "+(curveInfo.isspeedflag() == true ? "highspeed" : "lowspeed"));
			System.out.println("Direction: "+(curveInfo.isDirection() == true ? "left" : "right"));
			System.out.println("----------------------------------------------------------------------");
		}
		JFrame mainWindow;
		JLabel columnFormat;
		mainWindow = new JFrame();

		JButton startButton = new JButton("Start Simulation");
		JButton resetButton = new JButton("Reset");
		JTextField linearValues = new JTextField();
		JTextField curvePrompt = new JTextField();
		JTextField averageSpeed = new JTextField();
		JTextField gpsStart = new JTextField();
		JTextField gpsEnd = new JTextField();
		JTextField speedTypeField = new JTextField();
		JTextField curveTypeField = new JTextField();
		
		columnFormat = new JLabel(
				"Time offset   |   Speed   |   Steering Angle    |    Yaw Rate   |   Lateral Acc.   |   Longi Acc.   |   GPS Lat:Lon ");
		JLabel curveDetection = new JLabel("Curve Status");
		JLabel lastCurve = new JLabel("Last Curve Stats :");
		JLabel avgSpeedLabel = new JLabel("Average Speed :");
		JLabel startPosition = new JLabel("Curve Start Position :");
		JLabel endPosition = new JLabel("Curve End Position :");
		JLabel speedTypeLabel = new JLabel("Speed Type : ");
		JLabel curveTypeJLabel = new JLabel("Curve Type");

		startButton.setBounds(150, 50, 200, 30);
		resetButton.setBounds(400, 50, 200, 30);
		columnFormat.setBounds(30, 100, 1000, 30);
		linearValues.setBounds(30, 150, 770, 30);
		curveDetection.setBounds(30,200,200,30);
		curvePrompt.setBounds(150,200,250,30);
		lastCurve.setBounds(30,250,250,40);
		avgSpeedLabel.setBounds(50,300,300,40);
		averageSpeed.setBounds(300,300,200,40);
		startPosition.setBounds(50,340,200,40);
		gpsStart.setBounds(300,340,200,40);
		endPosition.setBounds(50,380,200,40);
		gpsEnd.setBounds(300,380,200,40);
		speedTypeLabel.setBounds(50,420,200,40);
		speedTypeField.setBounds(300,420,200,40);
		curveTypeJLabel.setBounds(50,460,200,40);
		curveTypeField.setBounds(300,460,200,40);
		

		mainWindow.add(startButton);
		mainWindow.add(resetButton);
		mainWindow.add(columnFormat);
		mainWindow.add(linearValues);
		mainWindow.add(curvePrompt);
		mainWindow.add(curveDetection);
		mainWindow.add(lastCurve);
		mainWindow.add(averageSpeed);
		mainWindow.add(gpsStart);
		mainWindow.add(gpsEnd);
		mainWindow.add(avgSpeedLabel);
		mainWindow.add(startPosition);
		mainWindow.add(endPosition);
		mainWindow.add(speedTypeLabel);
		mainWindow.add(speedTypeField);
		mainWindow.add(curveTypeJLabel);
		mainWindow.add(curveTypeField);
		
		mainWindow.setSize(800, 500);
		mainWindow.setLayout(null);
		mainWindow.setVisible(true);

		// Action listeners
		resetButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				linearValues.setText(
						"--" + "\t" + "--" + "\t" + "--" + "\t" + "--" + "\t" + "--" + "\t" + "--" + "\t" + "--");
				startButton.setEnabled(true);
				if ( worker != null && !worker.isDone()) {
					worker.cancel(true);

				}
			}
		});

		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
//				System.out.println("asdasdsads");
				worker = new SwingWorker<String, String>() {
					@Override
					protected String doInBackground() throws Exception {
						// TODO Auto-generated method stub
						if(worker != null) {
							startButton.setEnabled(false);
						}
//						SimulatorToDisplaySensorData obj = new SimulatorToDisplaySensorData();
//						UIArray = obj.displaySensorInformation(dataArray);
						for (int i = 0; i < finalData.getUIArray().size(); i++) {
							if(isCancelled()) {
								break;
							}
							linearValues.setText(finalData.getUIArray().get(i));
							String[] offsetFromLinear = finalData.getUIArray().get(i).split("\\s+");
							if(finalData.getCurveData().get(0).getTimeOffset().equals(offsetFromLinear[0])) {
								String speed = finalData.getCurveData().get(0).isspeedflag() == true ? "High Speed" : "Low Speed";
								if(finalData.getCurveData().get(0).isDirection() == true) {
									curvePrompt.setText(speed+" Left Curve Detected!!");
								}
								else {
									curvePrompt.setText(speed+" Right Curve Detected!!");
								}
							}
							if(finalData.getCurveData().get(0).getTimeOffsetEnd().equals(offsetFromLinear[0])) {
								curvePrompt.setText("");
								averageSpeed.setText(finalData.getCurveData().get(0).getAverageVehicleSpeed());
								gpsStart.setText(finalData.getCurveData().get(0).getGpsLatLongStart());
								gpsEnd.setText(finalData.getCurveData().get(0).getGpsLatLongEnd());
								curveTypeField.setText(finalData.getCurveData().get(0).isDirection() == true ? "Left" : "Right");
								speedTypeField.setText(finalData.getCurveData().get(0).isspeedflag() == true ? "High Speed" : "Low Speed");
								finalData.getCurveData().remove(0);
								
							}
							Thread.sleep(1);
						}
						return null;
					}

				};
				worker.execute();

			}
		});

		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
}
