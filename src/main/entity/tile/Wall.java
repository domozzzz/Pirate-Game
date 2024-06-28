package main.entity.tile;

import main.gfx.SpriteSheet;

public class Wall extends Tile {

	public Wall() {
		super(SpriteSheet.getSpriteImage(2*16, 0, TILE_SIZE, TILE_SIZE));
		
		collision = true;
		breakable = false;
	}
}