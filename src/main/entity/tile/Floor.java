package main.entity.tile;

import main.gfx.SpriteSheet;

public class Floor extends Tile {

	public Floor() {
		super(SpriteSheet.getSpriteImage(1*TILE_SIZE, 0*TILE_SIZE, TILE_SIZE, TILE_SIZE));
		
		collision = false;
		breakable = true;
	}
}