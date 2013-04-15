package Core;

import java.awt.*;
import java.awt.event.*;

import javax.sound.sampled.Clip;

import ContestRounds.MainRound;

public class Time implements Runnable, KeyListener {
	private Main mainframe;
	private int value;
	private boolean isStart = false;
	//private final int defaultvalue = 60;
	private Point position;
	
	public Time(Main mainframe, int value) {
		this.mainframe = mainframe;
		this.value = value;
		position = new Point(209, 175);
	}
	
	public synchronized void run() {
		while(isStart) {
			value--;
			if(value <= 0) {
				value = 0;
				isStart = false;
				stopSound();
			}
			
			try {
				Thread.sleep(1000);
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void draw(Graphics2D g2d) {
		g2d.setFont(new Font("Courier", Font.BOLD, 40));
		
		g2d.setColor(Color.BLACK);
		if(value < 10)
			g2d.drawString("" + value, position.x + 3 + 12, position.y + 3);
		else if(value < 100)
			g2d.drawString("" + value, position.x + 3, position.y + 3);
		else
			g2d.drawString("" + value, position.x + 3 - 12, position.y + 3);
		
		g2d.setColor(Color.WHITE);
		if(value < 10)
			g2d.drawString("" + value, position.x + 12, position.y);
		else if(value < 100)
			g2d.drawString("" + value, position.x, position.y);
		else
			g2d.drawString("" + value, position.x - 12, position.y);
	}
	
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_S:
			startStop();
			break;
		case KeyEvent.VK_PAGE_UP:
			if(!e.isShiftDown())
				value++;
			else
				switch(MainRound.current_round) {
				case "ĐỐI ĐẦU":
					value = 45;
					break;
				case "ĐẤU TRƯỜNG":
					value = 45;
					break;
				case "MẬT MÃ CAESAR":
					value = 300;
					break;
				case "THÔNG ĐIỆP":
					value = 30;
					break;
				case "ONG XÂY TỔ":
					value = 30;
					break;
				case "SỨC MẠNH Đ.ĐỘI":
					if(e.isControlDown())
						value = 10;
					else
						value = 60;
					break;
				}
			break;
		case KeyEvent.VK_PAGE_DOWN:
			if(!e.isShiftDown()) {
				value--;
				if(value < 0)
					value = 0;
			}
			else
				value = 0;
			break;
		}
	}
	
	public void startStop() {
		isStart = !isStart;
		if(isStart) {
			mainframe.getSm().stopAll();
			mainframe.getSm().play(mainframe.getSm().getTime(), 0, true);
		}
		else
			stopSound();
			//mainframe.getSm().getTime().fadeEnding();
		new Thread(this).start();
	}
	
	public void resetToCurrentRound() {
		switch(MainRound.current_round) {
		case "ĐỐI ĐẦU":
			value = 45;
			break;
		case "ĐẤU TRƯỜNG":
			value = 45;
			break;
		case "MẬT MÃ CAESAR":
			value = 300;
			break;
		case "THÔNG ĐIỆP":
			value = 30;
			break;
		case "ONG XÂY TỔ":
			value = 30;
			break;
		case "SỨC MẠNH Đ.ĐỘI":
			value = 60;
			break;
		}
	}
	
	public void stopSound() {
		mainframe.getSm().stopAll();
		mainframe.getSm().play(mainframe.getSm().getMain_theme(), Clip.LOOP_CONTINUOUSLY, true);
	}
	
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}

	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
}
