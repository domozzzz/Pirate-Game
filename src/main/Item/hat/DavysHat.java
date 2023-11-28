package main.Item.hat;

import java.awt.Color;

import main.gfx.Display;
import main.gfx.SpriteSheet;

public class DavysHat extends Hat {
	
	public DavysHat() {
		front = SpriteSheet.getSpriteImage(0*8, 10*8, SPRITE_SIZE, SPRITE_SIZE);
		back = SpriteSheet.getSpriteImage(1*SPRITE_SIZE, 10*8, SPRITE_SIZE, SPRITE_SIZE);
		left = SpriteSheet.getSpriteImage(2*SPRITE_SIZE, 10*8, SPRITE_SIZE, SPRITE_SIZE);
		right = SpriteSheet.getSpriteImage(3*SPRITE_SIZE, 10*8, SPRITE_SIZE, SPRITE_SIZE);
		img = front;
	}
	
	@Override
	public void render(Display display, int x, int y, int flip) {
		display.render(img, x, y, 0);
	}
}
