package main.Item;

import main.gfx.SpriteSheet;

public class Bait extends Item {

	public Bait() {
		image = SpriteSheet.getSpriteImage(0*16, 0*8, 16, 16);
	}
	
	@Override
	public void tick() {
		super.tick();
	}
}