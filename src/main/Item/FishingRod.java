package main.Item;

import java.awt.Color;
import java.util.Random;

import main.Game;
import main.entity.mob.Player;
import main.entity.tile.Water;
import main.gfx.SpriteSheet;

public class FishingRod extends Item {
	
	private Game game;
	private Random random;

	public FishingRod(Game game) {
		this.game = game;
		this.random = new Random();
		this.img = SpriteSheet.getSpriteImage(1*16, 14*8, 16, 16);
		this.icon = img;
		this.main = Color.RED;
		this.trim = Color.WHITE;
	}
	
	@Override
	public void tick() {
		super.tick();
		
		if (random.nextInt(60) == 1) {
			setCooldown(10);
		}
	}
	
	@Override
	public void use() {
		
		if (game.getPlayer().getInv().contains(Bait.class) && game.getPlayer().getEntityInFront() instanceof Water) {
		
			game.getPlayer().getInv().removeItem(Bait.class);
			catchFish();
		}
	}
	
	public void catchFish() {
		int nextInt = random.nextInt(100);
		
		if (nextInt > 99) {
			game.getPlayer().getInv().addItem(new Pistol(game));
		}
		
		else if (nextInt > 90) {
			game.getPlayer().getInv().addItem(new Pistol(game));
		}
		
		else if (nextInt > 80) {
			game.getPlayer().getInv().addItem(new Pistol(game));
		}
		
		else  if (nextInt > 50) {
			game.getPlayer().getInv().addItem(new Pistol(game));
		} 
		
		else if (nextInt > 0) {
			game.getPlayer().getInv().addItem(new Pistol(game));
		}
	}
}