package Core;

import java.awt.*;
import java.util.Scanner;

import Helpers.*;

public class Statistics implements Runnable {
	private Team team_a, team_b, team_c;
	
	private final Point defaultposition;
	private final Point defaultscoreposition;
	private final int defaultdistance;
	
	public Statistics(String team_a, String team_b, String team_c) {
		defaultposition = new Point(40, 718);
		defaultscoreposition = new Point(defaultposition.x + 220, defaultposition.y);
		defaultdistance = 333;
		
		this.team_a = new Team(this, team_a, 0, defaultposition, defaultscoreposition);
		this.team_b = new Team(this, team_b, 0, new Point(defaultposition.x + defaultdistance, defaultposition.y), new Point(defaultscoreposition.x + defaultdistance, defaultscoreposition.y));
		this.team_c = new Team(this, team_c, 0, new Point(defaultposition.x + 2*defaultdistance, defaultposition.y), new Point(defaultscoreposition.x + 2*defaultdistance, defaultscoreposition.y));
	}
	
	public void run() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("---- TEAM NAMES EDITOR ----");
		System.out.print("Input team A's name: ");
		team_a.setName(scanner.next());
		System.out.print("Input team B's name: ");
		team_b.setName(scanner.next());
		System.out.print("Input team C's name: ");
		team_c.setName(scanner.next());
		System.out.println("Names have been set successfully.\n---------------------------");
	}
	
	public void draw(Graphics2D g2d) {
		team_a.draw(g2d);
		team_b.draw(g2d);
		team_c.draw(g2d);
	}
	
	public void drawInMainScore(Graphics2D g2d) {
		team_a.drawInMainScore(g2d, 400, 260);
		team_b.drawInMainScore(g2d, 80, 470);
		team_c.drawInMainScore(g2d, 510, 650);
	}

	public Team getTeam_a() {
		return team_a;
	}

	public void setTeam_a(Team team_a) {
		this.team_a = team_a;
	}

	public Team getTeam_b() {
		return team_b;
	}

	public void setTeam_b(Team team_b) {
		this.team_b = team_b;
	}

	public Team getTeam_c() {
		return team_c;
	}

	public void setTeam_c(Team team_c) {
		this.team_c = team_c;
	}
	
	public void setTeamsNames(String a, String b, String c) {
		team_a.setName(a);
		team_b.setName(b);
		team_c.setName(c);
	}
	
	public int[] getTeamsScores() {
		int[] scores = new int[3];
		scores[0] = team_a.getScore();
		scores[1] = team_b.getScore();
		scores[2] = team_c.getScore();
		
		return scores;
	}
	
	/*
	 * Add/subtract to/from team with index number.
	 * */
	public void score_modify(int index, String operator) {
		if(index < 0 || index > 2)
			return;
		if(!operator.equals("add") && !operator.equals("subtract"))
			return;
		
		switch(index) {
		case 0:
			if(operator.equals("add"))
				team_a.setScore(team_a.getScore() + 5);
			else
				team_a.setScore(team_a.getScore() - 5);
			break;
		case 1:
			if(operator.equals("add"))
				team_b.setScore(team_b.getScore() + 5);
			else
				team_b.setScore(team_b.getScore() - 5);
			break;
		case 2:
			if(operator.equals("add"))
				team_c.setScore(team_c.getScore() + 5);
			else
				team_c.setScore(team_c.getScore() - 5);
			break;
		}
	}
}

class Team {
	Statistics statistics;
	String name;
	int score;
	int score_digits;
	Point nameposition;
	Point scoreposition;
	
	public Team(Statistics s, String name, int score, Point nameposition, Point scoreposition) {
		statistics = s;
		this.name = name;
		this.score = score;
		this.nameposition = nameposition;
		this.scoreposition = scoreposition;
		updateScoreDigits();
	}
	
	public void drawInMainScore(Graphics2D g2d, int x, int y) {
		g2d.setColor(Color.BLACK);
		g2d.setFont(new Font("Arial", Font.BOLD, 50));
		g2d.drawString(name, x + 5, y + 5);
		
		GraphicsUtil.drawString(g2d, "" + score, new Rectangle(x + 290 + 5, y - 60 + 5, 60, 60), GraphicsUtil.Align.North);
		
		if(Helpers.maxOfThree(statistics.getTeam_a().getScore(), statistics.getTeam_b().getScore(), statistics.getTeam_c().getScore()) == this.score)
			g2d.setColor(Color.YELLOW);
		else
			g2d.setColor(Color.WHITE);
		g2d.drawString(name, x, y);
		GraphicsUtil.drawString(g2d, "" + score, new Rectangle(x + 290, y - 60, 60, 60), GraphicsUtil.Align.North);
	}
	
	public void draw(Graphics2D g2d) {
		Font a = new Font("Arial", Font.BOLD, 36);
		Font b = new Font("Courier", Font.BOLD, 44);
		
		g2d.setFont(a);
		g2d.setColor(Color.BLACK);
		g2d.drawString(name, nameposition.x + 3, nameposition.y + 3);
		
		g2d.setFont(b);
		switch(score_digits) {
		case 1:
			g2d.drawString("" + score, scoreposition.x + 3, scoreposition.y + 3);
			break;
		case 2:
			g2d.drawString("" + score, scoreposition.x + 3 - 15, scoreposition.y + 3);
			break;
		case 3:
			g2d.drawString("" + score, scoreposition.x + 3 - 29, scoreposition.y + 3);
			break;
		}
		
		g2d.setFont(a);
		if(Helpers.maxOfThree(statistics.getTeam_a().getScore(), statistics.getTeam_b().getScore(), statistics.getTeam_c().getScore()) == this.score)
			g2d.setColor(Color.YELLOW);
		else
			g2d.setColor(Color.WHITE);
		g2d.drawString(name, nameposition.x, nameposition.y);
		
		g2d.setFont(b);
		switch(score_digits) {
		case 1:
			g2d.drawString("" + score, scoreposition.x, scoreposition.y);
			break;
		case 2:
			g2d.drawString("" + score, scoreposition.x - 15, scoreposition.y);
			break;
		case 3:
			g2d.drawString("" + score, scoreposition.x - 29, scoreposition.y);
			break;
		}
	}
	
	private void updateScoreDigits() {
		if(score >= 0 && score < 10)
			score_digits = 1;
		else if(score > -10 || score < 100)
			score_digits = 2;
		if(score >= 100 || score <= -10)
			score_digits = 3;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
		updateScoreDigits();
	}

	public int getScore_digits() {
		return score_digits;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}