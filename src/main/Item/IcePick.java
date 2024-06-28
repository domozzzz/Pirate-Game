package main.Item;

import main.entity.mob.Player;
import main.entity.tile.Water;
import main.gfx.SpriteSheet;

public class IcePick extends Item {

	public IcePick() {
		image = SpriteSheet.getSpriteImage(1*16, 6*16, 16, 16);
	}
	
	@Override
	public void tick() {
		super.tick();
	}

	@Override
	public void use(Player player) {
		player.placeTile(new Water());
	}
}