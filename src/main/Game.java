package main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;

import javax.swing.JFrame;

import main.entity.mob.Player;
import main.gfx.Display;
import main.screen.menu.PauseMenu;
import main.screen.menu.TitleMenu;
import main.screen.Screen;
import main.screen.level.Level;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	public Screen titleMenu, levelScreen, characterMenu;
	public static boolean paused;

	private final String TITLE = "Pirates";
	public static final int SCREEN_HEIGHT = 256;
	public static final int SCREEN_WIDTH = 256;
	public static final int FPS = 60;

	private int levelNum = 0;
	private int prevLevel = 1;
	private boolean running;
	private int[] pixels;
	private ArrayList<Level> levels = new ArrayList<Level>();
	private Screen screen;
	public PauseMenu pauseMenu;
	private Display display;
	private Input input;
	private BufferedImage displayImage;
	private Player player;
	private Audio audio;

	public Game() {
		setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));

		displayImage = new BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) displayImage.getRaster().getDataBuffer()).getData();

		display = new Display();
		input = new Input(this);
		audio = new Audio();
		player = new Player(this);

		loadScreens();
		loadLevels();

		levelScreen = levels.get(levelNum);
		setScreen(titleMenu);
	}

	public void start() {
		running = true;
		new Thread(this).start();
	}

	public void stop() {
		running = false;
	}

	public void run() {

		long lastTime = System.nanoTime();
		double unprocessedTime = 0;
		double nsPerTick = 1000000000.0 / FPS;

		while (running) {
			long currentTime = System.nanoTime();
			unprocessedTime += (currentTime - lastTime) / nsPerTick;
			lastTime = currentTime;

			if (unprocessedTime >= 1) {
				tick();
				render();
				unprocessedTime--;
			}
		}
	}

	private void tick() {
		if (paused) {
			pauseMenu.tick();
		} else {
			screen.tick();
		}
	}

	private void render() {

		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = displayImage.getGraphics();
		display.clear();
		screen.render(display);

		if (paused) {
			pauseMenu.render(display);
		}

		// switch pixels
		for (int i = 0; i < pixels.length; i++)
			pixels[i] = display.pixels[i];

		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(displayImage, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}

	private void loadScreens() {
		titleMenu = new TitleMenu(this, input);
		//characterMenu = new CharacterMenu(this, input, player);
		pauseMenu = new PauseMenu(this, input);
	}

	private void loadLevels() {
		for (int i = 0; i < Level.maps.length; i++) {
			levels.add(new Level(this, i));
		}
	}

	public Display getDisplay() {
		return display;
	}

	public void setScreen(Screen screen) {
		this.screen = screen;
		if (Level.class.isInstance(screen)) {
			player.setMap(screen.map);
			screen.addPlayer(player);
			((Level) screen).loadEntities();
		} else if (TitleMenu.class.isInstance(screen)) {
			titleMenu.addPlayer(player);
		}

		else {
			screen.removePlayer();
		}
	}

	public void changeLevel(int newLevelNum) {
		prevLevel = levelNum;
		levelNum = newLevelNum;
		levelScreen = levels.get(levelNum);
		setScreen(levelScreen);
		player.setMap(screen.getMap());
	}

	public Player getPlayer() {
		return player;
	}

	public Screen getScreen() {
		return screen;
	}

	public int getLevelNum() {
		return levelNum;
	}

	public int getPrevLevel() {
		return prevLevel;
	}

	public Input getInput() {
		return input;
	}
	
	public Audio getAudio() {
		return audio;
	}

	public static void main(String[] args) {
		Game game = new Game();
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		frame.add(game);
		frame.setTitle(game.TITLE);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		game.start();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
}
