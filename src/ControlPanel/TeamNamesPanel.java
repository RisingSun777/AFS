package ControlPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TeamNamesPanel extends JPanel implements ActionListener {
	public static final String a_string = "Team A";
	public static final String b_string = "Team B";
	public static final String c_string = "Team C";
	
	private ControlPanel controlpanel;
	private NameField a;
	private NameField b;
	private NameField c;
	private JButton setbutton;
	
	public TeamNamesPanel(ControlPanel controlpanel) {
		this.controlpanel = controlpanel;
		setLayout(new FlowLayout());
		a = new NameField(a_string, this);
		b = new NameField(b_string, this);
		c = new NameField(c_string, this);
		
		this.setBorder(
                BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("Teams' names"),
                                BorderFactory.createEmptyBorder(5,5,5,5)));
		
		setbutton = new JButton("Set");
		setbutton.setActionCommand("Set");
		setbutton.addActionListener(this);
		add(setbutton);
	}
	
	public void actionPerformed(ActionEvent e) {
		controlpanel.getStatistics().setTeamsNames(a.textfield.getText(), b.textfield.getText(), c.textfield.getText());
	}
}

class NameField {
	TeamNamesPanel panel;
	final String name;
	JLabel label;
	JTextField textfield;
	
	public NameField(String name, TeamNamesPanel panel) {
		this.name = name;
		this.panel = panel;
		label = new JLabel(name + ": ");
		textfield = new JTextField(10);
		textfield.setActionCommand(name);
		textfield.addActionListener(this.panel);
		this.panel.add(label);
		this.panel.add(textfield);
	}
}
