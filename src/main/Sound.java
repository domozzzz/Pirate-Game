package main;

import java.applet.Applet;
import java.applet.AudioClip;

@SuppressWarnings("removal")
public class Sound {
	
	public static final Sound hit = new Sound("/res/sounds/roblox/hit.wav");
	public static final Sound death = new Sound("/res/sounds/roblox/death.wav");
	public static final Sound open = new Sound("/res/sounds/roblox/button.wav");
	public static final Sound shoot = new Sound("/res/sounds/roblox/snap.wav");
	public static final Sound pickUp = new Sound("/res/sounds/roblox/bass.wav");
	public static final Sound hurt = new Sound("/res/sounds/roblox/splat.wav");

	
	@SuppressWarnings("removal")
	private AudioClip clip;

	@SuppressWarnings({ "removal", "deprecation" })
	private Sound(String name) {
		try {
			clip = Applet.newAudioClip(Sound.class.getResource(name));
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void play() {
		try {
			new Thread() {
				@SuppressWarnings("deprecation")
				public void run() {
					clip.play();
				}
			}.start();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
