package entryPoint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class UIDesign {
	
	void display() {
	
		JFrame mainWindow;
		JLabel time;
		mainWindow = new JFrame();
		
		JButton startButton = new JButton("Start Simulation");
		JButton resetButton = new JButton("Reset");
		time = new JLabel("Time offset   |   Speed   |   Steering Angle    |    Yaw Rate   |   Lateral Acc.   |   Longi Acc.   |   GPS Lat/Lon ");
		
		startButton.setBounds(150,100,200,30);
		resetButton.setBounds(400,100,200,30);
		mainWindow.add(startButton);
		mainWindow.add(resetButton);
		
		time.setBounds(30,200,1000,30);
		
		mainWindow.add(time);
		mainWindow.setSize(800, 500);
		mainWindow.setLayout(null);
		mainWindow.setVisible(true);
		
		
		//Action listeners
		resetButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
//				mainWindow.getContentPane().removeAll();
				mainWindow.repaint();
				mainWindow.add(startButton);
				mainWindow.add(resetButton);
				mainWindow.add(time);
				
			}
		});
		
		startButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

	}
}
