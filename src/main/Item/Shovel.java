package main.Item;

import java.awt.Color;
import java.util.Random;

import main.Game;
import main.entity.mob.Player;
import main.entity.tile.Tile;
import main.entity.tile.Water;
import main.gfx.SpriteSheet;

public class Shovel extends Item {
	
	private Player player;
	private Game game;

	public Shovel(Game game) {
		this.game = game;
		this.player = game.getPlayer();
		this.img = SpriteSheet.getSpriteImage(2*16, 7*16, 16, 16);
		this.icon = img;
		this.main = Color.RED;
		this.trim = Color.WHITE;
	}
	
	@Override
	public void tick() {
		super.tick();
	}

	@Override
	public void use() {
		player.removeTile();
	}
}