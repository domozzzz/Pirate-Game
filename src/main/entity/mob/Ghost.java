package main.entity.mob;

import java.awt.Rectangle;
import java.util.Random;

import main.Level;
import main.Sound;
import main.Item.Ammo;
import main.Item.ItemEntity;
import main.entity.Map;
import main.gfx.Display;
import main.gfx.SpriteSheet;


public class Ghost extends Mob {

	private Map map;
	private Random random = new Random();
	
	public Ghost(Level level) {
		this.level = level;
		image = SpriteSheet.getSpriteImage(11*16, 4*16, 16, 16);
		map = level.getMap();
		hp = 10;
		
		initHitbox();
		spawn();
	}
	
	@Override
	public void tick() {
		super.tick();
		
		int xd = level.getPlayer().x - x;
		int yd = level.getPlayer().y - y;
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
			level.getPlayer().knock(20);
			Sound.hurt.play();
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
}
