package main.entity.mob;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.Game;
import main.Level;
import main.Sound;
import main.entity.Entity;
import main.gfx.Display;
import main.gfx.SpriteSheet;

public abstract class Mob extends Entity {
	
	protected Level level;
	protected int speed;
	protected int hp;
	protected BufferedImage front, back, side, frontWalk, backWalk,
	sideWalk, heart, halfHeart, emptyHeart, bubble, hatImage, sideItem, sideWalk2;
	protected boolean walking;
	protected char lastDir;
	protected int flip;
	protected static final int SPRITE_SIZE = 16;
	
	public Mob() {
		initHitbox();
	}
	
	@Override
	public void render(Display display) {
		display.render(image, x, y, flip);
	}
	
	private void initHitbox() {
		rect = (new Rectangle());
		rect.x = 2;
		rect.y = 8;	
		rect.width = 11;
		rect.height = 7;
	}
	
	public Rectangle getRect() {
		return rect;
	}
	
	protected boolean isCollision() {
		if ((level).getTileAt(x + rect.x, y + rect.y).isCollision()
				|| level.getTileAt(x + rect.x + rect.width, y + rect.y + rect.height).isCollision()
				|| level.getTileAt(x + rect.x + rect.width, y + rect.y).isCollision() 
				|| level.getTileAt(x + rect.x, y + rect.y + rect.height).isCollision()) {
			return true;
		}
		return false;
	}

	@Override
	public void tick() {
		super.tick();
		isDead();
	}
	
	public int getHp() {
		return hp;
	}
	
	public void damage(int damage) {
		hp -= damage;
	}
	
	public void heal(int hp) {
		this.hp += hp;
	}
	
	public int getCenterX() {
		return x + rect.x + (int)rect.getWidth()/2;
	}

	public int getCenterY() {
		return y + rect.y + (int)rect.getHeight()/2;
	}
	
	protected void isDead() {
		if (hp <= 0) {
			Sound.death.play();
			level.removeMob(this);
		}
	}
}