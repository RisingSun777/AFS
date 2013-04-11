package Core;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import ContestRounds.*;
import ControlPanel.ControlPanel;

public class Main extends JFrame implements KeyListener {
	private static final long serialVersionUID = 1L;
	
	//Core vars
	public static final int screenwidth = 1024;
	public static final int screenheight = 768;
	private boolean isfullscreen = false;
	private int PrevX,PrevY,PrevWidth,PrevHeight;
	private ResourceManager rm;
	private BufferedImage backbuffer;
	private Graphics2D g2d;
	private Statistics statistics;
	private Time time;
	private KeyListener currentkeylistener = null;
	private SoundManager sm;
	private ControlPanel controlpanel;
	
	public Toolkit toolkit = Toolkit.getDefaultToolkit();
	
	//contest rounds
	private MainRound mainround;
	private ToOng toong;
	private Caesar caesar;
	private BanLinh banlinh;
	private ThongDiep thongdiep;
	
	public Main() {
		super("AFS 2013");
		this.setSize(screenwidth, screenheight);
		this.setVisible(true);
		addKeyListener(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		rm = new ResourceManager();
		rm.setMainframe(this);
		backbuffer = new BufferedImage(screenwidth, screenheight, BufferedImage.TYPE_INT_RGB);
		g2d = backbuffer.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		statistics = new Statistics("TEAM 1", "TEAM 2", "TEAM 3");
		time = new Time(this, 30);
		addKeyListener(time);
		sm = new SoundManager();

		mainround = new MainRound(this);
		updateKeyListener(mainround);
		toong = new ToOng(this);
		caesar = new Caesar();
		banlinh = new BanLinh();
		thongdiep = new ThongDiep();
		
		sm.play(sm.getMain_theme(), Clip.LOOP_CONTINUOUSLY, true);
		
		controlpanel = new ControlPanel(statistics, this);
	}
	
	public void paint(Graphics g) {
		//super.paint(g);
		rm.drawBackground(g2d);
		if(rm.getCurrentbackground() != "Cover" && rm.getCurrentbackground() != "MainScore") {
			time.draw(g2d);
			statistics.draw(g2d);
		}
		
		switch(rm.getCurrentbackground()) {
		case "Main":
			mainround.draw(g2d);
			if(mainround.current_list != null) {
				rm.drawQuestion(g2d);
				rm.drawSolution(g2d);
			}
			break;
		case "ToOng":
			toong.drawBoard(g2d);
			break;
		case "Caesar":
			caesar.draw(g2d);
			break;
		case "BanLinh":
			banlinh.draw(g2d);
			break;
		case "ThongDiep":
			thongdiep.draw(g2d);
			break;
		case "Cover":
			//will do later
			break;
		case "MainScore":
			statistics.drawInMainScore(g2d);
			break;
		}
		
		g.drawImage(backbuffer, 0, 0, this);
	}
	
	public void updateBackground(String name) {
		switch(name) {
		case "Main":
			updateKeyListener(mainround);
			break;
		case "BanLinh":
			updateKeyListener(banlinh);
			break;
		case "Caesar":
			//This round does not have KeyListener
			break;
		case "ThongDiep":
			updateKeyListener(thongdiep);
			break;
		case "ToOng":
			updateKeyListener(toong);
			break;
		case "MainScore":
			sm.stopAll();
			sm.play(sm.getScore_summary(), Clip.LOOP_CONTINUOUSLY, true);
			break;
		case "Cover":
			break;
		}
		rm.setCurrentbackground(name);
	}
	
	public void updateRoundName(String name) {
		MainRound.current_round = name;
		switch(name) {
		case "ĐỐI ĐẦU":
			MainRound.current_list = rm.getDoidau();
			break;
		case "BẢN LĨNH":
			//MainRound.current_list = rm.get();
			break;
		case "MẬT MÃ CAESAR":
			//This round does not have KeyListener
			break;
		case "THÔNG ĐIỆP":
			MainRound.current_list = rm.getThongdiep();
			break;
		case "ONG XÂY TỔ":
			break;
		case "SỨC MẠNH Đ.ĐỘI":
			MainRound.current_list = rm.getSucmanh();
			break;
		}
		MainRound.current_question = 1;
		QuestionSet.setQuestionvisible(false);
		QuestionSet.setSolutionvisible(false);
	}
	
	private void updateKeyListener(KeyListener a) {
		if(currentkeylistener != null)
			removeKeyListener(currentkeylistener);
		addKeyListener(a);
		currentkeylistener = a;
	}
	
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_END:
			if(e.isShiftDown())
				sm.stopAll();
			else
				if(!sm.getMain_theme().clip.isRunning())
					sm.play(sm.getMain_theme(), Clip.LOOP_CONTINUOUSLY, true);
			break;
		case KeyEvent.VK_ESCAPE:
			WindowEvent wev = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
	        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
			break;
		case KeyEvent.VK_F12:
			if(isfullscreen == false){

                PrevX = getX();
                PrevY = getY();
                PrevWidth = getWidth();
                PrevHeight = getHeight();

                dispose(); //Destroys the whole JFrame but keeps organized every Component                               
                //Needed if you want to use Undecorated JFrame
                //dispose() is the reason that this trick doesn't work with videos
                setUndecorated(true);

                setBounds(-10,-100,getToolkit().getScreenSize().width+30,getToolkit().getScreenSize().height+110);
                setVisible(true);
                isfullscreen = true;
			}
			else {
				setVisible(true);

				setBounds(PrevX, PrevY, PrevWidth, PrevHeight);
				dispose();
				setUndecorated(false);
				setVisible(true);
				isfullscreen = false;
			}
			break;
		case KeyEvent.VK_F1:
			//JOptionPane.showMessageDialog(this,"F1 was pressed");
			if(e.isShiftDown()) {
				updateKeyListener(mainround);
				rm.setCurrentbackground("Main");
			}
			else {
				MainRound.current_round = "ĐỐI ĐẦU";
				MainRound.current_list = rm.getDoidau();
				MainRound.current_question = 1;
				QuestionSet.setQuestionvisible(false);
				QuestionSet.setSolutionvisible(false);
			}
			break;
		case KeyEvent.VK_F2:
			//JOptionPane.showMessageDialog(this,"F2 was pressed");
			if(e.isShiftDown()) {
				updateKeyListener(banlinh);
				rm.setCurrentbackground("BanLinh");
			}
			else {
				MainRound.current_round = "BẢN LĨNH";
				MainRound.current_question = 1;
				QuestionSet.setQuestionvisible(false);
				QuestionSet.setSolutionvisible(false);
			}
			break;
		case KeyEvent.VK_F3:
			//JOptionPane.showMessageDialog(this,"F3 was pressed");
			if(e.isShiftDown()) {
				rm.setCurrentbackground("Caesar");
			}
			else {
				MainRound.current_round = "MẬT MÃ CAESAR";
				MainRound.current_question = 1;
				QuestionSet.setQuestionvisible(false);
				QuestionSet.setSolutionvisible(false);
			}
			break;
		case KeyEvent.VK_F4:
			//JOptionPane.showMessageDialog(this,"F4 was pressed");
			if(e.isShiftDown()) {
				updateKeyListener(thongdiep);
				rm.setCurrentbackground("ThongDiep");
			}
			else {
				MainRound.current_round = "THÔNG ĐIỆP";
				MainRound.current_list = rm.getThongdiep();
				MainRound.current_question = 1;
				QuestionSet.setQuestionvisible(false);
				QuestionSet.setSolutionvisible(false);
			}
			break;
		case KeyEvent.VK_F5:
			//JOptionPane.showMessageDialog(this,"F5 was pressed");
			if(e.isShiftDown()) {
				updateKeyListener(toong);
				rm.setCurrentbackground("ToOng");
			}
			else {
				MainRound.current_round = "ONG XÂY TỔ";
				MainRound.current_question = 1;
				QuestionSet.setQuestionvisible(false);
				QuestionSet.setSolutionvisible(false);
			}
			break;
		case KeyEvent.VK_F6:
			MainRound.current_round = "SỨC MẠNH Đ.ĐỘI";
			MainRound.current_list = rm.getSucmanh();
			MainRound.current_question = 1;
			QuestionSet.setQuestionvisible(false);
			QuestionSet.setSolutionvisible(false);
			break;
		case KeyEvent.VK_F9:
			rm.setCurrentbackground("MainScore");
			sm.stopAll();
			sm.play(sm.getScore_summary(), Clip.LOOP_CONTINUOUSLY, true);
			break;
		case KeyEvent.VK_F10:
			rm.setCurrentbackground("Cover");
			break;
		case KeyEvent.VK_1:
			if(e.isShiftDown())
				statistics.getTeam_a().setScore(statistics.getTeam_a().getScore() + 5);
			if(e.isControlDown())
				statistics.getTeam_a().setScore(statistics.getTeam_a().getScore() - 5);
			break;
		case KeyEvent.VK_2:
			if(e.isShiftDown())
				statistics.getTeam_b().setScore(statistics.getTeam_b().getScore() + 5);
			if(e.isControlDown())
				statistics.getTeam_b().setScore(statistics.getTeam_b().getScore() - 5);
			break;
		case KeyEvent.VK_3:
			if(e.isShiftDown())
				statistics.getTeam_c().setScore(statistics.getTeam_c().getScore() + 5);
			if(e.isControlDown())
				statistics.getTeam_c().setScore(statistics.getTeam_c().getScore() - 5);
			break;
		case KeyEvent.VK_O:
			if(e.isControlDown())
				new Thread(rm).start();
			if(e.isShiftDown())
				new Thread(statistics).start();
			break;
		}
	}
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	
	/**
	 * @return the statistics
	 */
	public Statistics getStatistics() {
		return statistics;
	}
	/**
	 * @param statistics the statistics to set
	 */
	public void setStatistics(Statistics statistics) {
		this.statistics = statistics;
	}
	/**
	 * @return the sm
	 */
	public SoundManager getSm() {
		return sm;
	}

	/**
	 * @return the mainround
	 */
	public MainRound getMainround() {
		return mainround;
	}

	public static void main(String[] args) {
		new Main();
	}

}