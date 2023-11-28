package main.entity.mob;

import java.util.Random;

import main.Game;
import main.Item.Ammo;
import main.Item.ItemEntity;
import main.entity.Map;
import main.gfx.Display;
import main.gfx.SpriteSheet;


public class Ghost extends Mob {

	private Map map;
	private Random random = new Random();
	
	public Ghost(Game game) {
		super(game);
		image = SpriteSheet.getSpriteImage(11*16, 4*16, 16, 16);
		map = game.levelScreen.getMap();
		hp = 10;
	
		spawn();
	}
	
	@Override
	public void tick() {
		super.tick();
		
		int xd = game.getScreen().player.x - x;
		int yd = game.getPlayer().y - y;
		if (xd * xd + yd * yd < 100 * 100) {
			if (yd < 0) y--;
			if (yd > 0) y++;
			if (xd < 0) x--;
			if (xd > 0) x++;
		}
		
		if (walking && tickCount % 8 == 0) {
			flip = 2 - flip;
		}
		
		walking = false;
	}
	
	@Override
	public void render(Display display) {
		super.render(display);
	}
	
	public void spawn () {
		int xd, yd;
		do {
			x = random.nextInt(map.pw);
			y = random.nextInt(map.ph);
			xd = game.getScreen().player.x - x;
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
			game.levelScreen.addEntity(new ItemEntity(new Ammo(),new Ammo().img, x, y));
		}
	}
}