package main.entity.tile;

import java.awt.Color;
import java.awt.image.BufferedImage;

import main.Game;
import main.Item.FishingRod;
import main.Item.Food;
import main.gfx.SpriteSheet;

public class Chest extends Tile {
	
	private boolean opened;
	private BufferedImage openedImg;
	
	public Chest(Game game) {
		super(SpriteSheet.getSpriteImage(1*TILE_SIZE, 1*TILE_SIZE, TILE_SIZE, TILE_SIZE));
		openedImg = SpriteSheet.getSpriteImage(2*TILE_SIZE, 1*TILE_SIZE, TILE_SIZE, TILE_SIZE);
		
		main = Color.YELLOW;
		trim = Color.GRAY;
		
		collision = true;
		breakable = false;
	}
	
	@Override
	public void tick() {
		super.tick();
		image = (opened) ? openedImg : image;
	}
	
	@Override
	public void event(Game game) {
		if (!opened) {
			game.getPlayer().getInv().addItem(new Food(game.getPlayer()));
			game.getPlayer().getInv().addItem(new Food(game.getPlayer()));
			game.getPlayer().getInv().addItem(new Food(game.getPlayer()));
			opened = true;
		}	
	}
}
