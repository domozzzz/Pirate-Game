package main.menu;

import main.Game;
import main.Input;
import main.gfx.Display;

public class GameOverMenu extends Menu {
	
	boolean cooldown;
	private int cooldownTicks;

	public GameOverMenu(Game game, Input input) {
		super(game, input);
		MAX_SELECTION = 1;
	}
	
	@Override
	public void tick() {
		if (input.enter && !cooldown) {
			switch (selected) {
			case 0 -> game.reset();
			case 1 -> System.exit(0);
			}
		}
		super.tick();
		
		if (cooldown && 0 >= --cooldownTicks) {
			cooldown = false;
		}
		System.out.println(cooldownTicks);
	}

	public void render(Display display) {
		
		//Title
		
		display.fillColor(0xFFFFFFFF);
		
		if (font != null) {
			font.draw("Game Over", display, 10*8, 5*8);
			
			//Buttons
			font.draw("Replay", display, 10*8, 16*8);
			font.draw("Quit", display, 10*8, 18*8);
			
			switch (selected) {
			case 0 -> font.draw("a", display, 21*8, 16*8);
			case 1 -> font.draw("a", display, 21*8, 18*8);
			}
		}
	}

	public void setCoolDown(int ticks) {
		cooldown = true;
		cooldownTicks = ticks;
	}
}