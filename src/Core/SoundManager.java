package Core;

import java.io.File;
import java.util.ArrayList;
import javax.sound.sampled.*;

public class SoundManager {
	private Sound answer;
	private Sound main_theme;
	private Sound time;
	private Sound score_summary;
	
	private ArrayList<Sound> system_sounds;
	private ArrayList<Sound> ongxayto_sounds;
	private ArrayList<Sound> doidau_sounds;
	private ArrayList<Sound> thongdiep_sounds;
	private ArrayList<Sound> matma_sounds;
	private ArrayList<Sound> hopluc_sounds;
	private ArrayList<Sound> dautruong_sounds;
	
	public SoundManager() {
		answer = new Sound("answer.wav");
		main_theme = new Sound("main_theme.wav");
		time = new Sound("time_count.wav");
		score_summary = new Sound("summary.wav");
		
		initSoundLists();
	}
	
	private void initSoundLists() {
		system_sounds = new ArrayList<Sound>();
		ongxayto_sounds = new ArrayList<Sound>();
		doidau_sounds = new ArrayList<Sound>();
		thongdiep_sounds = new ArrayList<Sound>();
		matma_sounds = new ArrayList<Sound>();
		hopluc_sounds = new ArrayList<Sound>();
		dautruong_sounds = new ArrayList<Sound>();
		
		system_sounds.add(new Sound("0_Bang diem.wav"));
		system_sounds.add(new Sound("0_Tra loi dung.wav"));
		system_sounds.add(new Sound("0_Tra loi sai.wav"));
		
		ongxayto_sounds.add(new Sound("1_Ong xay to_An quan co.wav"));
		ongxayto_sounds.add(new Sound("1_Ong xay to_Chien thang tuyet doi.wav"));
		ongxayto_sounds.add(new Sound("1_Ong xay to_Doc cau hoi.wav"));
		ongxayto_sounds.add(new Sound("1_Ong xay to_Suy nghi.wav"));
		
		doidau_sounds.add(new Sound("2_Doi dau_Doc cau hoi.wav"));
		doidau_sounds.add(new Sound("2_Doi dau_Suy nghi.wav"));
		
		thongdiep_sounds.add(new Sound("3_Thong diep_Chien thang tuyet doi.wav"));
		thongdiep_sounds.add(new Sound("3_Thong diep_Doc cau hoi.wav"));
		thongdiep_sounds.add(new Sound("3_Thong diep_Doi o chu.wav"));
		thongdiep_sounds.add(new Sound("3_Thong diep_Mo o.wav"));
		thongdiep_sounds.add(new Sound("3_Thong diep_Show bang chon.wav"));
		thongdiep_sounds.add(new Sound("3_Thong diep_Suy nghi.wav"));
		
		matma_sounds.add(new Sound("4_Mat ma.wav"));
		
		hopluc_sounds.add(new Sound("5_Hop luc.wav"));
		
		dautruong_sounds.add(new Sound("6_Dau truong_10s chon phuong an.wav"));
		dautruong_sounds.add(new Sound("6_Dau truong_30s suy nghi.wav"));
		dautruong_sounds.add(new Sound("6_Dau truong_45s suy nghi.wav"));
		dautruong_sounds.add(new Sound("6_Dau truong_75s suy nghi.wav"));
		dautruong_sounds.add(new Sound("6_Dau truong_Doc cau hoi.wav"));
	}
	
	/**
	 *Play a sound, do not stop other running sounds. 
	 * */
	public void play(Sound sound, int loop_flag, boolean playFromBeginning) {
		if(playFromBeginning)
			sound.clip.setFramePosition(0);
		sound.clip.loop(loop_flag);
	}
	
	public void stopAll() {
		if(answer.clip.isRunning())
			answer.clip.stop();
		if(main_theme.clip.isRunning())
			main_theme.clip.stop();
		if(time.clip.isRunning())
			time.clip.stop();
		if(score_summary.clip.isRunning())
			score_summary.clip.stop();
	}

	/**
	 * @return the answer
	 */
	public Sound getAnswer() {
		return answer;
	}

	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(Sound answer) {
		this.answer = answer;
	}

	/**
	 * @return the main_theme
	 */
	public Sound getMain_theme() {
		return main_theme;
	}

	/**
	 * @param main_theme the main_theme to set
	 */
	public void setMain_theme(Sound main_theme) {
		this.main_theme = main_theme;
	}

	/**
	 * @return the time
	 */
	public Sound getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(Sound time) {
		this.time = time;
	}

	/**
	 * @return the score_summary
	 */
	public Sound getScore_summary() {
		return score_summary;
	}

	/**
	 * @param score_summary the score_summary to set
	 */
	public void setScore_summary(Sound score_summary) {
		this.score_summary = score_summary;
	}
}
class Sound {
	public static final String filename_prefix = "./!RES/Sounds/";
	AudioInputStream stream;
	Clip clip;
	String name;
	
	FloatControl volume;
	float default_volume;
	
	public Sound(String filename) {
		try {
			stream = AudioSystem.getAudioInputStream(new File(filename_prefix + filename));
			clip = AudioSystem.getClip();
			clip.open(stream);
			
			volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			default_volume = volume.getValue();
			
			name = filename;
		} catch(Exception e) {e.printStackTrace(); }
	}
	
	public String toString() {
		return name;
	}
}