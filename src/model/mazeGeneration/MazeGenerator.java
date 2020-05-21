package model.mazeGeneration;

import java.util.Stack;

import model.maps.Node;

public class MazeGenerator {
	
	private Node[][] nodes;
	
	private Stack<Node> stack;
	private Node current;
	
	private boolean running = true;
	
	public MazeGenerator(Node[][] nodes) {
		this.nodes = nodes;
		for(int i = 0; i<nodes.length; i++) {
			for(int j = 0; j<nodes[i].length; j++) {
				this.nodes[i][j].reset();
				if(j%2!=0 || i%2!=0) {
					this.nodes[i][j].setWall(true);
				}
			}
		}
		
		for(int i = 0; i<nodes.length; i++) {
			for(int j = 0; j<nodes[i].length; j++) {
				this.nodes[i][j].setMazeNeighbours(this.nodes);
			}
		}
		
		stack = new Stack<Node>();
		
		current = this.nodes[0][0];
		current.setVisited(true);
		stack.push(current);
	}
	
	public void tick() {
		if(!stack.isEmpty()) {
			current = stack.pop();
			
			Node next = current.getRandomMazeNeighbour();
			if(next!=null) {
				stack.push(current);
				removeWallBetween(current, next);
				next.setVisited(true);
				stack.push(next);
			}
		}else {
			running = false;
		}
	}
	
	public void removeWallBetween(Node n1, Node n2) {
		if (n1.getI() == n2.getI()) {
			if (n2.getJ() < n1.getJ()) {
				nodes[n1.getJ() - 1][n1.getI()].setWall(false);
			} else {
				nodes[n1.getJ() + 1][n1.getI()].setWall(false);
			}
		} else if (n1.getJ() == n2.getJ()) {
			if (n2.getI() < n1.getI()) {
				nodes[n1.getJ()][n1.getI() - 1].setWall(false);
			} else {
				nodes[n1.getJ()][n1.getI() + 1].setWall(false);
			}
		}
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public Node[][] getNodes(){
		return nodes;
	}
	
}
