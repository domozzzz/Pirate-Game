package main.entity.tile;

import java.awt.Color;
import java.awt.image.BufferedImage;

import main.Game;
import main.entity.Entity;
import main.gfx.Display;

public class Tile extends Entity {
	
	public static final int TILE_SIZE = 16;
	
	protected Color main = Color.DARK_GRAY;
	protected Color trim = Color.GRAY;
	protected Color extra = Color.LIGHT_GRAY;
	
	protected boolean breakable;
	protected int flippy = 0;
	
	public Tile(BufferedImage image) {
		super();
		this.image = image;
		w = image.getWidth();
		h = image.getHeight();
		
		breakable = false;
	}

	public void tick() {
		super.tick();
	}
	
	public void render(Display display, int x, int y, int flip) {
		display.render(image, x*w, y*h, flippy);
	}
	
	public void event(Game game) {
	}
	
	public boolean isCollision() {
		return collision;
	}

	public void setCollision(boolean collision) {
		this.collision = collision;
	}
	
	public boolean isBreakable() {
		return breakable;
	}
}
