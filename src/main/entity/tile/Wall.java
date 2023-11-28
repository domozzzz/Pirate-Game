package main.entity.tile;

import java.awt.Color;

import main.gfx.SpriteSheet;

public class Wall extends Tile {

	public Wall() {
		super(SpriteSheet.getSpriteImage(2*16, 0, TILE_SIZE, TILE_SIZE));
		
		main = Color.BLUE;
		trim = Color.BLACK;
		
		collision = true;
		breakable = false;
	}
}