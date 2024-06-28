package main.entity.tile;

import main.gfx.SpriteSheet;

public class Tree extends Tile{
	
	public Tree() {
		super(SpriteSheet.getSpriteImage(0, 2 * 16, TILE_SIZE, TILE_SIZE));
		
		breakable = true;
		collision = true;
	}

}
