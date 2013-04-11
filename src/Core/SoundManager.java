package Core;

import java.io.File;

import javax.sound.sampled.*;

public class SoundManager {
	private Sound answer;
	private Sound main_theme;
	private Sound time;
	private Sound score_summary;
	
	public SoundManager() {
		answer = new Sound("answer.wav");
		main_theme = new Sound("main_theme.wav");
		time = new Sound("time_count.wav");
		score_summary = new Sound("summary.wav");
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
class Sound implements Runnable {
	public static final String filename_prefix = "./!RES/Sounds/";
	AudioInputStream stream;
	Clip clip;
	
	FloatControl volume;
	float default_volume;
	
	public Sound(String filename) {
		try {
			stream = AudioSystem.getAudioInputStream(new File(filename_prefix + filename));
			clip = AudioSystem.getClip();
			clip.open(stream);
			
			volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			default_volume = volume.getValue();
		} catch(Exception e) {e.printStackTrace(); }
	}
	
	/**
	 * For fade ending effect
	 * */
	public void run() {
		float minimum_volume = volume.getMinimum();
		float current_volume = volume.getValue();
		for(float i = current_volume; i > minimum_volume; i -= 5f) {
			volume.setValue(i);
			try {
				Thread.sleep(30);
			} catch(InterruptedException e) {e.printStackTrace(); }
		}
		
		clip.stop();
	}
	
	public void fadeEnding() {
		new Thread(this).start();
	}
}