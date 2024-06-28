package main.gfx.particle;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

import main.Level;
import main.entity.Entity;
import main.gfx.Display;

public class Particle extends Entity{
	
	int size = 1;
	int ticks;
	int speed = 1;
	int x, x1, x2, x3;
	int y, y1, y2, y3;
	private Level level;
	double random;

	public Particle(Level level, Color color, int ticks, int x, int y) {
		this.level = level;
		this.ticks = ticks;
		
		this.x = x;
		this.x1 = x;
		this.x2 = x;
		this.x3 = x;
		
		this.y = y;
		this.y1 = y;
		this.y2 = y;
		this.y3 = y;
		
		random = Math.random();
		
		image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		int[] data = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		Arrays.fill(data, color.getRGB());

	}
	
	public void tick() {
		super.tick();
		
		if (random < 0.25) {
		    x += speed;
		    y += speed;
		    
			x1 -= speed;
			y1 -= speed;
			
			x2 -= speed;
			y2 += speed;
			
		} else if (random < 0.5){
		    x += speed;
		    y += speed;
		    
			x1 -= speed;
			y1 -= speed;
			
			x2 += speed;
			y2 -= speed;
			
		} else if (random < 0.75){
		    x += speed;
		    y += speed;
		    
			x1 -= speed;
			y1 += speed;
			
			x2 += speed;
			y2 -= speed;
			
		} else {
		    x -= speed;
		    y -= speed;
		    
			x1 -= speed;
			y1 += speed;
			
			x2 += speed;
			y2 -= speed;
		}
	
		
		if (0 >= --ticks)  {
			level.removeEntity(this);
		}	
	}
	
	@Override
	public void render(Display display) {
		display.render(image, x, y, 0);
		display.render(image, x1, y1, 0);
		display.render(image, x2, y2, 0);
	}
}
