package main.Item;

import java.awt.Color;
import java.util.Random;

import main.Game;
import main.entity.mob.Player;
import main.entity.tile.Tile;
import main.entity.tile.Water;
import main.gfx.SpriteSheet;

public class IcePick extends Item {
	
	private Player player;
	private Game game;

	public IcePick(Game game) {
		this.game = game;
		this.player = game.getPlayer();
		this.img = SpriteSheet.getSpriteImage(1*16, 6*16, 16, 16);
		this.icon = img;
		this.main = Color.BLUE;
		this.trim = Color.WHITE;
	}
	
	@Override
	public void tick() {
		super.tick();
	}

	@Override
	public void use() {
		game.levelScreen.player.placeTile(new Water());
	}
}