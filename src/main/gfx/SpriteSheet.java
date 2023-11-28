package main.gfx;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class SpriteSheet {
	
	public static final int GRID_SIZE = 8;
	private static BufferedImage image;
	public int[] pixels;
	
	public SpriteSheet(BufferedImage image) {
		SpriteSheet.image = image;
	}
	
	public static BufferedImage getSpriteImage(int x, int y, int w, int h) {
		return image.getSubimage(x, y, w, h);
	}
}