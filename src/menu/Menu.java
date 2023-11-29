package main.screen.menu;

import main.Game;
import main.Input;
import main.screen.Screen;

public class Menu extends Screen {
	
	protected int selected = 0;
	protected int MAX_SELECTION = 0;
	protected final Input input;
	
	public Menu(Game game, Input input) {
		super(game);
		this.input = input;
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
}
