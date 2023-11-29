package main.screen.menu;

import main.Game;
import main.Input;
import main.entity.mob.Mob;
import main.gfx.Display;
import main.gfx.Font;

public class FightMenu extends Menu {
	
	private Mob enemy;
	private final String[] DIALOGUE = {"Hey buddy pal", "ur a cool guy", "wee booo woo boo", "veri epik"};
	private String[] lines;
	private int dialogueCounter = 0;
	private final int MAX_DIALOGUE;
	private boolean playerTurn, talking, main, action, passive, item;
	private final int MAIN_SELECTS = 2;
	private final int ACT_SELECTS = 2;
	private final int PASSIVE_SELECTS = 0;
	private int ITEM_SELECTS = 0;
	private final int ATTACK = 0;
	
	public FightMenu(Game game, Input input) {
		super(game, input);
		
		MAX_DIALOGUE = DIALOGUE.length - 1;
		
		processDialogue();
		
		playerTurn = true;
		talking = true;
		setMain();
	}
	
	private void processDialogue() {
		
		StringBuilder sb = new StringBuilder(DIALOGUE[0]);

		int i = 0;
		while (i + 10 < sb.length() && (i = sb.lastIndexOf(" ", i + 10)) != -1) {
			//System.out.println(sb.lastIndexOf(" ", i + 10));
		    sb.replace(i, i + 1, "\n");
		}
		
	}
	
	@Override
	public void tick() {
		
		enemy.tick();
		
		if (playerTurn) {
			handleInput();
		} else if (enemy.getHp() > 0) {
			handleEnemy();
			playerTurn = true;
		}
		
		//dodgy
		game.getPlayer().tick();
		//
		
		super.tick();
		
	}
	
	private void handleEnemy() {
		game.getPlayer().damage(1);
	}

	private void handleInput() {
		
		if (talking) {
			if (input.enter) {
				if (dialogueCounter < MAX_DIALOGUE) {
					dialogueCounter++;
				}
				
				if (dialogueCounter >= MAX_DIALOGUE) {
					talking = false;
				}
			}
		
		} else {
			
			if (action) {
				if (input.enter) {
					switch(selected) {
						case ATTACK -> enemy.damage(1);
					}
					playerTurn = false;
				}
			}
			
			if (passive) {
				if (input.enter) {
					switch(selected) {
						case 0 -> game.setScreen(game.levelScreen);
					}
					playerTurn = false;
				}
			}
			
			if (!main) {
				if (input.back) {
					setMain();
				}
			}
			
			if (main) {
				if (input.enter) {
					switch(selected) {
						case 0 -> setAction();
						case 1 -> setPassive();
						case 2 -> setItem();
					}
				}
			}
		}
	}
	
	private void setMain() {
		main = true;
		action = false;
		passive = false;
		item = false;
		MAX_SELECTION = MAIN_SELECTS;
		selected = 0;
	}
	
	private void setAction() {
		main = false;
		action = true;
		passive = false;
		item = false;
		MAX_SELECTION = ACT_SELECTS;
		selected = 0;
	}
	
	private void setPassive() {
		main = false;
		action = false;
		passive = true;
		item = false;
		MAX_SELECTION = PASSIVE_SELECTS;
		selected = 0;
	}
	
	private void setItem() {
		main = false;
		action = false;
		passive = false;
		item = true;
		MAX_SELECTION = ITEM_SELECTS;
		selected = 0;
	}
	
	public void render(Display display) {
		
		font.draw(DIALOGUE[dialogueCounter], display, 10*Font.SIZE, 26* Font.SIZE);
		
		if (main) {
			font.draw("action", display, 1*Font.SIZE, 26*Font.SIZE);
			font.draw("passive", display, 1*Font.SIZE, 28*Font.SIZE);
			font.draw("item", display, 1*Font.SIZE, 30*Font.SIZE);
		}
		
		if (action) {
			font.draw("attack", display, 1*Font.SIZE, 26*Font.SIZE);
			font.draw("wait", display, 1*Font.SIZE, 28*Font.SIZE);
			font.draw("flee", display, 1*Font.SIZE, 30*Font.SIZE);
		}
		
		if (passive) {
			font.draw("wait", display, 1*Font.SIZE, 26*Font.SIZE);	
		}
		
		if (item) {
			font.draw("banana", display, 1*Font.SIZE, 26*Font.SIZE);
		}
		
		if (!talking) {
			font.draw(">", display, 0, (26 + selected*2) * Font.SIZE);
		} else {
			font.draw(">", display, 9*Font.SIZE, 26* Font.SIZE);
		}
		
		
		//drawing enemy
		enemy.x = 16*8;
		enemy.y = 16*8;
		enemy.render(display);
		font.draw(enemy.getClass().getSimpleName(), display, 1*Font.SIZE);	
		//draw enemy hp
		font.draw(Integer.toString(enemy.getHp()), display, 3*Font.SIZE);
		
		//draw player hp
		font.draw(Integer.toString(game.getPlayer().getHp()), display, 10*Font.SIZE);
	}
	
	public void setEnemy(Mob enemy) {
		this.enemy = enemy;
	}
}
