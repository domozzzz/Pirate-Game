package main.Item;

import java.awt.Color;

import main.Game;
import main.Input;
import main.entity.Entity;
import main.entity.mob.Player;
import main.entity.tile.Chest;
import main.entity.tile.Tile;
import main.gfx.Display;
import main.gfx.SpriteSheet;

public class Inventory {
	
	public Item items[] = new Item[10];
	private int currentItem = 0;
	public int itemFlip = 0;
	private int inv = 0;
	private boolean open;
	
	private final Game game;
	private final Player player;
	private final Display display;
	private final Input input;
	
	private Item[] contents;
	
	
	public Inventory(Game game, Player player) {
		this.game = game;
		this.display = game.getDisplay();
		this.player = player;
		this.input = game.getInput();
		
		this.addItem(new FishingRod(game));
		this.addItem(new Bait(player));
		this.addItem(new Bait(player));
		this.addItem(new Bait(player));
		this.addItem(new Pistol(game));
		this.addItem(new Pistol(game));
		this.addItem(new IcePick(game));
		this.addItem(new Shovel(game));
		this.addItems(Ammo.class, 10);

		
		setContents(new Item[60]);
		
		for (int i = 0; i < 6; i++) {
			contents[i] = new Bait(player);
		}
	}
	
	private void addItems(Class<?> className, int num) {
		for (int i = 0; i < num; i++) {
			try {
				addItem((Item) className.newInstance());
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void tick() {
		currentItem = input.num;
		if (getCurrentItem() != null) {
			getCurrentItem().tick();
		
			if (input.enter) {
				getCurrentItem().use();
			}
		}
	}
	
	public void render() {
		drawHotbar();
		drawInv();
		renderItems();
	}
	
	public void renderItems() {
		//draw items
		if (getCurrentItem() != null) {
			getCurrentItem().render(display, player.x, player.y, 0);
		
		}
	}

	public void drawHotbar() {
		for (int i = 0; i < 10; i++) {
			Color borderColor = Color.LIGHT_GRAY;
			if (i == currentItem) {
				borderColor = Color.YELLOW;
			}
			
			//hotbar
			display.render(SpriteSheet.getSpriteImage(0, 19*8, 16, 16), (3+i)*16 + display.xScroll, 15*16 + display.yScroll, 0);
			
			//item's icons
			if (items[i] != null) {
				display.render(items[i].icon, (3+i)*16 + display.xScroll, 15*16 + display.yScroll, 0);
			}
			if (items[i] != null && items[i].slot > 1) {
				display.getFont().draw(String.valueOf(items[i].slot), display, 6*8+i*16 + display.xScroll, 30*8 + display.yScroll);
			}
		}
	}
	
	public void drawInv() {
		//inventory
		if (open) {
			int index = 0;
			for (int j = 0; j < 6; j++) {
				for (int i = 0; i < 10; i++) {
					Color borderColor = Color.LIGHT_GRAY;
					if (index == inv) {
						borderColor = Color.YELLOW;
					}
					if (contents[j] != null) {
						display.render(contents[j].img, (3+i)*16 + display.xScroll, 6*16 + j*16 + display.yScroll, 0);
						display.render(SpriteSheet.getSpriteImage(0, 19*8, 16, 16), (3+i)*16 + display.xScroll, 6*16 + j*16 + display.yScroll, 0);
					}
					index++;
				}
			}
		}
	}
	
	public Item getCurrentItem() {
		return items[currentItem];
	}
	
	public void addItem(Item item) {
		for (int i = 0; i < items.length; i++) {
			if (items[i] != null && item.getClass().equals(items[i].getClass())) {
				items[i].slot++;
				return;
			}
		}
		
		for (int i = 0; i < items.length; i++) {
			if (items[i] == null) {
				items[i] = item;
				items[i].slot++;
				return;
			}
		}
	}
	
	public void drop() {
		Item item = getCurrentItem();
		if (item != null) {
			for (int i = 0; i < items.length; i++) {
				//remove item from item stack
				if (items[i] != null && item.getClass().equals(items[i].getClass())) {
					throw_item();
					items[i].slot--;
				}
				// last item in stack
				if (items[i] != null && items[i].slot < 1) {
					throw_item();
					items[i] = null;
					return;
				}
			}
		}
	}
	
	public void throw_item() {
		int xDist = 0, yDist = 0;
		
		switch (player.getDir()) {
			case 'u' -> yDist -= player.REACH;
			case 'd' -> yDist += player.REACH;
			case 'l' -> xDist -= player.REACH;
			case 'r' -> xDist += player.REACH;
		}
		game.getLevel().addEntity(new ItemEntity(getCurrentItem(), getCurrentItem().img, player.x + xDist, player.y + yDist));
	}
	
	public void removeItem(Class<?> className) {
		for (int i = 0; i < items.length; i++) {
			if (items[i] != null && items[i].getClass().equals(className)) {
				//game.screen.entities.add(new Entity(display, getCurrentItem().image, player.x, player.y));
				items[i].slot--;
			}
			if (items[i] != null && items[i].slot < 1) {
				//game.screen.entities.add(new Entity(display, getCurrentItem().image, player.x, player.y));
				items[i] = null;
				return;
			}
		}
	}

	public boolean contains(Class<?> className) {
		for (int i = 0; i < items.length; i++) {
			if (items[i] != null && items[i].getClass().equals(className)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isEquipped(Class<?> className) {
	
		if (items[currentItem] != null && items[currentItem].getClass().equals(className)) {
			return true;
		}
		return false;
	}

	public void displayChest(Chest chest) {
		open = true;
		player.freeze();
	}

	public Item[] getContents() {
		return contents;
	}

	public void setContents(Item[] contents) {
		this.contents = contents;
	}

	public void gui() {
		if (input.enter) {
			addItem(contents[inv]);
			contents[inv] = null;
			
		}
		if (input.up && inv >= 10) inv -= 10;
		if (input.down && inv < 50) inv += 10;
		if (input.left && inv > 0) inv--;
		if (input.right && inv % 10 < 9) inv++;	
		
		if (input.back) player.unfreeze();
		input.clear();
	}
}
