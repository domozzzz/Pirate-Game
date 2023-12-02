package main.Item;

import java.awt.Color;
import java.util.Random;

import main.entity.mob.Player;
import main.entity.tile.Water;
import main.gfx.SpriteSheet;

public class FishingRod extends Item {
	
	private Random random;

	public FishingRod() {
		this.random = new Random();
		this.image = SpriteSheet.getSpriteImage(1*16, 14*8, 16, 16);
		this.icon = image;
	}
	
	@Override
	public void tick() {
		super.tick();
		
		if (random.nextInt(60) == 1) {
			setCooldown(10);
		}
	}
	
	@Override
	public void use(Player player) {
		
		if (player.getInventory().contains(Bait.class) && player.getEntityInFront() instanceof Water) {
		
			player.getInventory().removeItem(Bait.class);
			catchFish(player);
		}
	}
	
	public void catchFish(Player player) {
		int nextInt = random.nextInt(100);
		
		if (nextInt > 99) {
			player.getInventory().add(new Pistol(player.getLevel()));
		}
		
		else if (nextInt > 90) {
			player.getInventory().add(new Pistol(player.getLevel()));
		}
		
		else if (nextInt > 80) {
			player.getInventory().add(new Pistol(player.getLevel()));
		}
		
		else  if (nextInt > 50) {
			player.getInventory().add(new Pistol(player.getLevel()));
		} 
		
		else if (nextInt > 0) {
			player.getInventory().add(new Pistol(player.getLevel()));
		}
	}
}