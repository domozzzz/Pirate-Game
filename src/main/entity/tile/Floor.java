package main.entity.tile;

import java.awt.Color;

import main.gfx.SpriteSheet;

public class Floor extends Tile {

	public Floor() {
		super(SpriteSheet.getSpriteImage(0*TILE_SIZE, 0*TILE_SIZE, TILE_SIZE, TILE_SIZE));
		
		main = Color.GRAY;
		trim = Color.LIGHT_GRAY;
		
		collision = false;
		breakable = true;
	}
}