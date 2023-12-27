package main.menu;

import main.entity.mob.Player;
import main.gfx.Display;
import main.gfx.SpriteSheet;

public class Hud {
	
	private static final int TILE_SIZE = 16;
	private Player player;

	public Hud(Player player) {
		this.player = player;
	}
	
	public void render(Display display) {
		drawHotbar(display);
		renderItems(display);
	}
	
	public void renderItems(Display display) {
		//draw items
		if (player.getActiveItem() != null) {
			player.getActiveItem().render(display, player.x, player.y, 0);
		
		}
	}

	public void drawHotbar(Display display) {
		for (int i = 0; i < 10; i++) {
			//draw hotbar
			display.render(SpriteSheet.getSpriteImage(0, 19*8, TILE_SIZE, TILE_SIZE), (3+i)*TILE_SIZE + display.xScroll, 15*TILE_SIZE + display.yScroll, 0);
			
			if (player.getInventory().getItem(i) != null) {
				//item's icons
				display.render(player.getInventory().getItem(i).getImage(), (3+i)*TILE_SIZE + display.xScroll, 15*TILE_SIZE + display.yScroll, 0);
				//item counts
				if (player.getInventory().getItem(i).slot > 1) {
					display.getFont().draw(String.valueOf(player.getInventory().getItem(i).slot), display, 6*8+i*TILE_SIZE + display.xScroll, 30*8 + display.yScroll);
				}
			}
		}
	}
}
