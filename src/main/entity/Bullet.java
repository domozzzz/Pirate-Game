package main.entity;

import java.awt.Color;
import java.awt.Rectangle;

import main.Level;
import main.Sound;
import main.entity.mob.Mob;
import main.entity.tile.Tile;
import main.gfx.Display;
import main.gfx.SpriteSheet;
import main.gfx.particle.Particle;

public class Bullet extends Entity {
	
	private Level level;
	public int bulletPos = 0;
	public int range = 24;
	private char xDir, yDir;
	
	public Bullet(Level level, int x, int  y, char xDir, char yDir) {
		
		this.level = level;
		this.x = x;
		this.y = y;
		this.xDir = xDir;
		this.yDir = yDir;
		
		image = SpriteSheet.getSpriteImage(2*16, 12*8, 16, 16);

		 createHitbox();
	}
	
	private void createHitbox() {
		rect = new Rectangle();
		rect.x = 7;
		rect.y = 7;	
		rect.width = 3;
		rect.height = 3;
	}
	
	@Override
	public void tick() {
		super.tick();
		
		switch(yDir)  {
			case 'u' -> y-=2;
			case 'd' -> y+=2;
		}
		
		switch(xDir)  {
			case 'l' -> x-=2;
			case 'r' -> x+=2;
		}
		handleEntityCollision();
	}
	
	@Override
	public void render(Display display) {
		display.render(image, x, y, 0);
	}
	
	private void handleEntityCollision() {
		Entity entity = level.getEntityAt(x + rect.x , y + rect.y, rect.width, rect.height);
		
		if (entity != null && entity instanceof Mob) {
			((Mob) entity).damage(100);
			level.removeEntity(this);
			level.addEntity(new Particle(level, Color.RED, 5, getCenterX(), getCenterY()));
		}
		
		Tile tile = level.getTileAt(getCenterX(), getCenterY());
		
		if (tile != null && tile.collision) {
			Sound.hit.play();
			level.removeEntity(this);
			level.addEntity(new Particle(level, Color.BLACK, 5, getCenterX(), getCenterY()));
		}
	}
}
