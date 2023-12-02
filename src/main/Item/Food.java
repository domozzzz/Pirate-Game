package main.Item;

import main.entity.mob.Player;
import main.gfx.SpriteSheet;

public class Food extends Item {
	
	private int hp = 1;
	
	public Food() {
		this.image = SpriteSheet.getSpriteImage(0*16, 0*16, 16, 16);
	}

	@Override
	public void use(Player player) {
		if (player.getHp() != player.getMaxHp()) {
			player.heal(hp);
			player.getInventory().removeItem(Food.class);
		}
	}
}
