package main.entity.mob;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.Game;
import main.entity.Entity;
import main.gfx.Display;
import main.gfx.SpriteSheet;
import main.screen.level.Level;

public abstract class Mob extends Entity {
	
	protected Game game;
	protected int speed;
	protected int hp;
	protected BufferedImage front, back, side, frontWalk, backWalk,
	sideWalk, heart, halfHeart, emptyHeart, bubble, hatImage, sideItem, sideWalk2;
	protected boolean walking;
	protected char lastDir;
	protected int flip;
	protected static final int SPRITE_SIZE = 16;
	
	private final String DEATH_SOUND = "/res/sounds/roblox/death.wav";
	
	public Mob(Game game) {
		this.game = game;
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
		if (((Level) game.getScreen()).getTileAt(x + getRect().x, y + getRect().y).isCollision() 
				|| ((Level) game.getScreen()).getTileAt(x + getRect().x + getRect().width, y + getRect().y + getRect().height).isCollision()
				|| ((Level) game.getScreen()).getTileAt(x + getRect().x + getRect().width, y + getRect().y).isCollision() 
				|| ((Level) game.getScreen()).getTileAt(x + getRect().x, y + getRect().y + getRect().height).isCollision()) {
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
	
	public int getMiddleX() {
		return x + rect.x + (int)rect.getWidth()/2;
	}

	public int getMiddleY() {
		return y + rect.y + (int)rect.getHeight()/2;
	}
	
	protected void isDead() {
		if (hp <= 0) {
			game.getAudio().playAudio(DEATH_SOUND);
			game.levelScreen.removeMob(this);
		}
	}
}