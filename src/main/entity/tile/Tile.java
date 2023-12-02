package main.entity.tile;

import java.awt.image.BufferedImage;

import main.Game;
import main.Level;
import main.entity.Entity;
import main.gfx.Display;

public abstract class Tile extends Entity {
	
	protected boolean breakable;
	
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
	
	public void render(Display display, int x, int y) {
		display.render(image, x*w, y*h, flip);
	}
	
	public void event(Game game) {}
	
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
