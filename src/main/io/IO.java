package main.io;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;

import main.entity.Map;

public class IO {
	
	public static BufferedImage loadImage(String filepath) {
		
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(IO.class.getResourceAsStream(filepath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return image;
	}
	
	public static Map loadMap(String filepath) {
		
		BufferedReader fileReader = new BufferedReader(new InputStreamReader(IO.class.getResourceAsStream(filepath)));
		int[] output = new int[99999];
		
		try {
			String line = fileReader.readLine();
			String[] split = null;
			int row = 0;
			int rows = 0;
			
			while (line != null) {
				
				split = line.split(" ");
				for (int i = 0; i < split.length; i++) {
					
					output[row] = Integer.parseInt(split[i]);
					row++;
				}
				rows++;
				line = fileReader.readLine();
			}
			fileReader.close();
			int cols = split.length;
			return new Map(output, rows, cols);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
