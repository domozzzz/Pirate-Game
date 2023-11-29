//package main.screen.menu;
//
//import java.awt.Color;
//import java.awt.image.BufferedImage;
//import java.util.Arrays;
//import java.util.List;
//import java.util.ListIterator;
//
//import main.Game;
//import main.Input;
//import main.entity.mob.Player;
//import main.gfx.Display;
//import main.gfx.Font;
//import main.gfx.SpriteSheet;
//
//public class CharacterMenu extends Menu {
//	
//	private Player player;
//	private BufferedImage playerPreview;
//	private List<Color> colors = Arrays.asList(Color.BLACK, Color.WHITE, Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW);
//	private ListIterator<Color> shirtIterator = colors.listIterator();
//	private ListIterator<Color> pantsIterator = colors.listIterator();
//	private ListIterator<Color> shoesIterator = colors.listIterator();
//	
//	public CharacterMenu(Game game, Input input, Player player) {
//		super(game, input);
//		this.player = player;
//		MAX_SELECTION = 2;
//		
//		playerPreview = SpriteSheet.getSpriteImage(0, 6*8, 16, 16);
//	}
//	
//	@Override
//	public void tick() {
//		
//		if (input.back) {
//			game.setScreen(game.titleMenu);
//		}
//		
//		switch (selected) {
//		case 0: 
//			player.shirtColor = iterate(shirtIterator);
//			break;
//		case 1: 
//			player.pantsColor = iterate(pantsIterator);
//			break;
//		case 2:
//			player.shoesColor = iterate(shoesIterator);
//			break;
//		}
//		super.tick();
//	}
//	
//	private Color iterate(ListIterator<Color> iterator) {
//		Color color = null;
//		if (iterator.hasNext()) {
//			color = iterator.next();
//			color = iterator.previous();
//		} else {
//			color = iterator.previous();
//			color = iterator.next();
//		}
//		if (input.right && iterator.hasNext()) {
//			color = iterator.next();
//		}
//
//		if (input.left && iterator.hasPrevious()) {
//			color = iterator.previous();
//		}
//		return color;
//	}
//	
//	public void render(Display display) {
//		font.draw("Customize", display, 0, 0);
//		display.render(playerPreview, 3*Font.SIZE, 3*Font.SIZE, 0);
//		
//		//Buttons
//		font.draw("Shirt   "+ getColor(player.shirtColor), display, 5*Font.SIZE, 16*Font.SIZE);
//		font.draw("Pants   "+ getColor(player.pantsColor), display, 5*Font.SIZE, 18*Font.SIZE);
//		font.draw("Shoes   "+ getColor(player.shoesColor), display, 5*Font.SIZE, 20*Font.SIZE);
//		
//		switch (selected) {	
//		case 0://shirt
//			if (shirtIterator.hasPrevious()) {
//			font.draw("<", display, 12*Font.SIZE, 16*Font.SIZE);
//			}
//			if (shirtIterator.hasNext()) {
//				font.draw(">", display, 18*Font.SIZE, 16*Font.SIZE);
//			}
//			break;
//		case 1: //pants
//			if (pantsIterator.hasPrevious()) {
//			font.draw("<", display, 12*Font.SIZE, 18*Font.SIZE);
//			}
//			if (pantsIterator.hasNext()) {
//				font.draw(">", display, 18*Font.SIZE, 18*Font.SIZE);
//			}
//			break;
//		case 2: //shoes
//			if (shoesIterator.hasPrevious()) {
//			font.draw("<", display, 12*Font.SIZE, 20*Font.SIZE);
//			}
//			if (shoesIterator.hasNext()) {
//				font.draw(">", display, 18*Font.SIZE, 20*Font.SIZE);
//			}
//			break;
//		}
//	}
//	
//	private String getColor(Color color) {
//		if (color.getRGB() == Color.WHITE.getRGB()) {
//			return "white";
//		}
//		if (color.getRGB() == Color.RED.getRGB()) {
//			return " red";
//		}
//		if (color.getRGB() == Color.GREEN.getRGB()) {
//			return "green";
//		}
//		if (color.getRGB() == Color.BLUE.getRGB()) {
//			return "bluee";
//		}
//		if (color.getRGB() == Color.YELLOW.getRGB()) {
//			return "yello";
//		}
//		return "black";
//	}
//}
