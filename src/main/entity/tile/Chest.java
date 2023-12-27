package main.entity.tile;

import java.awt.image.BufferedImage;

import main.Game;
import main.Level;
import main.Sound;
import main.Item.Food;
import main.gfx.SpriteSheet;

public class Chest extends Tile {
	
	private boolean opened;
	private BufferedImage openedImg;
	private Level level;
	
	public Chest(Level level) {
		super(SpriteSheet.getSpriteImage(1*TILE_SIZE, 1*TILE_SIZE, TILE_SIZE, TILE_SIZE));
		openedImg = SpriteSheet.getSpriteImage(2*TILE_SIZE, 1*TILE_SIZE, TILE_SIZE, TILE_SIZE);
		
		this.level = level;
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
			level.getPlayer().getInventory().add(new Food());
			level.getPlayer().getInventory().add(new Food());
			level.getPlayer().getInventory().add(new Food());
			Sound.open.play();
			opened = true;
		}	
	}
}
