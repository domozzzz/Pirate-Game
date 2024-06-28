package main.ai;

import java.util.ArrayList;

import main.Game;
import main.Level;
import main.entity.Entity;
import main.gfx.SpriteSheet;

public class PathFinder {
	
	
	Level level;
	Node[][] node;
	ArrayList<Node> openList = new ArrayList<>();
	public ArrayList<Node> pathList = new ArrayList<>();
	Node startNode, goalNode, currentNode;
	boolean goalReached = false;
	int step = 0;
	
	public PathFinder(Level level) {
		this.level = level;
		instantiateNodes();
	}
	
	public void instantiateNodes() {
		node = new Node[level.getMap().cols][level.getMap().rows];
		
		int col = 0;
		int row = 0;
		
		while (col < level.getMap().cols && row < level.getMap().rows) {
			
			node[col][row] = new Node(row, col);
			
			col++;
			if (col == level.getMap().cols) {
				col = 0;
				row++;
			}
		}
		openList.clear();
		pathList.clear();
		goalReached = false;
		step = 0;
	}
	
	public void setNodes(int startCol, int startRow, int goalCol, int goalRow, Entity entity) {
		
		resetNodes();
		
		startNode = node[startCol][startRow];
		currentNode = startNode;
		goalNode = node[goalCol][goalRow];
		openList.add(currentNode);
		
		int col = 0;
		int row = 0;
		
		while (col < level.getMap().cols && row < level.getMap().rows)  {
			
			int tileNum = level.getMap().tileMap[level.getMap().cols * row + col];
			if (tileNum == 1)  {
				node[col][row].solid = true;
				//game.getDisplay().render(SpriteSheet.getSpriteImage(2*16, 7*16, 16, 16), col*16, row*16, 0);
			}
			
			getCost(node[col][row]);
			
			col++;
			if (col == level.getMap().cols) {
				col = 0;
				row++;
			}
		}
	}
	
	public void getCost(Node node) {
		
		int xDist = Math.abs(node.col - startNode.col);
		int yDist = Math.abs(node.row - startNode.row);
		node.gCost = xDist + yDist;
		
		xDist = Math.abs(node.col - goalNode.col);
		yDist = Math.abs(node.row - goalNode.row);
		node.hCost = xDist + yDist;
		
		node.fCost = node.gCost + node.hCost;
	}
	
	public boolean search()  {
		
		while (goalReached == false)  {
			
			int col = currentNode.col;
			int row = currentNode.row;
			
			currentNode.checked = true;
			openList.remove(currentNode);
			
			if (row - 1 >= 0)  {
				openNode(node[col][row-1]);
			}
			if (col - 1 >= 0)  {
				openNode(node[col-1][row]);
			}
			if (row + 1 < level.getMap().rows)  {
				openNode(node[col][row+1]);
			}
			if (col + 1 < level.getMap().cols)  {
				openNode(node[col+1][row]);
			}
			if (col + 1 < level.getMap().cols && row + 1 < level.getMap().rows)  {
				openNode(node[col+1][row+1]);
			}
			if (col + 1 < level.getMap().cols && row - 1 >= 0)  {
				openNode(node[col+1][row-1]);
			}
			if (col - 1 >= 0 && row - 1 >= 0)  {
				openNode(node[col-1][row-1]);
			}
			if (col - 1 >= 0 && row + 1 < level.getMap().rows)  {
				openNode(node[col-1][row+1]);
			}
			
			int bestNodeIndex = 0;
			int bestNodefCost = 999;
			
			for (int i = 0; i < openList.size(); i++)  {
				
				if (openList.get(i).fCost < bestNodefCost) {
					bestNodeIndex = i;
					bestNodefCost = openList.get(i).fCost;
					
				} else if (openList.get(i).fCost == bestNodefCost) {
					if (openList.get(i).fCost < openList.get(bestNodeIndex).gCost) {
						bestNodeIndex = i;
						
					}
				}
			}
				
			if (openList.size() == 0) {
				break;
			}
			
			currentNode = openList.get(bestNodeIndex);
			
			if (currentNode == goalNode) {
				goalReached = true;
				trackThePath();
			}
			step++;
		
		}
		return goalReached;
	}
	
	public void trackThePath() {
		
		Node current = goalNode;
		
		while (current != startNode) {
			
			pathList.add(0, current);
			current = current.parent;
			
		}
		
	}
		
	public void openNode(Node node) {
		if (node.open == false && node.checked == false && node.solid == false) {
			//game.getDisplay().render(SpriteSheet.getSpriteImage(1*16, 7*16, 16, 16), node.col*16, node.row*16, 0);
			node.open = true;
			node.parent = currentNode;
			openList.add(node);
		}
	}
	
	public void resetNodes() {
		int col = 0;
		int row = 0;
		
		while (col < level.getMap().cols && row < level.getMap().rows)  {
			
			node[col][row].open = false;
			node[col][row].checked = false;
			node[col][row].solid = false;
			
			col++;
			
			if (col == level.getMap().cols) {
				col = 0;
				row++;
			}
		}
		openList.clear();
		pathList.clear();
		goalReached = false;
		step = 0;
	}
}
