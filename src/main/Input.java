package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener {
	
	public boolean up, down, left, right, enter, back, dispose, inv;
	public int num = 0;
	
	public Input(Game game) {
		game.addKeyListener(this);
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W -> up = true;
		case KeyEvent.VK_S -> down = true;
		case KeyEvent.VK_A -> left = true;
		case KeyEvent.VK_D -> right = true;
		case KeyEvent.VK_UP -> up = true;
		case KeyEvent.VK_DOWN -> down = true;
		case KeyEvent.VK_LEFT -> left = true;
		case KeyEvent.VK_RIGHT -> right = true;
		case KeyEvent.VK_ENTER -> enter = true;
		case KeyEvent.VK_BACK_SPACE -> back = true;
		case KeyEvent.VK_ESCAPE -> back = true;
		case KeyEvent.VK_Q -> dispose = true;
		case KeyEvent.VK_E -> inv = true;
		
		case KeyEvent.VK_1 -> num = 0;
		case KeyEvent.VK_2 -> num = 1;
		case KeyEvent.VK_3 -> num = 2;
		case KeyEvent.VK_4 -> num = 3;
		case KeyEvent.VK_5 -> num = 4;
		case KeyEvent.VK_6 -> num = 5;
		case KeyEvent.VK_7 -> num = 6;
		case KeyEvent.VK_8 -> num = 7;
		case KeyEvent.VK_9 -> num = 8;
		case KeyEvent.VK_0 -> num = 9;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W -> up = false;
		case KeyEvent.VK_S -> down = false;
		case KeyEvent.VK_A -> left = false;
		case KeyEvent.VK_D -> right = false;
		case KeyEvent.VK_UP -> up = false;
		case KeyEvent.VK_DOWN -> down = false;
		case KeyEvent.VK_LEFT -> left = false;
		case KeyEvent.VK_RIGHT -> right = false;
		case KeyEvent.VK_ENTER -> enter = false;
		case KeyEvent.VK_BACK_SPACE -> back = false;
		case KeyEvent.VK_ESCAPE -> back = false;
		case KeyEvent.VK_Q -> dispose = false;
		}
	}
	
	public void clear() {
		up = false;
		down = false;
		left = false;
		right = false;
		enter = false;
		back = false;
		dispose = false;
	}
	
	public void clearNonMovement() {
		enter = false;
		back = false;
		inv = false;
		dispose = false;
	}
}
