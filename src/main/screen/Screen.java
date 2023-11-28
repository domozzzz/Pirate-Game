package main.screen;

import java.awt.Rectangle;
import java.util.ArrayList;

import main.Game;
import main.Input;
import main.entity.Bullet;
import main.entity.Entity;
import main.entity.Map;
import main.entity.mob.Human;
import main.entity.mob.Mob;
import main.entity.mob.Player;
import main.entity.tile.Tile;
import main.gfx.Font;
import main.gfx.Display;

public class Screen { 
	
	public static final int TILE_SIZE = 16;
	protected final int CAMERA_TILE_WIDTH = 16;
	protected final int CAMERA_TILE_HEIGHT = 16;
	protected ArrayList<Entity> entities = new ArrayList<>();
	public Map map;
	protected Tile[] tiles = new Tile[4096];
	
	protected Game game;
	protected Font font;
	public Player player;
	
	public Screen(Game game) {
		this.player = game.getPlayer();
		this.game = game;
		font = game.getDisplay().getFont();
	}
	
	public Map getMap() {	
		return map;
	}
	
	public Tile getTileAt(int x, int y) {
		return tiles[map.tileMap[x/CAMERA_TILE_WIDTH + y/CAMERA_TILE_HEIGHT*map.cols]];
	}
	
	public void tick() {
		if (player != null) {
			player.tick();
		}
		
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).tick();
		}
	}

	public void render(Display display) {
		if (player != null) {
			player.render(display);
		}
		
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(display);
		}
	}

	public void addPlayer(Player player) {
		this.player = player;
	}

	public void removePlayer() {
		this.player = null;
	}

	public void removeMob(Mob mob) {
		entities.remove(mob);
	}

	public Tile[] getTiles() {
		return tiles;
	}

	public void setTiles(Tile[] tiles) {
		this.tiles = tiles;
	}

	public int getTileTopLeftX(int x) {
		return x/CAMERA_TILE_WIDTH;
	}
	
	public int getTileTopLeftY(int y) {
		return y/CAMERA_TILE_HEIGHT*map.cols;
	}
	
	public void addEntity(Entity entity) {
		entities.add(entity);
		
	}

	public void removeEntity(Entity entity) {
		entities.remove(entity);
		
	}
}
