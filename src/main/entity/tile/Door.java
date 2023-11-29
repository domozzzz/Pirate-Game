package main.entity.tile;

import main.Game;
import main.gfx.SpriteSheet;

public class Door extends Tile {
	
	private final String OPEN_SOUND = "/res/sounds/roblox/button.wav";

	public Door() {
		super(SpriteSheet.getSpriteImage(0, 2*SpriteSheet.GRID_SIZE, Tile.TILE_SIZE, Tile.TILE_SIZE));
		
		collision = true;
		breakable = false;
	}	
	
	@Override
	public void event(Game game) {
		if (!isCooldown()) {
			game.nextLevel();
			game.getAudio().playAudio(OPEN_SOUND);
			setCooldown(20);
		}
	}	
}
