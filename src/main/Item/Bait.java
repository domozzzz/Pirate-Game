package main.Item;

import java.awt.Color;
import java.util.Random;

import main.entity.mob.Player;
import main.gfx.SpriteSheet;

public class Bait extends Item {
	
	private Player player;
	private Random random;

	public Bait(Player player) {
		this.player = player;
		this.img = SpriteSheet.getSpriteImage(0*16, 0*8, 16, 16);
		this.icon = img;
		this.main = Color.RED;
		this.trim = Color.WHITE;
	}
	
	@Override
	public void tick() {
		super.tick();
	}

	@Override
	public void use() {
		// TODO Auto-generated method stub
		
	}
}