package main.entity.tile;

import java.awt.Color;

import main.gfx.SpriteSheet;

public class Water extends Tile {

	public Water() {
		super(SpriteSheet.getSpriteImage(2*SpriteSheet.GRID_SIZE, 0, Tile.TILE_SIZE, Tile.TILE_SIZE));
		
		main = Color.BLUE;
		trim = Color.BLACK;
		
		collision = true;
		breakable = false;
	}
	
	@Override
	public void tick() {
		super.tick();
		if (tickCount % 100 == 0) {
			flippy = 1 - flippy;
		}
	}
}