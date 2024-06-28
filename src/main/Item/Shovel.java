package main.Item;

import main.entity.mob.Player;
import main.gfx.SpriteSheet;

public class Shovel extends Item {

	public Shovel() {
		this.image = SpriteSheet.getSpriteImage(2*16, 7*16, 16, 16);
	}
	
	@Override
	public void tick() {
		super.tick();
	}

	@Override
	public void use(Player player) {
		player.removeTile();
	}
}