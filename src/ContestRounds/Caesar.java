package ContestRounds;

import java.io.*;
import java.awt.*;
import java.util.ArrayList;
import Helpers.GraphicsUtil;
import Helpers.GraphicsUtil.Align;

public class Caesar {
	private ArrayList<String> codelist;
	
	public Caesar() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("./!RES/Quests/CaesarCode.txt"));
			codelist = new ArrayList<String>();
			while(true) {
				String s = br.readLine();
				if(s == null)
					break;
				codelist.add(s);
			}
			br.close();
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2d) {
		int temp = 190;
		g2d.setFont(new Font("Arial", Font.BOLD, 36));
		g2d.setColor(Color.WHITE);
		for(int i = 0; i < codelist.size(); i++) {
			
			Rectangle temp_rect = new Rectangle(380, temp, 500, 50);
			g2d.setStroke(new BasicStroke(3.0f));
			g2d.draw(temp_rect);
			
			GraphicsUtil.drawString(g2d, codelist.get(i), temp_rect, Align.North);
			temp += 50;
		}
	}
}
