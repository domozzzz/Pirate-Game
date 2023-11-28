package main.Item;

import java.awt.Color;

import main.entity.mob.Player;
import main.gfx.SpriteSheet;

public class Food extends Item {
	
	private Player player;
	private int hp = 1;
	
	public Food(Player player) {
		this.img = SpriteSheet.getSpriteImage(0*16, 0*16, 16, 16);
		this.player = player;
		this.icon = img;
		this.main = Color.BLUE;
		this.trim = Color.WHITE;
	}

	@Override
	public void use() {
		if (player.getHp() != player.getMaxHp()) {
			player.heal(hp);
			player.getInv().removeItem(Food.class);
		}
	}
}
