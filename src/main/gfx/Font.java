package main.gfx;

import java.awt.Color;

public class Font {
	
	private final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private final String nums = "0123456789,.!?'\"-=/\\()<>";
	public static int SIZE = 8; //pixels
	private final int spriteSheetChars = 17*SIZE;
	private final int spriteSheetNums = 18*SIZE;

	public void draw(String msg, Display screen, int x, int y) {
		msg = msg.toUpperCase();
		
		for (int i = 0; i < msg.length(); i++) {
			
			int spriteSheetX;
			int spriteSheetY;
			
			if (msg.charAt(i) == ' ') {
				x+=8;
				continue;
			}
			
			if (chars.indexOf(msg.charAt(i)) != -1) {
				spriteSheetX = chars.indexOf(msg.charAt(i)) * SIZE;
				spriteSheetY = spriteSheetChars;
				
			} else {
				spriteSheetX = nums.indexOf(msg.charAt(i)) * SIZE;
				spriteSheetY = spriteSheetNums;
			}
			
			screen.render(SpriteSheet.getSpriteImage(spriteSheetX, spriteSheetY, SIZE, SIZE), x += 8, y, 0);
		}
	}
	
	public void draw(String msg, Display display, int y) {
		int x = display.cameraWidthTiles * SIZE - msg.length() * SIZE/2;
		draw(msg, display, x, y);
	}
	
	public void scrollingDraw(String msg, Display display, int x, int y) {
		draw(msg, display, x + display.xScroll, y + display.yScroll);
	}
	
	public void scrollingDraw(String msg, Display display, int y) {
		int x = display.cameraWidthTiles*SIZE - msg.length()*SIZE/2;
		draw(msg, display, x + display.xScroll, y + display.yScroll);
	}
}