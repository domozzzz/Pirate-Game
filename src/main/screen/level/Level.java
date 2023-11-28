package main.screen.level;

import java.awt.Rectangle;

import main.Game;
import main.entity.Entity;
import main.entity.mob.Ghost;
import main.entity.mob.Human;
import main.entity.tile.Chest;
import main.entity.tile.Door;
import main.entity.tile.Floor;
import main.entity.tile.Tile;
import main.entity.tile.Wall;
import main.entity.tile.Water;
import main.gfx.Display;
import main.io.IO;
import main.screen.Screen;

public class Level extends Screen {
	
	public static final String[] maps = {"/res/maps/map00.txt" , "/res/maps/map01.txt", "/res/maps/map02.txt"};
	private int mobCount = 20;
	
	public Level(Game game, int mapNum) {
		super(game);
		map = IO.loadMap(maps[mapNum]);
		loadTiles();
	}

	@Override
	public void tick() {
		super.tick();
		
		for (int i = 0; i < getTiles().length; i++) {
			if (getTiles()[i] != null) {
				getTiles()[i].tick();
			}
		}
	}
	
	@Override
	public void render(Display display) {
		//render tiles
		for (int x = 0; x < map.cols ; x++) {
			for (int y = 0; y < map.rows; y++) {
				Tile tile = getTiles()[map.tileMap[x + y*map.cols]];
				if (x - display.xScroll <= CAMERA_TILE_WIDTH && y - display.yScroll <= CAMERA_TILE_HEIGHT) {
					tile.render(display, x, y, 0);
				}
			}
		}
		super.render(display);
	}
	
	//Divide for pixels to coordinates
	public Tile getTileAt(int x, int y) {
		return tiles[map.tileMap[x/CAMERA_TILE_WIDTH + y/CAMERA_TILE_HEIGHT*map.cols]];
	}
	
	private void loadTiles() {
		tiles[0] = new Floor();
		tiles[1] = new Wall();
		tiles[3] = new Door();
		tiles[4] = new Chest(game);
		tiles[5] = new Water();
	}
	
	public void loadEntities() {
		for (int i = 0; i < mobCount; i++) {
			if (i % 2 == 0) {
				entities.add(new Human(game));
			} else {
				entities.add(new Ghost(game));
			}
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

	public void removeTile(int x, int y) {	
		addTile(0, x, y);
	}
}
