package main.menu;

import java.awt.Color;

import main.Item.Inventory;
import main.entity.mob.Player;
import main.gfx.Display;
import main.gfx.SpriteSheet;

public class Hud {
	
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
			Color borderColor = Color.LIGHT_GRAY;
			if (i == player.getActiveSlot()) {
				borderColor = Color.YELLOW;
			}
			
			//hotbar
			display.render(SpriteSheet.getSpriteImage(0, 19*8, 16, 16), (3+i)*16 + display.xScroll, 15*16 + display.yScroll, 0);
			
			//item's icons
			if (player.getInventory().getItem(i) != null) {
				display.render(player.getInventory().getItem(i).getImage(), (3+i)*16 + display.xScroll, 15*16 + display.yScroll, 0);
			}
			if (player.getInventory().getItem(i) != null && player.getInventory().getItem(i).slot > 1) {
				display.getFont().draw(String.valueOf(player.getInventory().getItem(i).slot), display, 6*8+i*16 + display.xScroll, 30*8 + display.yScroll);
			}
		}
	}
	
	public void drawHotbar1(Display display) {
		for (int i = 0; i < 10; i++) {
			Color borderColor = Color.LIGHT_GRAY;
			if (i == player.getActiveSlot()) {
				borderColor = Color.YELLOW;
			}
			
			//hotbar
			display.render(SpriteSheet.getSpriteImage(0, 19*8, 16, 16), (3+i)*16 + display.xScroll, 15*16 + display.yScroll, 0);
			
			//item's icons
			if (player.getInventory().getItem(i) != null) {
				display.render(player.getInventory().getItem(i).getImage(), (3+i)*16 + display.xScroll, 15*16 + display.yScroll, 0);
			}
			if (player.getInventory().getItem(i) != null && player.getInventory().getItem(i).slot > 1) {
				display.getFont().draw(String.valueOf(player.getInventory().getItem(i).slot), display, 6*8+i*16 + display.xScroll, 30*8 + display.yScroll);
			}
		}
	}
}
