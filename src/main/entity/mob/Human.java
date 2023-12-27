package main.entity.mob;

import java.awt.Rectangle;
import java.util.Random;

import main.Sound;
import main.Level;
import main.Item.Ammo;
import main.Item.ItemEntity;
import main.ai.PathFinder;
import main.entity.Map;
import main.gfx.Display;
import main.gfx.SpriteSheet;
	
public class Human extends Mob {

	private Map map;
	private PathFinder pathFinder;
	private Random random;
	
	private int knockBack = 20;
	
	public Human(Level level) {
		this.level = level;
		
		map = level.getMap();
		pathFinder = new PathFinder(level);
		random = new Random();

		hp = 10;
		
		loadImages();
		image = frontWalk;
		
		initHitbox();
		spawn();
	}
	
	@Override
	public void tick() {
		super.tick();
	}
	
	public void searchPath() {
		
		int startCol = x/16;
		int startRow = y/16;
		
		Player player = level.getPlayer();
		
		int goalCol = player.getCenterX()/16;
		int goalRow = player.getCenterY()/16;
		
		pathFinder.setNodes(startCol, startRow, goalCol, goalRow, this);
		
		if (pathFinder.search() == true) {
			
			int xd = pathFinder.pathList.get(0).col * 16 - x;
			int yd = pathFinder.pathList.get(0).row * 16 - y;
			
			if (yd < 0) y--;
			else if (yd > 0) y++;
			else if (xd < 0) x--;	
			else if (xd > 0) x++;
		}
	}
	
	@Override
	public void render(Display display) {
		super.render(display);
		searchPath();
	}
	
	protected void handleImages() {
		switch(lastDir) {
		case 'u':
			if (walking) {
				image = backWalk;
			} else {
				image = back;
			}
			break;
		case 'd':
			if (walking) {
				image = frontWalk;
			} else {
				image = front;
			}
			break;
		case 'l':
			if (walking) {
				image = sideWalk;
			} else {
				image = side;
			}
			break;
		case 'r':
			if (walking) {
				image = sideWalk;
			} else {
				image = side;
			}
			break;
		}
	}
	
	public void spawn () {
		int xd, yd;
		do {
			x = random.nextInt(map.pw);
			y = random.nextInt(map.ph);
			xd = level.getPlayer().x - x;
			yd = level.getPlayer().y - y;
		} while (isCollision() || xd * xd + yd * yd < 100 * 100);
		
		if (x % 4 == 0) lastDir = 'u';
		if (x % 4 == 1) lastDir = 'd';
		if (x % 4 == 2) lastDir = 'l';
		if (x % 4 == 3) lastDir = 'r';
	}
	
	@Override
	public void event(Level level) {
		if (!cooldown) {
			level.getPlayer().damage(1);
			level.getPlayer().knock(knockBack);
			Sound.hit.play();
			setCooldown(20);
		}
	}
	
	@Override
	protected void initHitbox() {
		rect = (new Rectangle());
		rect.x = 2;
		rect.y = 2;	
		rect.width = 11;
		rect.height = 11;
	}
	
	@Override
	protected void isDead() {
		super.isDead();
		if (hp <= 0) {
			level.addEntity(new ItemEntity(new Ammo(),new Ammo().getImage(), x, y));
		}
	}
	
	public void loadImages() {
		front = SpriteSheet.getSpriteImage(0, 6*8, SPRITE_SIZE, SPRITE_SIZE);
		back = SpriteSheet.getSpriteImage(1*SPRITE_SIZE, 6*8, SPRITE_SIZE, SPRITE_SIZE);
		side = SpriteSheet.getSpriteImage(2*SPRITE_SIZE, 6*8, SPRITE_SIZE, SPRITE_SIZE);
		frontWalk = SpriteSheet.getSpriteImage(3*SPRITE_SIZE, 6*8, SPRITE_SIZE, SPRITE_SIZE);
		backWalk = SpriteSheet.getSpriteImage(4*SPRITE_SIZE, 6*8, SPRITE_SIZE, SPRITE_SIZE);
		sideWalk = SpriteSheet.getSpriteImage(5*SPRITE_SIZE, 6*8, SPRITE_SIZE, SPRITE_SIZE);
		sideItem = SpriteSheet.getSpriteImage(6*SPRITE_SIZE, 6*8, SPRITE_SIZE, SPRITE_SIZE);
	}
}
