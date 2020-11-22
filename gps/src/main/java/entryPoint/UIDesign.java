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
		columnFormat = new JLabel(
				"Time offset   |   Speed   |   Steering Angle    |    Yaw Rate   |   Lateral Acc.   |   Longi Acc.   |   GPS Lat:Lon ");
		JLabel curveDetection = new JLabel("Curve Status");

		startButton.setBounds(150, 100, 200, 30);
		resetButton.setBounds(400, 100, 200, 30);
		columnFormat.setBounds(30, 200, 1000, 30);
		linearValues.setBounds(30, 250, 770, 30);
		curveDetection.setBounds(30, 300, 200, 30);
		curvePrompt.setBounds(150, 300, 250, 30);

		mainWindow.add(startButton);
		mainWindow.add(resetButton);
		mainWindow.add(columnFormat);
		mainWindow.add(linearValues);
		mainWindow.add(curvePrompt);
		mainWindow.add(curveDetection);
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
									finalData.getCurveData().remove(0);
								}
								else {
									curvePrompt.setText(speed+" Right Curve Detected!!");
									finalData.getCurveData().remove(0);
								}
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
