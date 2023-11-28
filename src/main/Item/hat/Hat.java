package main.Item.hat;

import java.awt.image.BufferedImage;

import main.gfx.Display;

public abstract class Hat {
	
	protected final int SPRITE_SIZE = 16;
	protected BufferedImage front;
	protected BufferedImage back;
	protected BufferedImage left;
	protected BufferedImage right;
	protected BufferedImage img;
	
	public void setHatImg(BufferedImage img) {
		this.img =  img;
	}
	
	public abstract void render(Display display, int x, int y, int flip);

	public void setFront() {
		img = front;
	}
	
	public void setBack() {
		img = back;
	}
	
	public void setLeft() {
		img = left;
	}
	
	public void setRight() {
		img = right;
	}
}
