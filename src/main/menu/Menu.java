package main.menu;

import main.Game;
import main.Input;
import main.gfx.Display;
import main.gfx.Font;

public abstract class Menu {
	
	protected int selected = 0;
	protected int MAX_SELECTION = 0;
	protected final Input input;
	protected Game game;
	protected Font font;
	
	public Menu(Game game, Input input) {
		this.game = game;
		this.input = input;
		this.font = game.getDisplay().getFont();
	}

	public void tick() {
		if (input.up)
			selected--;
			
		if (input.down)
			selected++;
		
		if (selected < 0) 
			selected = MAX_SELECTION;
		
		if (selected > MAX_SELECTION)
			selected = 0;
		
		input.clear();
	}

	public abstract void render(Display display);
}
