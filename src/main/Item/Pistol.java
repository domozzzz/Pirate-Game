package main.Item;

import java.awt.Color;
import java.awt.image.BufferedImage;

import main.Game;
import main.entity.Bullet;
import main.gfx.Display;
import main.gfx.SpriteSheet;

public class Pistol extends Item {
	
	private Game game;
	private BufferedImage bulletImage;	
	private final String FIRE_SOUND = "/res/sounds/roblox/snap.wav";
	
	public Pistol(Game game) {
		
		this.game = game;
		
		icon = SpriteSheet.getSpriteImage(0*16, 12*8, 16, 16);
		img = SpriteSheet.getSpriteImage(1*16, 12*8, 16, 16);		 
	}
	
	@Override
	public void use() {
		if (!cooldown && game.getPlayer().getInv().contains(Ammo.class)) {
			
			game.getLevel().addEntity(new Bullet(game, game.getPlayer().x,
					game.getPlayer().y,
					game.getPlayer().getXDir(), game.getPlayer().getYDir()));
			
			game.getPlayer().getInv().removeItem(Ammo.class);
			game.getAudio().playAudio(FIRE_SOUND);
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
