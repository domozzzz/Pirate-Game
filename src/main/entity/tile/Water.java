package main.entity.tile;

import main.Game;
import main.gfx.SpriteSheet;

public class Water extends Tile {

	public Water() {
		super(SpriteSheet.getSpriteImage(2*SpriteSheet.GRID_SIZE, 0, Tile.TILE_SIZE, Tile.TILE_SIZE));
		
		collision = true;
		breakable = false;
	}
	
	@Override
	public void tick() {
		super.tick();
		if (tickCount % 100 == 0) {
			flip = 1 - flip;
		}
	}

	@Override
	public void event(Game game) {
	}
}