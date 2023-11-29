package main.Item;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.Game;
import main.entity.Entity;

public class ItemEntity extends Entity {
	
	private Item item;
	private final String PICKUP_SOUND = "/res/sounds/roblox/bass.wav";
	
	public ItemEntity(Item item, BufferedImage image, int x, int y) {
		this.item = item;
		this.image = image;
		this.x = x;
		this.y = y;
		createHitbox();
	}
	
	@Override
	public void tick() {
		super.tick();
		
	}
	
	private void createHitbox() {
		rect = new Rectangle();
		rect.x = 2;
		rect.y = 6;	
		rect.width = 11;
		rect.height = 9;
	}
	
	@Override
	public void event(Game game) {
		if (!cooldown) {
			game.getPlayer().getInv().addItem(item);
			game.getLevel().removeEntity(this);
			game.getAudio().playAudio(PICKUP_SOUND);
			setCooldown(80);
		}
	}
}
