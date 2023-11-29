package main.entity.mob;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;

import main.Game;
import main.Item.Ammo;
import main.Item.ItemEntity;
import main.ai.Node;
import main.ai.PathFinder;
import main.entity.Map;
import main.gfx.Display;
import main.gfx.SpriteSheet;


public class Human extends Mob {

	private Map map;
	private Random random = new Random();
	boolean onPath;
	PathFinder pathFinder;
	char dir;
	
	public Human(Game game) {
		super(game);
		pathFinder = new PathFinder(game);
		loadImages();
		image = frontWalk;
		map = game.getLevel().getMap();
		hp = 10;
		spawn();
		onPath = true;
	}
	
	@Override
	public void tick() {
		super.tick();
	}
	
	public void searchPath() {
		
		int startCol = x/16;
		int startRow = y/16;
		
		Player player = game.getPlayer();
		
		int goalCol = player.getMiddleX()/16;
		int goalRow = player.getMiddleY()/16;
		
		pathFinder.setNodes(startCol, startRow, goalCol, goalRow, this);
		
		if (pathFinder.search() == true) {
			
			int xd = pathFinder.pathList.get(0).col * 16 - x;
			int yd = pathFinder.pathList.get(0).row * 16 - y;
			
			if (yd < 0) y--;
			if (yd > 0) y++;
			if (xd < 0) x--;
			if (xd > 0) x++;
		}
		
		int xd = game.getPlayer().x - x;
		int yd = game.getPlayer().y - y;
		
		if (xd * xd + yd * yd < 15 * 15) {
			onPath = false;
			xd = player.x - x;
			yd = player.x - y;
			
			if (yd < 0) y--;
			if (yd > 0) y++;
			if (xd < 0) x--;
			if (xd > 0) x++;
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
			xd = game.getPlayer().x - x;
			yd = game.getPlayer().y - y;
		} while (isCollision() || xd * xd + yd * yd < 100 * 100);
		
		if (x % 4 == 0) lastDir = 'u';
		if (x % 4 == 1) lastDir = 'd';
		if (x % 4 == 2) lastDir = 'l';
		if (x % 4 == 3) lastDir = 'r';
	}
	
	public void event(Game game) {
		if (!cooldown) {
			game.getPlayer().damage(1);
			setCooldown(20);
		}
	}
	
	@Override
	protected void isDead() {
		super.isDead();
		if (hp <= 0) {
			game.getLevel().addEntity(new ItemEntity(new Ammo(),new Ammo().img, x, y));
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
