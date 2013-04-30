package Core;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.*;

import ContestRounds.MainRound;

public class ResourceManager implements Runnable {
	private final String soket_location = "./!RES/Quests/SoKet/";
	private final String banket_location = "./!RES/Quests/BanKet/";
	
	private Main mainframe;
	private HashMap<String, Image> backgroundlist;
	private String currentbackground = "Cover";

	private ArrayList<QuestionSet> doidau = null;
	private ArrayList<QuestionSet> thongdiep = null;
	private ArrayList<QuestionSet> sucmanh = null;
	private ArrayList<QuestionSet> ongxayto = null;
	private ArrayList<QuestionSet> dautruong = null;
	private ArrayList<QuestionSet> hopluc = null;
	
	public ResourceManager() {
		backgroundlist = new HashMap<String, Image>();
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		backgroundlist.put("Main", tk.getImage("./!RES/Backs/Main.png"));
		backgroundlist.put("DauTruong", tk.getImage("./!RES/Backs/DauTruongMain.png"));
		backgroundlist.put("Caesar", tk.getImage("./!RES/Backs/CaesarMain.png"));
		backgroundlist.put("ThongDiep", tk.getImage("./!RES/Backs/ThongDiepMain.png"));
		backgroundlist.put("ToOng", tk.getImage("./!RES/Backs/ToOngMain.png"));
		backgroundlist.put("Cover", tk.getImage("./!RES/Backs/cover.png"));
		backgroundlist.put("MainScore", tk.getImage("./!RES/Backs/MainScore.png"));
	}
	
	public void run() {
		System.out.println("Match loader\n------------");
		loadMatch();
		System.out.println("Match's questions have been loaded successfully.\n------------");
	}
	
	public void loadMatch() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Input match no: ");
		String tran = "Tran " + scanner.nextInt();
		
		initAllQuestionLists(tran);
	}
	
	public void loadMatch(int match) {
		if(match <= 0)
			return;
		String tran = "Tran " + match;
		
		initAllQuestionLists(tran);
		System.out.println("Match's questions have been loaded successfully.\n------------");
	}
	
	public void loadMatch(String match) {
		int temp;
		try {
			temp = Integer.parseInt(match);
		} catch(NumberFormatException e) {
			e.printStackTrace();
			return;
		}
		loadMatch(temp);
	}
	
	private void initAllQuestionLists(String tran) {
		doidau = new ArrayList<QuestionSet>();
		dautruong = new ArrayList<QuestionSet>();
		hopluc = new ArrayList<QuestionSet>();
		ongxayto = new ArrayList<QuestionSet>();		
		//thongdiep = new ArrayList<QuestionSet>();
		//sucmanh = new ArrayList<QuestionSet>();
		
		initQuestionList(doidau, tran, "DD", 8);
		initQuestionList(dautruong, tran, "DT", 12);
		initQuestionList(hopluc, tran, "HL", 5);
		initQuestionList(ongxayto, tran, "OX", 9);
		//initQuestionList(thongdiep, tran, "TD", 10);
		//initQuestionList(sucmanh, tran, "SM", 6);
	}
	
	private void initQuestionList(ArrayList<QuestionSet> list, String tran, String roundcode, int noOfQuestions) {
		String path_prefix = banket_location + tran + "/";
		for(int i = 1; i <= noOfQuestions; i++) {
			QuestionSet temp_question = new QuestionSet(this,
					path_prefix + roundcode + "_" + (i < 10 ? "0" + i : i) + "a.png",
					path_prefix + roundcode + "_" + (i < 10 ? "0" + i : i) + "z.png");
			list.add(temp_question);
		}
	}
	
	public Main getMainframe() {
		return mainframe;
	}

	public void setMainframe(Main mainframe) {
		this.mainframe = mainframe;
	}

	public HashMap<String, Image> getBackgroundlist() {
		return backgroundlist;
	}
	
	public String getCurrentbackground() {
		return currentbackground;
	}

	public void setCurrentbackground(String currentbackground) {
		this.currentbackground = currentbackground;
	}

	public ArrayList<QuestionSet> getDoidau() {
		return doidau;
	}

	public ArrayList<QuestionSet> getThongdiep() {
		return thongdiep;
	}

	public ArrayList<QuestionSet> getSucmanh() {
		return sucmanh;
	}

	/**
	 * @return the ongxayto
	 */
	public ArrayList<QuestionSet> getOngxayto() {
		return ongxayto;
	}

	/**
	 * @return the dautruong
	 */
	public ArrayList<QuestionSet> getDautruong() {
		return dautruong;
	}

	/**
	 * @return the hopluc
	 */
	public ArrayList<QuestionSet> getHopluc() {
		return hopluc;
	}

	public void drawBackground(Graphics2D g2d) {
		if(currentbackground == "" || backgroundlist == null)
			return;
		g2d.drawImage(backgroundlist.get(currentbackground), 0, 0, mainframe);
		mainframe.repaint();
	}
	
	public void drawQuestion(Graphics2D g2d) {
		switch(MainRound.current_round) {
		case "ĐỐI ĐẦU":
			doidau.get(MainRound.current_question - 1).drawQuestion(g2d);
			break;
		case "THÔNG ĐIỆP":
			thongdiep.get(MainRound.current_question - 1).drawQuestion(g2d);
			break;
		case "SỨC MẠNH Đ.ĐỘI":
			sucmanh.get(MainRound.current_question - 1).drawQuestion(g2d);
			break;
		case "ONG XÂY TỔ":
			ongxayto.get(MainRound.current_question - 1).drawQuestion(g2d);
			break;
		case "ĐẤU TRƯỜNG":
			dautruong.get(MainRound.current_question - 1).drawQuestion(g2d);
			break;
		case "HỢP LỰC":
			hopluc.get(MainRound.current_question - 1).drawQuestion(g2d);
			break;
		}
	}
	
	public void drawSolution(Graphics2D g2d) {
		switch(MainRound.current_round) {
		case "ĐỐI ĐẦU":
			doidau.get(MainRound.current_question - 1).drawSolution(g2d);
			break;
		case "THÔNG ĐIỆP":
			thongdiep.get(MainRound.current_question - 1).drawSolution(g2d);
			break;
		case "SỨC MẠNH Đ.ĐỘI":
			sucmanh.get(MainRound.current_question - 1).drawSolution(g2d);
			break;
		case "ONG XÂY TỔ":
			ongxayto.get(MainRound.current_question - 1).drawSolution(g2d);
			break;
		case "ĐẤU TRƯỜNG":
			dautruong.get(MainRound.current_question - 1).drawSolution(g2d);
			break;
		case "HỢP LỰC":
			hopluc.get(MainRound.current_question - 1).drawSolution(g2d);
			break;
		}
	}
}
