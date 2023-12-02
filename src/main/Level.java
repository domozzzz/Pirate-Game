package main;

import java.awt.Rectangle;
import java.util.ArrayList;

import main.entity.Entity;
import main.entity.Map;
import main.entity.mob.Ghost;
import main.entity.mob.Human;
import main.entity.mob.Mob;
import main.entity.mob.Player;
import main.entity.tile.Chest;
import main.entity.tile.Door;
import main.entity.tile.Floor;
import main.entity.tile.Tile;
import main.entity.tile.Tree;
import main.entity.tile.Wall;
import main.entity.tile.Water;
import main.gfx.Display;
import main.io.IO;

public class Level {
	
	public static final String[] maps = {"/res/maps/map00.txt" , "/res/maps/map01.txt", "/res/maps/map02.txt"};
	public static final int[][] spawns = {{1,1}, {12,1}, {12, 1}};
	private int humans = 1;
	private int ghosts = 0;

	protected ArrayList<Entity> entities = new ArrayList<>();
	public Tile[] tiles = new Tile[9999];
	public static final int TILE_SIZE = 16;
	protected final int CAMERA_TILE_WIDTH = 16;
	protected final int CAMERA_TILE_HEIGHT = 16;
	private Map map;
	private Player player;
	
	public Level(int mapNum) {
		map = IO.loadMap(maps[mapNum]);
		loadTiles();
	}

	public void tick() {
		
		if (player != null) {
			player.tick();
		}
		
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).tick();
		}
		
		for (int i = 0; i < tiles.length; i++) {
			if (tiles[i] != null) {
				tiles[i].tick();
			}
		}
	}
	
	public void render(Display display) {
		//render tiles
		for (int x = 0; x < map.cols ; x++) {
			for (int y = 0; y < map.rows; y++) {
				Tile tile = tiles[map.tileMap[x + y*map.cols]];
				if (x - display.xScroll <= CAMERA_TILE_WIDTH && y - display.yScroll <= CAMERA_TILE_HEIGHT) {
					tile.render(display, x, y);
				}
			}
		}
		
		if (player != null) {
			player.render(display);
		}
		
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(display);
		}
	}
	
	public void removeMob(Mob mob) {
		entities.remove(mob);
	}
	
	//Divide for pixels to coordinates
	public Tile getTileAt(int x, int y) {
		return tiles[map.tileMap[x/CAMERA_TILE_WIDTH + y/CAMERA_TILE_HEIGHT*map.cols]];
	}
	
	private void loadTiles() {
		tiles[0] = new Floor();
		tiles[1] = new Wall();
		tiles[2] = new Tree();
		tiles[3] = new Door();
		tiles[4] = new Chest(this);
		tiles[5] = new Water();
	}
	
	public void loadEntities() {
		for (int i = 0; i < humans; i++) {
				entities.add(new Human(this));
			}
		
		for (int i = 0; i < humans; i++) {
			entities.add(new Ghost(this));
		}
	}

	public Entity getEntityAt(Rectangle rect) {
		for (Entity entity : entities) {
			if (entity.rect != null) {
				Rectangle eRec = new Rectangle(entity.x + entity.rect.x, entity.y + entity.rect.y,
						entity.rect.width, entity.rect.height);
				if (eRec.intersects(rect)) {
						return entity;
				}
			}
		}
		return null;
	}
	
	public int getTileTopLeftX(int x) {
		return x/CAMERA_TILE_WIDTH;
	}
	
	public int getTileTopLeftY(int y) {
		return y/CAMERA_TILE_HEIGHT*map.cols;
	}
	
	public boolean isSameTile(int tileNum, int x, int y) {
		int gridPos = x/CAMERA_TILE_WIDTH + y/CAMERA_TILE_HEIGHT*map.cols;
		
		if (tileNum == map.tileMap[gridPos]) {
			return true;
		}
		return false;
	}
	
	public void addTile(int tileNum, int x, int y) {
	    int gridPos = x/CAMERA_TILE_WIDTH + y/CAMERA_TILE_HEIGHT*map.cols;
		if (tiles[map.tileMap[gridPos]].isBreakable() && !isSameTile(tileNum, x, y)) {
			map.tileMap[gridPos] = tileNum;
		}
	}
	
	public void addEntity(Entity entity) {
		entities.add(entity);
		
	}

	public void removeEntity(Entity entity) {
		entities.remove(entity);
		
	}
	
	public void addPlayer(Player player) {
		this.player = player;
		player.setLevel(this);
	}
	
	public void removePlayer() {
		entities.remove(player);
	}

	public void removeTile(int x, int y) {	
		addTile(0, x, y);
	}

	public Map getMap() {
		return map;
	}

	public Player getPlayer() {
		return player;
	}
}
