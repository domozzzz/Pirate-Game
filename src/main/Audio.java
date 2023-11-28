package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Audio {
	
	private Clip clip;
	
	public void playAudio(String path) {
		setFile(path);
		clip.start();
	}
	
	private void setFile(String path) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getResource((path)));
			clip = AudioSystem.getClip();
			clip.open(ais);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loopAudio() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stopAudio() {
		clip.stop();
	}

}
