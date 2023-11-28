package main.entity;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.Game;
import main.entity.mob.Mob;
import main.entity.tile.Tile;
import main.gfx.Display;
import main.gfx.SpriteSheet;
import main.gfx.particle.Particle;
import main.screen.level.Level;

public class Bullet extends Entity {
	
	private Game game;
	BufferedImage bulletImage;
	public int bulletPos = 0;
	public int range = 24;
	private int x, y;
	private char xDir, yDir;
	
	public Bullet(Game game, int x, int  y, char xDir, char yDir) {
		
		this.game = game;
		this.x = x;
		this.y = y;
		this.xDir = xDir;
		this.yDir = yDir;
		
		 //icon = SpriteSheet.getSpriteImage(0*16, 12*8, 16, 16);
		 bulletImage = SpriteSheet.getSpriteImage(2*16, 12*8, 16, 16);
		 
		 image = bulletImage;
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
	
	private void handleEntityCollision() {
		if (game.getScreen() instanceof Level) {
			Entity entity = ((Level) game.getScreen()).getEntityAt(new Rectangle(x + rect.x , y + rect.y, rect.width, rect.height));
			if (entity != null && entity instanceof Mob) {
				((Mob) entity).damage(100);
				game.getScreen().removeEntity(this);
				game.levelScreen.addEntity(new Particle(game, Color.RED, 5, 1, 1, x + rect.x + (int)rect.getWidth()/2, y + rect.y + (int)rect.getHeight()/2));
			}
		}
		
		Tile tile = game.levelScreen.getTileAt((int)(x + rect.getX() + rect.getWidth()/2), (int)(y + rect.getY() + rect.getHeight()/2));
		if (tile != null && tile.collision) {
			game.getScreen().removeEntity(this);
			game.levelScreen.addEntity(new Particle(game, Color.BLACK, 5, 1, 1, x + rect.x + (int)rect.getWidth()/2, y + rect.y + (int)rect.getHeight()/2));
		}
	}
	
	@Override
	public void render(Display display) {
		//draw bullets
		display.render(image, x, y, 0);
	}
}
