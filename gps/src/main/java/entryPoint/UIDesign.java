package entryPoint;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class UIDesign {

	private List<SensorObj> dataArray = new ArrayList<SensorObj>();
	private List<String> UIArray = new ArrayList<String>();
	SwingWorker worker = null;

	void display(List<SensorObj> dataArray) {

		this.dataArray = dataArray;
		JFrame mainWindow;
		JLabel columnFormat;
		mainWindow = new JFrame();

		JButton startButton = new JButton("Start Simulation");
		JButton resetButton = new JButton("Reset");
		JTextField linearValues = new JTextField();
		columnFormat = new JLabel(
				"Time offset   |   Speed   |   Steering Angle    |    Yaw Rate   |   Lateral Acc.   |   Longi Acc.   |   GPS Lat:Lon ");

		startButton.setBounds(150, 100, 200, 30);
		resetButton.setBounds(400, 100, 200, 30);
		columnFormat.setBounds(30, 200, 1000, 30);
		linearValues.setBounds(30, 250, 770, 30);

		mainWindow.add(startButton);
		mainWindow.add(resetButton);
		mainWindow.add(columnFormat);
		mainWindow.add(linearValues);
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
				System.out.println("asdasdsads");
				worker = new SwingWorker<String, String>() {
					@Override
					protected String doInBackground() throws Exception {
						// TODO Auto-generated method stub
						if(worker != null) {
							startButton.setEnabled(false);
						}
						SimulatorToDisplaySensorData obj = new SimulatorToDisplaySensorData();
						UIArray = obj.displaySensorInformation(dataArray);
						for (int i = 0; i < UIArray.size(); i++) {

							linearValues.setText(UIArray.get(i));
							Thread.sleep(100);
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
