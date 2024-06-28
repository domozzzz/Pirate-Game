package main.menu;

import main.Game;
import main.Input;
import main.gfx.Display;

public class TitleMenu extends Menu {

	public TitleMenu(Game game, Input input) {
		
		super(game, input);
		MAX_SELECTION = 2;
	}
	
	@Override
	public void tick() {
		if (input.enter) {
			switch (selected) {
			case 0 -> game.setMenu(null);
			case 1 -> game.setMenu(game.characterMenu);
			case 2 -> System.exit(0);
			}
		}
		super.tick();
	}

	public void render(Display display) {
		
		//Title
		
		display.fillColor(0xFFFFFFFF);
		
		if (font != null) {
			font.draw("Pirates", display, 10*8, 5*8);
			
			//Buttons
			font.draw("Start", display, 10*8, 16*8);
			font.draw("Settings", display, 10*8, 18*8);
			font.draw("Quit", display, 10*8, 20*8);
			
			switch (selected) {
			case 0 -> font.draw("a", display, 21*8, 16*8);
			case 1 -> font.draw("a", display, 21*8, 18*8);
			case 2 -> font.draw("a", display, 21*8, 20*8);
			}
		}
	}
}