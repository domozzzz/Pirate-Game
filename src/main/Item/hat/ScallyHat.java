package main.Item.hat;

import java.awt.Color;

import main.gfx.Display;
import main.gfx.SpriteSheet;

public class ScallyHat extends Hat {
	
	public ScallyHat() {
		front = SpriteSheet.getSpriteImage(4*SPRITE_SIZE, 10*8, SPRITE_SIZE, SPRITE_SIZE);
		back = SpriteSheet.getSpriteImage(5*SPRITE_SIZE, 10*8, SPRITE_SIZE, SPRITE_SIZE);
		left = SpriteSheet.getSpriteImage(6*SPRITE_SIZE, 10*8, SPRITE_SIZE, SPRITE_SIZE);
		right = SpriteSheet.getSpriteImage(7*SPRITE_SIZE, 10*8, SPRITE_SIZE, SPRITE_SIZE);
		img = front;
	}

	@Override
	public void render(Display display, int x, int y, int flip) {
		display.render(img, x, y, 0);
	}
}
