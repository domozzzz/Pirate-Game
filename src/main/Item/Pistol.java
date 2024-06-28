package main.Item;

import main.Level;
import main.Sound;
import main.entity.Bullet;
import main.entity.mob.Player;
import main.gfx.SpriteSheet;

public class Pistol extends Item {
	
	
	public Pistol(Level level) {
		
		this.level = level;
		
		image = SpriteSheet.getSpriteImage(1*16, 12*8, 16, 16);		 
	}

	@Override
	public void use(Player player) {
		if (!cooldown && player.getInventory().contains(Ammo.class)) {
			
			level.addEntity(new Bullet(level, player.x, player.y, player.getXDir(), player.getYDir()));
			player.getInventory().removeItem(Ammo.class);
			
			Sound.shoot.play();
			setCooldown(20);
		}
	}
	
	@Override
	public void tick() {
		super.tick();
		if (!cooldown) {
			shooting = false;
		}
	}
}
