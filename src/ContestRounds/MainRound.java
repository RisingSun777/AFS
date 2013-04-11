package ContestRounds;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import Core.Main;
import Core.QuestionSet;
import Helpers.GraphicsUtil;

public class MainRound implements KeyListener {
	private Main mainframe;
	public static int current_question;
	public static String current_round;
	public static ArrayList<QuestionSet> current_list = null;
	
	public MainRound(Main mainframe) {
		this.mainframe = mainframe;
		current_question = 1;
		current_round = "";
	}
	
	public void draw(Graphics2D g2d) {
		g2d.setFont(new Font("Arial", Font.BOLD, 40));
		
		g2d.setColor(Color.BLACK);
		GraphicsUtil.drawString(g2d, "" + current_question, new Rectangle(85 + 3, 65 + 3, 50, 50), GraphicsUtil.Align.North);
		g2d.drawString(current_round, 585 + 3, 185 + 3);
		
		g2d.setColor(Color.WHITE);
		GraphicsUtil.drawString(g2d, "" + current_question, new Rectangle(85, 65, 50, 50), GraphicsUtil.Align.North);
		g2d.drawString(current_round, 585, 185);
	}
	
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_Q:
			showHideQuestion();
			break;
		case KeyEvent.VK_A:
			showHideSolution();
			break;
		case KeyEvent.VK_N:
			if(current_list != null) {
				if(!e.isControlDown()) {
					current_question++;
					if(current_question > current_list.size())
						current_question = current_list.size();
					QuestionSet.setQuestionvisible(false);
					QuestionSet.setSolutionvisible(false);
				}
				else {
					current_question--;
					if(current_question < 1)
						current_question = 1;
					QuestionSet.setQuestionvisible(false);
					QuestionSet.setSolutionvisible(false);
				}
			}
		}
	}
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
	
	public void showHideQuestion() {
		QuestionSet.setQuestionvisible(!QuestionSet.isQuestionvisible());
		if(QuestionSet.isSolutionvisible())
			QuestionSet.setSolutionvisible(false);
	}
	
	public void showHideSolution() {
		QuestionSet.setSolutionvisible(!QuestionSet.isSolutionvisible());
		playSoundSolution();
		if(QuestionSet.isQuestionvisible())
			QuestionSet.setQuestionvisible(false);
	}
	
	private void playSoundSolution() {
		if(QuestionSet.isSolutionvisible())
			mainframe.getSm().play(mainframe.getSm().getAnswer(), 0, true);
	}
}
