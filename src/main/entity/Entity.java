package main.entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.Game;
import main.gfx.Display;

public class Entity {
	
	public static final int TILE_SIZE = 16;
	public int x, y, w, h;
	public boolean collision;
	public boolean cooldown;
	public int cooldownTicks;
	
	protected int tickCount;
	protected BufferedImage image;
	public Rectangle rect;
	protected int flip = 0;

	public Entity(BufferedImage image, int x, int y) {
		this.image = image;
		this.x = x;
		this.y = y;
	}
	public Entity() {

	}
	public void tick() {
		tickCount++;
		checkCooldown();
	}
	public void setCooldown(int ticks) {
		cooldownTicks += ticks;
		cooldown = true;
	}
	
	private void checkCooldown() {
		if (cooldownTicks <= 0) {
			cooldown = false;
		} else {
			cooldownTicks--;
		}
	}
	
	public void render(Display display) {
		display.render(image, x, y, 0);
	}
	
	public boolean isCooldown() {
		return cooldown;
	}
	
	public BufferedImage getImage() {return image;}
	
	public void setImage(BufferedImage image) {this.image = image;}
	
	public void event(Game game) {
	}
}