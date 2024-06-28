package main.Item;

import main.gfx.SpriteSheet;

public class Ammo extends Item{

	public Ammo() {
		image = SpriteSheet.getSpriteImage(1*16, 6*16, 16, 16);
	}
}
