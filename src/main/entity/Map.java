package main.entity;

import main.gfx.Display;

public class Map {
	
	public final int[] tileMap;
	public final int rows;
	public final int cols;
	public final int pw;
	public final int ph;
	
	public Map(int[] tileMap, int rows, int cols) {
		
		this.tileMap = tileMap;
		this.rows = rows;
		this.cols = cols;
		this.pw = cols * Display.TILE_SIZE;
		this.ph = rows * Display.TILE_SIZE;
	}	
}