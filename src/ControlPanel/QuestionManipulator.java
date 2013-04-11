package ControlPanel;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import ContestRounds.MainRound;
import Core.QuestionSet;

public class QuestionManipulator extends JPanel implements ActionListener {
	private ControlPanel controlpanel;
	private final String shq = "Show/Hide question";
	private final String shs = "Show/Hide solution";
	
	private JButton nextquestion;
	private JButton prevquestion;
	private JTextField currentquestion;
	private JLabel currentquestion_label;
	
	private JButton showhidequestion;
	private JButton showhidesolution;
	
	public QuestionManipulator(ControlPanel controlpanel) {
		this.controlpanel = controlpanel;
		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Question Manipulator"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		
		nextquestion = new JButton(" + ");
		nextquestion.setActionCommand("Next");
		nextquestion.addActionListener(this);
		
		prevquestion = new JButton(" - ");
		prevquestion.setActionCommand("Prev");
		prevquestion.addActionListener(this);
		
		currentquestion = new JTextField(5);
		currentquestion_label = new JLabel("Current question: ");
		
		showhidequestion = new JButton(shq);
		showhidequestion.setActionCommand(showhidequestion.getText());
		showhidequestion.addActionListener(this);
		
		showhidesolution = new JButton(shs);
		showhidesolution.setActionCommand(showhidesolution.getText());
		showhidesolution.addActionListener(this);
		
		add(currentquestion_label);
		add(prevquestion);
		add(currentquestion);
		add(nextquestion);
		add(showhidequestion);
		add(showhidesolution);
	}
	
	public void update() {
		currentquestion.setText("" + MainRound.current_question);
	}
	
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "Next":
			if(MainRound.current_list != null) {
				MainRound.current_question++;
				if(MainRound.current_question > MainRound.current_list.size())
					MainRound.current_question = MainRound.current_list.size();
				QuestionSet.setQuestionvisible(false);
				QuestionSet.setSolutionvisible(false);
				update();
			}
			break;
		case "Prev":
			if(MainRound.current_list != null) {
				MainRound.current_question--;
				if(MainRound.current_question < 1)
					MainRound.current_question = 1;
				QuestionSet.setQuestionvisible(false);
				QuestionSet.setSolutionvisible(false);
				update();
			}
			break;
		case shq:
			controlpanel.getMainframe().getMainround().showHideQuestion();
			break;
		case shs:
			controlpanel.getMainframe().getMainround().showHideSolution();
			break;
		}
	}
}
