package Core;
import java.awt.*;

public class QuestionSet {
	private Image question;
	private Image solution;
	private Point coordinate;
	private static boolean questionvisible = false;
	private static boolean solutionvisible = false;
	
	private ResourceManager rm;
	
	public QuestionSet(ResourceManager rm, String questiondir, String solutiondir) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		this.rm = rm;
		question = tk.getImage(questiondir);
		solution = tk.getImage(solutiondir);
		
		coordinate = new Point(120, 243);
	}
	
	public void drawQuestion(Graphics2D g2d) {
		if(questionvisible) {
			//g2d.drawImage(question, coordinate.x, coordinate.y, rm.getMainframe());
			//g2d.drawImage(question, coordinate.x, coordinate.y, coordinate.x + 782, coordinate.y + 375, 0, 0, 2218, 1065, rm.getMainframe());
			g2d.drawImage(question, coordinate.x, coordinate.y, rm.getMainframe());
			rm.getMainframe().repaint();
		}
	}
	
	public void drawSolution(Graphics2D g2d) {
		if(solutionvisible) {
			//g2d.drawImage(solution, coordinate.x, coordinate.y, rm.getMainframe());
			//g2d.drawImage(solution, coordinate.x, coordinate.y, coordinate.x + 782, coordinate.y + 375, 0, 0, 2218, 1065, rm.getMainframe());
			g2d.drawImage(solution, coordinate.x, coordinate.y, rm.getMainframe());
			rm.getMainframe().repaint();
		}
	}

	public Image getQuestion() {
		return question;
	}

	public void setQuestion(Image question) {
		this.question = question;
	}

	public Image getSolution() {
		return solution;
	}

	public void setSolution(Image solution) {
		this.solution = solution;
	}

	public static boolean isQuestionvisible() {
		return questionvisible;
	}

	public static void setQuestionvisible(boolean questionvisible) {
		QuestionSet.questionvisible = questionvisible;
	}

	public static boolean isSolutionvisible() {
		return solutionvisible;
	}

	public static void setSolutionvisible(boolean solutionvisible) {
		QuestionSet.solutionvisible = solutionvisible;
	}
	
}
