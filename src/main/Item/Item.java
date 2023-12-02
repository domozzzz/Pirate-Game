package main.Item;

import java.awt.Color;
import java.awt.image.BufferedImage;

import main.Game;
import main.Level;
import main.entity.Entity;
import main.entity.mob.Player;
import main.gfx.Display;

public abstract class Item extends Entity {
	
	protected Color main, trim;
	
	protected boolean cooldown;
	protected int tickCount;
	protected int cooldownEnd;
	public int slot = 0;
	protected Level level;

	protected BufferedImage icon;
	public boolean shooting;
	
	public void use(Player player) {}
	
	public void tick() {
		tickCount++;
		checkCooldown();
	}
	
	public void render(Display display, int x, int y, int flip) {
		display.render(image, x, y, 0);
	}	
	public void setCooldown(int ticks) {
		cooldownEnd = tickCount + ticks;
		cooldown = true;
	}
	
	public void checkCooldown() {
		if (tickCount >= cooldownEnd)
			cooldown = false;
	}
}
