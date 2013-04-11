package ContestRounds;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import Helpers.GraphicsUtil;

public class BanLinh implements KeyListener {
	private ArrayList<ArrayList<SelectionRectangle>> list;
	public static final int horizontallength = 4, verticallength = 3;
	private Point selection;
	
	public BanLinh() {
		list = new ArrayList<ArrayList<SelectionRectangle>>();
		
		int tempx = 265, tempy = 308; //all subtract by 5, this is the coordinate of the first cell
		for(int i = 0; i < verticallength; i++) {
			ArrayList<SelectionRectangle> templist = new ArrayList<SelectionRectangle>();
			for(int j = 0; j < horizontallength; j++) {
				SelectionRectangle temp = new SelectionRectangle(tempx + j*SelectionRectangle.width + 5, tempy + i*SelectionRectangle.height + 5, new Point(i, j));
				templist.add(temp);
			}
			list.add(templist);
		}
		
		selection = new Point(0, 0);
	}
	
	public void draw(Graphics2D g2d) {
		list.get(selection.x).get(selection.y).fill(g2d);
		
		g2d.setColor(Color.WHITE);
		g2d.setStroke(new BasicStroke(5.0f));
		for(int i = 0; i < list.size(); i++)
			for(int j = 0; j < list.get(i).size(); j++) {
				g2d.draw(list.get(i).get(j).shape);
				list.get(i).get(j).drawNumber(g2d);
			}
		
		drawFirstCol(g2d);
		g2d.setTransform(new AffineTransform());
	}
	
	private void drawFirstCol(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		g2d.setStroke(new BasicStroke(5.0f));
		g2d.setFont(new Font("Arial", Font.BOLD, 40));
		
		for(int i = 0; i < 3; i++) {
			Rectangle temp_rect = new Rectangle(265 - SelectionRectangle.width + 5, 308 + 5 + i*SelectionRectangle.height, SelectionRectangle.width, SelectionRectangle.height);
			
			g2d.setColor(Color.BLACK);
			Rectangle temp_rect_shad = new Rectangle(temp_rect);
			temp_rect_shad.x += 3; temp_rect_shad.y += 3;
			g2d.translate(0, 15);
			GraphicsUtil.drawString(g2d, i*10 + " điểm", temp_rect_shad, GraphicsUtil.Align.North);
			g2d.setColor(Color.WHITE);
			GraphicsUtil.drawString(g2d, i*10 + " điểm", temp_rect, GraphicsUtil.Align.North);
			g2d.setTransform(new AffineTransform());
			g2d.draw(temp_rect);
		}
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
			if(selection.x >= verticallength)
				selection.x = verticallength - 1;
			break;
		case KeyEvent.VK_LEFT:
			selection.y--;
			if(selection.y < 0)
				selection.y = 0;
			break;
		case KeyEvent.VK_RIGHT:
			selection.y++;
			if(selection.y >= horizontallength)
				selection.y = horizontallength - 1;
			break;
		case KeyEvent.VK_ENTER:
			list.get(selection.x).get(selection.y).isvisible = !list.get(selection.x).get(selection.y).isvisible; 
			break;
		}
	}
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
}

class SelectionRectangle {
	static final int width = 168, height = 82;
	Rectangle shape;
	Point index;
	boolean isvisible = true;
	
	public SelectionRectangle(int x, int y, Point index) {
		shape = new Rectangle(x, y, width, height);
		this.index = index;
	}
	
	public void fill(Graphics2D g2d) {
		g2d.setColor(new Color(1.0f, 0, 1.0f, 0.3f));
		g2d.fill(shape);
	}
	
	public void drawNumber(Graphics2D g2d) {
		if(isvisible) {
			g2d.setFont(new Font("Arial", Font.BOLD, 60));
			Integer number = BanLinh.horizontallength*index.x + index.y + 1;
			String s = number > 9 ? number.toString() : "0" + number.toString();
			
			g2d.setColor(Color.BLACK);
			g2d.drawString(s, shape.x + 52 + 3, shape.y + 62 + 3);
			
			g2d.setColor(Color.WHITE);
			g2d.drawString(s, shape.x + 52, shape.y + 62);
		}
	}
}