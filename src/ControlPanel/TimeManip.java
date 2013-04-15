package ControlPanel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Core.Time;

public class TimeManip extends JPanel implements ActionListener {
	private final String
			setstring = "Set",
			startstopstring = "Start/Stop",
			resetstring = "Reset";
	private Time time;
	
	private JLabel valuelabel;
	private JTextField valuefield;
	private JButton setbutton;
	private JButton startstop;
	private JButton resetbutton;
	
	public TimeManip(Time time) {
		this.setLayout(new FlowLayout());
		this.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Time Manipulator"), 
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		this.time = time;
		
		valuelabel = new JLabel("Set current time (in seconds): ");
		valuefield = new JTextField(5);
		
		setbutton = new JButton(setstring);
		setbutton.setActionCommand(setstring);
		setbutton.addActionListener(this);
		
		startstop = new JButton(startstopstring);
		startstop.setActionCommand(startstopstring);
		startstop.addActionListener(this);
		
		resetbutton = new JButton(resetstring + " to current round");
		resetbutton.setActionCommand(resetstring);
		resetbutton.addActionListener(this);
		
		add(valuelabel);
		add(valuefield);
		add(setbutton);
		add(startstop);
		add(resetbutton);
	}
	
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case setstring:
			int val = Integer.parseInt(valuefield.getText()); 
			if(val >= 0)
				time.setValue(val);
			break;
		case startstopstring:
			time.startStop();
			break;
		case resetstring:
			time.resetToCurrentRound();
			break;
		}
	}
}
