package main.Item;

public class Inventory {
	
	public Item items[] = new Item[10];
	private Item[] contents;
	
	public void add(Class<?> className, int num) {
		for (int i = 0; i < num; i++) {
			try {
				add((Item)className.newInstance());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void add(Item item) {
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

	public Item[] getContents() {
		return contents;
	}

	public void setContents(Item[] contents) {
		this.contents = contents;
	}

	public Item getItem(int slot) {
		return items[slot];
	}
}
