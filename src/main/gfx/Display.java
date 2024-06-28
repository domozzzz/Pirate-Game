package main.gfx;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import main.Game;
import main.io.IO;

public class Display {
	
	public static int TILE_SIZE = 16;
	public static int ICON_SIZE = 8;
	
	public int[] pixels;
	public int yScroll = 0;
	public int xScroll = 0;
	public final int cameraWidthTiles = 16;
	public final int cameraWidth = 16 * 16;
	public final int cameraHeight = 16 * 16;
		
	private SpriteSheet spriteSheet;
	private String spriteSheetFile = "/res/spritesheets/tilles.png";
	private Font font;
	
	public Display() {
		pixels = new int[Game.SCREEN_WIDTH * Game.SCREEN_HEIGHT];
		
		spriteSheet = new SpriteSheet(IO.loadImage(spriteSheetFile));
		font = new Font();
	}
	
	public void fillColor(int argb) {
		Arrays.fill(pixels, argb);
	}
	
	public void clear() {
		Arrays.fill(pixels, 0xFF000000);
	}

	public void render(BufferedImage image, int x, int y, int flip) {
		for (int h = 0; h < image.getHeight(); h++) {
			for (int w = 0; w < image.getWidth(); w++) {
				int wf = w;
				int hf = h;
				
				if (flip == 2) wf = (image.getWidth() - 1) - w;				
				if (flip == 1) hf = (image.getWidth() - 1) - h;
				
				int xPos = x - xScroll + wf;
				int yPos = y - yScroll + hf;
				
				if (xPos < 0 || xPos >= Game.SCREEN_WIDTH) continue;
				if (yPos < 0 || yPos >= Game.SCREEN_HEIGHT) continue;
				
				int pixel = xPos + yPos*Game.SCREEN_WIDTH;
				Color color = new Color(image.getRGB(w, h));
				if (!color.equals(Color.decode("#B200FF")) && !color.equals(Color.decode("#57007F"))) {
					pixels[pixel] = image.getRGB(w, h);
				}
			}
		}
	}
	
	public Font getFont() 
	{
		return font;
	}
}