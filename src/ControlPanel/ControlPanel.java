package ControlPanel;

import javax.swing.*;

import Core.Main;
import Core.Statistics;

import java.awt.*;
import java.awt.event.*;

public class ControlPanel extends JPanel implements ActionListener, Runnable {
	public static final int preferredWidth = 1300;
	private Main mainframe;
	private Statistics statistics;

	private TeamNamesPanel team_names_panel;
	private ScorePanel score_panel;
	private BackgroundPanel background_panel;
	private QuestionManipulator question_panel;
	private TimeManip time_panel;
	
	public ControlPanel(Statistics statistics, Main mainframe) {
		this.setLayout(new GridLayout(3, 2));
		JFrame frame = new JFrame("AFS 2013 - Control panel");
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setPreferredSize(new Dimension(preferredWidth, Core.Main.screenheight / 2));
    	
    	this.statistics = statistics;
    	this.mainframe = mainframe;
    	
		team_names_panel = new TeamNamesPanel(this);
		add(team_names_panel);
		score_panel = new ScorePanel(this);
		add(score_panel);
		background_panel = new BackgroundPanel(this);
		add(background_panel);
		question_panel = new QuestionManipulator(this);
		add(question_panel);
		time_panel = new TimeManip(mainframe.getTime());
		add(time_panel);
    	
		frame.add(this);
    	frame.pack();
    	frame.setVisible(true);
    	
    	new Thread(this).start();
	}
	
	public void run() {
		while(true) {
			try {
				score_panel.update();
				question_panel.update();
				Thread.sleep(100);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		
	}

	/**
	 * @return the statistics
	 */
	public Statistics getStatistics() {
		return statistics;
	}

	/**
	 * @return the mainframe
	 */
	public Main getMainframe() {
		return mainframe;
	}
}
