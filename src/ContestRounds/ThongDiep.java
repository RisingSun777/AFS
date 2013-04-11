package ContestRounds;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Scanner;

import Helpers.GraphicsUtil;
import Helpers.GraphicsUtil.Align;

public class ThongDiep implements Runnable, KeyListener {
	private final int noOfRows = 6, noOfCols = 6;
	private ArrayList<ArrayList<Cell>> board;
	private Charset[] charset;
	private Point selection;
	private Image card;
	
	private Team teama, teamb, teamc;
	private boolean swapmode = false;
	
	public ThongDiep() {
		board = new ArrayList<ArrayList<Cell>>();
		selection = new Point(0, 0);
		card = Toolkit.getDefaultToolkit().getImage("./!RES/Card.png");
		teama = new Team(new Color(1, 0, 1, 0.3f));
		teamb = new Team(new Color(0, 1, 0, 0.3f));
		teamc = new Team(new Color(0, 0, 1, 0.3f));
		
		charset = new Charset[11];
		charset[0] = new Charset("S3", 1);
		charset[1] = new Charset("FS", 1);
		charset[2] = new Charset("01", 1);
		charset[3] = new Charset("A", 6);
		charset[4] = new Charset("F", 4);
		charset[5] = new Charset("S", 4);
		charset[6] = new Charset("2", 6);
		charset[7] = new Charset("0", 5);
		charset[8] = new Charset("1", 4);
		charset[9] = new Charset("3", 3);
		charset[10] = new Charset("", 1);
		
		for(int i = 0; i < noOfRows; i++) {
			ArrayList<Cell> temp_list = new ArrayList<Cell>();
			for(int j = 0; j < noOfCols; j++) {
				int temp_rand;
				do {
					temp_rand = (int) Math.floor(Math.random()*11);
				} while(charset[temp_rand].value >= charset[temp_rand].maxval);
				charset[temp_rand].value++;
				
				Cell temp_cell = new Cell(this, new Rectangle(60 + Cell.width*j, 220 + Cell.height*i, Cell.width, Cell.height), new Point(i, j), charset[temp_rand].name);
				temp_list.add(temp_cell);
			}
			board.add(temp_list);
		}
	}
	
	private void generateBoard() {
		for(int i = 0; i < charset.length; i++)
			charset[i].value = 0;
		
		for(int i = 0; i < noOfRows; i++) {
			for(int j = 0; j < noOfCols; j++) {
				int temp_rand;
				do {
					temp_rand = (int) Math.floor(Math.random()*11);
				} while(charset[temp_rand].value >= charset[temp_rand].maxval);
				charset[temp_rand].value++;
				board.get(i).get(j).value = charset[temp_rand].name;
				board.get(i).get(j).isVisible = false;
				board.get(i).get(j).claimed = null;
			}
		}
		
		teama.chars_obtained.clear();
		teamb.chars_obtained.clear();
		teamc.chars_obtained.clear();
	}
	
	private void showAll() {
		for(int i = 0; i < noOfRows; i++)
			for(int j = 0; j < noOfCols; j++)
				board.get(i).get(j).isVisible = true;
	}
	
	public void draw(Graphics2D g2d) {
		for(int i = 0; i < board.size(); i++)
			for(int j = 0; j < board.get(i).size(); j++) {
				board.get(i).get(j).draw(g2d);
			}
		
		teama.drawCharsObtained(g2d, 350, 110);
		teamb.drawCharsObtained(g2d, 350, 140);
		teamc.drawCharsObtained(g2d, 350, 170);
	}
	
	public void run() {
		//for moving cards only
		/*int temp = board.get(selection.x).get(selection.y).card_coordinate.y;
		if(board.get(selection.x).get(selection.y).isVisible) {
			for(int i = temp; i >= Cell.notVisibleY; i -= 5) {
				board.get(selection.x).get(selection.y).card_coordinate.y = i;
			}
		}
		else {
			for(int i = temp; i <= Cell.visibleY; i += 5) {
				board.get(selection.x).get(selection.y).card_coordinate.y = i;
			}
		}*/
		
			if(swapmode) {
				Scanner scanner = new Scanner(System.in);
				System.out.println("\n--- Swap mode ---");
				//scanner.useDelimiter("[ \n]");
				System.out.print("Input source team's number: ");
				int src_num = scanner.nextInt();
				System.out.print("Input destination team's number: ");
				int dst_num = scanner.nextInt();
				Team src_t = null, dst_t = null;
				switch(src_num) {
				case 1:
					src_t = teama;
					break;
				case 2:
					src_t = teamb;
					break;
				case 3:
					src_t = teamc;
					break;
				}
				switch(dst_num) {
				case 1:
					dst_t = teama;
					break;
				case 2:
					dst_t = teamb;
					break;
				case 3:
					dst_t = teamc;
					break;
				}
				
				System.out.print("Input source team's charset: ");
				String src_s = scanner.next();
				if(src_s.equals("null"))
					src_s = "";
				
				System.out.print("Input destination team's charset: ");
				String dst_s = scanner.next();
				if(dst_s.equals("null"))
					dst_s = "";
				
				if(swapCharset(src_t, dst_t, src_s, dst_s))
					System.out.println("Swapped successfully.");
				else
					System.out.println("Swapping failed");
				System.out.println("-----------------");
				
				swapmode = false;
			}
	}
	/**
	 * Swap character between 2 teams. Return true if success, false otherwise.
	 * */
	public boolean swapCharset(Team src_t, Team dst_t, String src_s, String dst_s) {
		//System.out.println("src_s = " + src_s);
		//System.out.println("dst_s = " + dst_s);
		
		if(src_t == null || dst_t == null)
			return false;
		if(!src_t.chars_obtained.contains(src_s) || !dst_t.chars_obtained.contains(dst_s) || src_t == dst_t)
			return false;
		String old_src = src_s;
		String old_dst = dst_s;
		src_t.chars_obtained.set(src_t.chars_obtained.indexOf(old_src), old_dst);
		dst_t.chars_obtained.set(dst_t.chars_obtained.indexOf(old_dst), old_src);
		return true;
	}
	
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_UP:
			selection.x--;
			if(selection.x < 0)
				selection.x = 0;
			break;
		case KeyEvent.VK_DOWN:
			selection.x++;
			if(selection.x >= noOfRows)
				selection.x = noOfRows - 1;
			break;
		case KeyEvent.VK_LEFT:
			selection.y--;
			if(selection.y < 0)
				selection.y = 0;
			break;
		case KeyEvent.VK_RIGHT:
			selection.y++;
			if(selection.y >= noOfCols)
				selection.y = noOfCols - 1;
			break;
		case KeyEvent.VK_ENTER:
			//board.get(selection.x).get(selection.y).isVisible = !board.get(selection.x).get(selection.y).isVisible;
			//new Thread(this).start();
			board.get(selection.x).get(selection.y).isVisible = false;
			board.get(selection.x).get(selection.y).claimed = null;
			break;
		case KeyEvent.VK_N:
			generateBoard();
			break;
		case KeyEvent.VK_R:
			showAll();
			break;
		case KeyEvent.VK_1:
			if(!e.isAltDown()) {
				if(e.isShiftDown() == false && e.isControlDown() == false) {
					board.get(selection.x).get(selection.y).isVisible = true;
					board.get(selection.x).get(selection.y).claimed = teama.color;
					teama.chars_obtained.add(board.get(selection.x).get(selection.y).value);
				}
			}
			else {
				if(!teama.chars_obtained.isEmpty())
					teama.chars_obtained.remove(teama.chars_obtained.size() - 1);
			}
			break;
		case KeyEvent.VK_2:
			if(!e.isAltDown()) {
				if(e.isShiftDown() == false && e.isControlDown() == false) {
					board.get(selection.x).get(selection.y).isVisible = true;
					board.get(selection.x).get(selection.y).claimed = teamb.color;
					teamb.chars_obtained.add(board.get(selection.x).get(selection.y).value);
				}
			}
			else {
				if(!teamb.chars_obtained.isEmpty())
					teamb.chars_obtained.remove(teamb.chars_obtained.size() - 1);
			}
			break;
		case KeyEvent.VK_3:
			if(!e.isAltDown()) {
				if(e.isShiftDown() == false && e.isControlDown() == false) {
					board.get(selection.x).get(selection.y).isVisible = true;
					board.get(selection.x).get(selection.y).claimed = teamc.color;
					teamc.chars_obtained.add(board.get(selection.x).get(selection.y).value);
				}
			}
			else {
				if(!teamc.chars_obtained.isEmpty())
					teamc.chars_obtained.remove(teamc.chars_obtained.size() - 1);
			}
			break;
		case KeyEvent.VK_M:
			swapmode = true;
			new Thread(this).start();
			break;
		}
	}
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}

	/**
	 * @return the selection
	 */
	public Point getSelection() {
		return selection;
	}

	/**
	 * @param selection the selection to set
	 */
	public void setSelection(Point selection) {
		this.selection = selection;
	}

	/**
	 * @return the card
	 */
	public Image getCard() {
		return card;
	}

	/**
	 * @param card the card to set
	 */
	public void setCard(Image card) {
		this.card = card;
	}
}

class Team {
	ArrayList<String> chars_obtained;
	Color color;
	
	public Team(Color color) {
		chars_obtained = new ArrayList<String>();
		this.color = color;
	}
	
	public void drawCharsObtained(Graphics2D g2d, int x, int y) {
		Color temp_color = new Color(color.getRed(), color.getGreen(), color.getBlue(), 200);
		g2d.setColor(temp_color);
		g2d.fillRect(x, y, 25, 25);
		g2d.setFont(new Font("Arial", Font.BOLD, 25));
		g2d.setColor(Color.WHITE);
		for(int i = 0; i < chars_obtained.size(); i++)
			g2d.drawString(chars_obtained.get(i), x + 40*i + 40, y + 20);
	}
	
	public void printCharsObtained() {
		if(!chars_obtained.isEmpty()) {
			for(String i: chars_obtained)
				System.out.println(i);
			System.out.println("");
		}
	}
}

class Cell {
	ThongDiep thongdiep;
	static final int width = 150, height = 70;
	static int notVisibleY, visibleY;
	Rectangle shape;
	Point index;
	String value;
	boolean isVisible = false;
	Color claimed = null;
	Point card_coordinate;
	
	public Cell(ThongDiep thongdiep, Rectangle shape, Point index, String value) {
		this.thongdiep = thongdiep;
		this.shape = shape;
		this.index = index;
		this.value = value;
		card_coordinate = new Point(shape.x + 10, shape.y + 5);
		notVisibleY = -70;
		visibleY = card_coordinate.y;
	}
	
	public void draw(Graphics2D g2d) {
		if(claimed != null) {
			g2d.setColor(claimed);
			g2d.fill(shape);
		}
		
		if(index.equals(thongdiep.getSelection())) {
			g2d.setColor(new Color(1.0f, 1.0f, 1.0f, 0.3f));
			g2d.fill(shape);
		}
		
		g2d.setStroke(new BasicStroke(3.0f));
		g2d.setColor(Color.WHITE);
		g2d.draw(shape);
		if(isVisible)
			GraphicsUtil.drawString(g2d, value, shape, Align.North);
		else
			g2d.drawImage(thongdiep.getCard(), card_coordinate.x, card_coordinate.y, null);
	}
}

class Charset {
	String name;
	int value;
	int maxval;
	
	public Charset(String name, int maxval) {
		this.name = name;
		this.maxval = maxval;
		value = 0;
	}
}