package ControlPanel;

import java.awt.Toolkit;
import java.awt.event.*;

import javax.swing.*;

public class ScorePanel extends JPanel {
	ControlPanel controlpanel;
	private TeamScore a;
	private TeamScore b;
	private TeamScore c;
	
	public ScorePanel(ControlPanel parent) {
		this.controlpanel = parent;
		a = new TeamScore(TeamNamesPanel.a_string, this);
		b = new TeamScore(TeamNamesPanel.b_string, this);
		c = new TeamScore(TeamNamesPanel.c_string, this);
		
		this.setBorder(
                BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("Teams' scores"),
                                BorderFactory.createEmptyBorder(5,5,5,5)));
	}
	
	public void update() {
		int[] temp = controlpanel.getStatistics().getTeamsScores();
		
		a.score_value.setText("" + temp[0]);
		b.score_value.setText("" + temp[1]);
		c.score_value.setText("" + temp[2]);
	}
}

class TeamScore implements ActionListener {
	ScorePanel scorepanel;
	final String name;
	JLabel label;
	JTextField score_value;
	JButton subtract;
	JButton add;
	
	public TeamScore(String name, ScorePanel scorepanel) {
		this.name = name;
		this.scorepanel = scorepanel;
		score_value = new JTextField(5);
		label = new JLabel(name + ": ");
		
		add = new JButton(" + ");
		add.setActionCommand("add");
		add.addActionListener(this);
		
		subtract = new JButton(" - ");
		subtract.setActionCommand("subtract");
		subtract.addActionListener(this);
		
		scorepanel.add(label);
		scorepanel.add(subtract);
		scorepanel.add(score_value);
		scorepanel.add(add);
	}
	
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "add":
			addScore();
			break;
		case "subtract":
			subtractScore();
			break;
		}
		
		scorepanel.update();
	}
	
	private void addScore() {
		switch(name) {
		case TeamNamesPanel.a_string:
			scorepanel.controlpanel.getStatistics().score_modify(0, "add");
			break;
		case TeamNamesPanel.b_string:
			scorepanel.controlpanel.getStatistics().score_modify(1, "add");
			break;
		case TeamNamesPanel.c_string:
			scorepanel.controlpanel.getStatistics().score_modify(2, "add");
			break;
		}
	}
	
	private void subtractScore() {
		switch(name) {
		case TeamNamesPanel.a_string:
			scorepanel.controlpanel.getStatistics().score_modify(0, "subtract");
			break;
		case TeamNamesPanel.b_string:
			scorepanel.controlpanel.getStatistics().score_modify(1, "subtract");
			break;
		case TeamNamesPanel.c_string:
			scorepanel.controlpanel.getStatistics().score_modify(2, "subtract");
			break;
		}
	}
}
