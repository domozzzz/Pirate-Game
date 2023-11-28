package main.Item;

import java.awt.Color;

import main.gfx.SpriteSheet;

public class Ammo extends Item{

	public Ammo() {
		this.img = SpriteSheet.getSpriteImage(1*16, 6*16, 16, 16);
		this.icon = img;
		this.main = Color.GREEN;
		this.trim = Color.WHITE;
	}
	
	@Override
	public void use() {
		
	}
	
	

}
