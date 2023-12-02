package main.entity.mob;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.Game;
import main.Input;
import main.Level;
import main.Item.Ammo;
import main.Item.Bait;
import main.Item.FishingRod;
import main.Item.IcePick;
import main.Item.Inventory;
import main.Item.Item;
import main.Item.ItemEntity;
import main.Item.Pistol;
import main.Item.Shovel;
import main.Item.hat.DavysHat;
import main.Item.hat.Hat;
import main.entity.Entity;
import main.entity.Map;
import main.entity.tile.Tile;
import main.gfx.Display;
import main.gfx.SpriteSheet;
import main.menu.Hud;

public class Player extends Mob {
	
	public final int REACH = 16;
	private final int BUILD_REACH = 16;
	private final int SPAWN_X = 1;
	private final int SPAWN_Y = 1;
	private int maxHp = 10;
	private int knockbackSpeed = 6;
	private int knockBack;
	
	private BufferedImage heart, halfHeart, emptyHeart;
	private BufferedImage border;
	
	private Inventory inventory;
	private Rectangle rect;
	private Input input;
	private Hat hat;
	private Map map;
	private Game game;
	private Hud hud;
	private int activeSlot;
		
	private boolean frozen, flipSide;
	private int screenCenterX, screenCenterY;
	
	public Player(Game game) {
		this.game = game;
		input = game.getInput();
		hud = new Hud(this);
		
		createHitbox();
		reset();
		loadImages();
		
		screenCenterX = game.getDisplay().cameraWidth/2;
		screenCenterY = game.getDisplay().cameraHeight/2;
		
		hat = new DavysHat();
		image = front;
	}
	
	public void init(Level level) {
		this.level = level;
		inventory = new Inventory();
		inventory.add(new FishingRod());
		inventory.add(new Bait());
		inventory.add(new Bait());
		inventory.add(new Bait());
		inventory.add(new Pistol(level));
		inventory.add(new Pistol(level));
		inventory.add(new IcePick());
		inventory.add(new Shovel());
		inventory.add(Ammo.class, 10);
	}
 	
	public void tick() {
		super.tick();
				
		activeSlot = input.num;
		if (getActiveItem() != null) {
			getActiveItem().tick();

			if (input.enter) {
				getActiveItem().use(level.getPlayer());
			}
		}
		
		handleInputs();
		handleEntities();
		doKnockBack();
		isGameOver();
	}
	
	protected void handleImages() {
		switch(lastDir) {
		case 'u':
			if (walking) {
				image = backWalk;
			} else {
				image = back;
			}
			break;
		case 'd':
			if (walking) {
				image = frontWalk;
			} else {
				image = front;
			}
			break;
		case 'l':
			if (walking) {
				image = sideWalk;
			} else {
				image = side;
			}
			break;
		case 'r':
			if (walking) {
				image = sideWalk;
			} else {
				image = side;
			}
			break;
		}
	}
	
	public void render(Display display) {
		//draw player
		super.render(display);
	
		if (isEquipped(IcePick.class) || isEquipped(Shovel.class)) {
			
			int p = 0;
			int o = 0;
			
			switch (lastDir) {
			
				case 'u': 
					p = game.getLevel().getTileTopLeftX(getCenterX());
					o = game.getLevel().getTileTopLeftX(getCenterY() - BUILD_REACH);
					break;
				case 'd':
					p = game.getLevel().getTileTopLeftX(getCenterX());
					o = game.getLevel().getTileTopLeftX(getCenterY() + BUILD_REACH);
					break;
				case 'l':
					p = game.getLevel().getTileTopLeftX(getCenterX() - BUILD_REACH);
					o = game.getLevel().getTileTopLeftX(getCenterY());
					break;
				case 'r':
					p = game.getLevel().getTileTopLeftX(getCenterX() + BUILD_REACH);
					o = game.getLevel().getTileTopLeftX(getCenterY());
					break;

			}
			
			display.render(border, p*16, o*16, flip);
		}
	
		//draw hat
		hat.render(display, x, y, flip);
		
		//health
		int fullHearts = hp/2;
		int emptyHearts = (maxHp - hp)/2;
		int nHalfHeart = 0;
		
		if (hp % 2 == 1) {
			nHalfHeart++;
		}
		
		for (int i = 0; i < fullHearts; i++) {
			display.render(heart, 8+(i)*8 + display.xScroll, 8 + display.yScroll, 0);
		}
		
		if (nHalfHeart == 1) {
			display.render(halfHeart, 8+(fullHearts)*8 + display.xScroll, 8 + display.yScroll, 0);
		}
		
		for (int i = 0; i < emptyHearts; i++) {
			display.render(emptyHeart, 8+(fullHearts+nHalfHeart+i)*8 + display.xScroll, 8 + display.yScroll, 0);
		}
		
		hud.render(display);
	}
	
	public void handleEntities() {
		handleTileEvent();
		handleEntityCollision();
	}
	
	public void handleInputs() {
		if (!frozen) {
			handleMovements();
		}
		handleActions();
	}

	private void handleActions() {
		if (input.dispose) {
			dropItem(getActiveSlot());
		}
		
		if (input.back) {
			Game.paused = true;
		}
		
		if (input.enter) {
			if (getEntityInFront() != null) {
				getEntityInFront().event(game);
			}
		}
		
		input.clearNonMovement();
	}
	
	private void handleMovements() {
		
		if (input.up && y >= 0) {
			y--;
			walking = true;
			if (y >= screenCenterY && y < map.ph - screenCenterY) {
				game.getDisplay().yScroll--;
			}
			if (isCollision()) {
				y++;
				walking = false;
				if (y >= screenCenterY && y < map.ph - screenCenterY) {
					game.getDisplay().yScroll++;
				}
			}
			lastDir = 'u';
		}
		
		if (input.down && y < map.ph - Display.TILE_SIZE) {
			y++;
			walking = true;
			if (y > screenCenterY && y <= map.ph - screenCenterY) {
				game.getDisplay().yScroll++;
			}
			if (isCollision()) {
				y--;
				walking = false;
				if (y > screenCenterY && y <= map.ph - screenCenterY) {
					game.getDisplay().yScroll--;
				}
			}
			lastDir = 'd';
		}
		
		if (input.left && x >= 0) {
			x--;
			walking = true;
			if (x >= screenCenterX && x < map.pw - screenCenterX) {
				game.getDisplay().xScroll--;
			}
			if (isCollision()) {
				x++;
				walking = false;
				if (x >= screenCenterX && x < map.pw - screenCenterX) {
					game.getDisplay().xScroll++;
				}
			}
			lastDir = 'l';
		}
		
		if (input.right && x < map.pw - Display.TILE_SIZE) {
			x++;
			walking = true;
			if (x > screenCenterX && x <= map.pw - screenCenterX) {
				game.getDisplay().xScroll++;
			}

			if (isCollision()) {
				x--;
				walking = false;
				if (x > screenCenterX && x <= map.pw - screenCenterX) {
					game.getDisplay().xScroll--;
				}
			}
				
			lastDir = 'r';
		}
		handleImages();
		
		if (walking && (lastDir == 'u' || lastDir == 'd') && tickCount % 12 == 0) {
			flip = 2 - flip;
			//game.getAudio().playAudio(STEP_SOUND);
		}
		
		if (walking && (lastDir == 'l' || lastDir == 'r') && tickCount % 12 == 0) {
			flipSide = !flipSide;
		}
		walking = false;
	}
	
	public void setMap(Map map) {
		this.map = map;
	}
	
	public Entity getEntityInFront() {
		Entity entity = null;
		
		// search for entity
		switch (lastDir) {
			case 'u' -> entity = ((Level) game.getLevel()).getEntityAt(new Rectangle(x, y - REACH));
			case 'd' -> entity = ((Level) game.getLevel()).getEntityAt(new Rectangle(x, y + REACH));
			case 'l' -> entity = ((Level) game.getLevel()).getEntityAt(new Rectangle(x - REACH, y));
			case 'r' -> entity = ((Level) game.getLevel()).getEntityAt(new Rectangle(x + REACH, y));
		}
		
		//if no entity, search for tile
		if (entity == null) {
			switch (lastDir) {
				case 'u' -> entity = ((Level) game.getLevel()).getTileAt(x, y - REACH);
				case 'd' -> entity = ((Level) game.getLevel()).getTileAt(x, y + REACH);
				case 'l' -> entity = ((Level) game.getLevel()).getTileAt(x - REACH, y);
				case 'r' -> entity = ((Level) game.getLevel()).getTileAt(x + REACH, y);
			}
		}
		return entity;
	}
	
	private void handleTileEvent() {
		if (game.getLevel() instanceof Level) {
			((Level) game.getLevel()).getTileAt(getCenterX(), getCenterY()).event(game);
		}
	}
	
	private void isGameOver() {
		if (hp <= 0) {
			game.getLevel().removePlayer();
			game.setMenu(game.titleMenu);
			reset();
		}
	}
	
	public void reset() {
		hp = maxHp;
		x = SPAWN_X*SPRITE_SIZE;
		y = SPAWN_Y*SPRITE_SIZE;
		game.getDisplay().xScroll = 0;
		game.getDisplay().yScroll = 0;
	}
		
	private void handleEntityCollision() {
		if (game.getLevel() instanceof Level) {
			Entity entity = ((Level) game.getLevel()).getEntityAt(new Rectangle(x + rect.x, y + rect.y, rect.width, rect.height));
			if (entity != null) {
				entity.event(game);
			}
		}
	}
	
	private void createHitbox() {
		rect = new Rectangle();
		rect.x = 2;
		rect.y = 6;	
		rect.width = 11;
		rect.height = 9;
	}
	
	public void loadImages() {
		//character
		front = SpriteSheet.getSpriteImage(0, 6*8, SPRITE_SIZE, SPRITE_SIZE);
		back = SpriteSheet.getSpriteImage(1*SPRITE_SIZE, 6*8, SPRITE_SIZE, SPRITE_SIZE);
		side = SpriteSheet.getSpriteImage(2*SPRITE_SIZE, 6*8, SPRITE_SIZE, SPRITE_SIZE);
		frontWalk = SpriteSheet.getSpriteImage(3*SPRITE_SIZE, 6*8, SPRITE_SIZE, SPRITE_SIZE);
		backWalk = SpriteSheet.getSpriteImage(4*SPRITE_SIZE, 6*8, SPRITE_SIZE, SPRITE_SIZE);
		sideWalk = SpriteSheet.getSpriteImage(5*SPRITE_SIZE, 6*8, SPRITE_SIZE, SPRITE_SIZE);
		sideItem = SpriteSheet.getSpriteImage(6*SPRITE_SIZE, 6*8, SPRITE_SIZE, SPRITE_SIZE);
		sideWalk2 = SpriteSheet.getSpriteImage(7*SPRITE_SIZE, 6*8, SPRITE_SIZE, SPRITE_SIZE);
		
		//health
		heart = SpriteSheet.getSpriteImage(26*8, 17*8, Display.ICON_SIZE, Display.ICON_SIZE);
		halfHeart = SpriteSheet.getSpriteImage(27*8, 17*8, Display.ICON_SIZE, Display.ICON_SIZE);
		emptyHeart = SpriteSheet.getSpriteImage(28*8, 17*8, Display.ICON_SIZE, Display.ICON_SIZE);
		
		//gui
		border = SpriteSheet.getSpriteImage(0, 19*8, 16, 16);
	}

	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

	public void freeze() {
		frozen = true;
	}
	
	public void unfreeze() {
		frozen = false;
	}

	public Game getGame() {
		return game;
	}
	
	public Inventory getInventory() {
		return inventory;
	}

	public void placeTile(Tile tile) {
		switch (lastDir) {
			case 'u' -> ((Level) game.getLevel()).addTile(5, getCenterX(), getCenterY() - BUILD_REACH);
			case 'd' -> ((Level) game.getLevel()).addTile(5, getCenterX(), getCenterY() + BUILD_REACH);
			case 'l' -> ((Level) game.getLevel()).addTile(5, getCenterX() - BUILD_REACH, getCenterY());
			case 'r' -> ((Level) game.getLevel()).addTile(5, getCenterX() + BUILD_REACH, getCenterY());
		}
	}
	
	public void removeTile() {
		switch (lastDir) {
			case 'u' -> ((Level) game.getLevel()).removeTile(getCenterX(), getCenterY() - BUILD_REACH);
			case 'd' -> ((Level) game.getLevel()).removeTile(getCenterX(), getCenterY() + BUILD_REACH);
			case 'l' -> ((Level) game.getLevel()).removeTile(getCenterX() - BUILD_REACH, getCenterY());
			case 'r' -> ((Level) game.getLevel()).removeTile(getCenterX() + BUILD_REACH, getCenterY());
		}
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public char getDir() {
		return lastDir;
	}

	public int getMaxHp() {
		return maxHp;
	}
	
	public char getXDir() {
		char dir = lastDir;
		if (input.left) dir = 'l';
		if (input.right) dir = 'r';
		return dir;
	}

	public char getYDir() {
		char dir = lastDir;
		if (input.up) dir = 'u';
		if (input.down) dir = 'd';
		return dir;
	}
	
	public void knock(int dist)  {
		knockBack += dist;
	}
	
	public void doKnockBack() {
		if (knockBack > 0) {
		
			if (lastDir == 'd' && y >= 0) {
				y -= knockbackSpeed;
				walking = true;
				if (y >= screenCenterY && y < map.ph - screenCenterY) {
					game.getDisplay().yScroll--;
				}
				if (isCollision()) {
					y += knockbackSpeed;
					walking = false;
					if (y >= screenCenterY && y < map.ph - screenCenterY) {
						game.getDisplay().yScroll++;
					}
					return;
				}
			}
			
			if (lastDir == 'u' && y < map.ph - Display.TILE_SIZE) {
				y += knockbackSpeed;
				walking = true;
				if (y > screenCenterY && y <= map.ph - screenCenterY) {
					game.getDisplay().yScroll += knockbackSpeed;
				}
				if (isCollision()) {
					y -= knockbackSpeed;
					walking = false;
					if (y > screenCenterY && y <= map.ph - screenCenterY) {
						game.getDisplay().yScroll -= knockbackSpeed;
					}
					return;
				}
			}
			
			if (lastDir == 'r' && x >= 0) {
				x -= knockbackSpeed;
				walking = true;
				if (x >= screenCenterX && x < map.pw - screenCenterX) {
					game.getDisplay().xScroll -= knockbackSpeed;
				}
				if (isCollision()) {
					x += knockbackSpeed;
					walking = false;
					if (x >= screenCenterX && x < map.pw - screenCenterX) {
						game.getDisplay().xScroll += knockbackSpeed;
					}
					return;
				}
			}
			
			if (lastDir == 'l' && x < map.pw - Display.TILE_SIZE) {
				x += knockbackSpeed;
				walking = true;
				if (x > screenCenterX && x <= map.pw - screenCenterX) {
					game.getDisplay().xScroll += knockbackSpeed;
				}
	
				if (isCollision()) {
					x -= knockbackSpeed;
					walking = false;
					if (x > screenCenterX && x <= map.pw - screenCenterX) {
						game.getDisplay().xScroll -= knockbackSpeed;
					}
					return;
				}
			}
			knockBack -= knockbackSpeed;
		} else  {
			knockBack = 0;
		}
	}
	
	public void throwItem(Item item) {
		int xDist = 0, yDist = 0;
		
		switch (lastDir) {
			case 'u' -> yDist -= REACH;
			case 'd' -> yDist += REACH;
			case 'l' -> xDist -= REACH;
			case 'r' -> xDist += REACH;
		}
		level.addEntity(new ItemEntity(getActiveItem(), getActiveItem().getImage(), x + xDist, y + yDist));
	}
	
	public void dropItem(int slot) {
		throwItem(getItem(slot));
		inventory.removeItem(getItem(slot).getClass());
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public Level getLevel() {
		return level;
	}

	public boolean isEquipped(Class<?> className) {
	
		if (getActiveItem() != null && getActiveItem().getClass().equals(className)) {
			return true;
		}
		return false;
	}
	
	public Item getActiveItem() {
		return inventory.getItem(getActiveSlot());
	}

	public Item getItem(int slot) {
		return inventory.getItem(slot);
	}

	public int getActiveSlot() {
		return activeSlot;
	}

	public void setActiveSlot(int activeSlot) {
		this.activeSlot = activeSlot;
	}
}