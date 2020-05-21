package model.pathfinding;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Stack;

import model.maps.Node;

public class Dijkstra {
	
	private Node[][] nodes;
	private Node startNode;
	private Node endNode;
	
	private ArrayList<Node> openSet;
	private ArrayList<Node> closedSet;
	private Node current;
	
	private boolean running = true;
	private boolean found = false;
	
	private int checks = 0;
	private int pathLength = 0;
	
	public Dijkstra(Point start, Point end, Node[][] nodes, boolean maze) {
		this.nodes = nodes;
		for(int i = 0; i<this.nodes.length; i++) {
			for(int j = 0; j<this.nodes[i].length; j++) {
				this.nodes[i][j].resetForDijkstraSearch();
				if(maze) {
					this.nodes[i][j].setNeighbours(this.nodes, 4);
				}else {
					this.nodes[i][j].setNeighbours(this.nodes);
				}
			}
		}
		
		startNode = this.nodes[start.y][start.x];
		startNode.setgScore(0);
		endNode = this.nodes[end.y][end.x];
		
		openSet = new ArrayList<Node>();
		
		openSet.add(startNode);
	}
	
	public void tick() {
		if(!openSet.isEmpty()) {
			int winnerIndex = 0;
			for(int i = 0; i<openSet.size(); i++) {
				if(openSet.get(i).getgScore()<openSet.get(winnerIndex).getgScore()) {
					winnerIndex = i;
				}
			}
			current = openSet.remove(winnerIndex);
			checks++;
			if(current.equals(endNode)) {
				running = false;
				found = true;
			}
			
			for(Node neighbour: current.getNeighbours()) {
				if(neighbour==null) continue;
				double tmpgScore = 1+current.getgScore();
				if(tmpgScore<neighbour.getgScore()) {
					openSet.remove(neighbour);
					neighbour.setgScore(tmpgScore);
					neighbour.setPrevious(current);
					openSet.add(neighbour);
				}
			}
			
		}else {
			running = false;
			found  = false;
		}
	}
	
	public Stack<Node> constructPath() {
		Stack<Node> path = new Stack<Node>();
		Node current = endNode;
		while (current != null) {
			pathLength++;
			path.push(current);
			current = current.getPrevious();
		}
		return path;
	}

	public boolean isRunning() {
		return running;
	}

	public ArrayList<Node> getOpenSet() {
		return this.openSet;
	}

	public ArrayList<Node> getClosedSet() {
		return closedSet;
	}

	public boolean isFound() {
		return this.found;
	}
	
	public int getChecks() {
		return checks;
	}
	
	public int getPathLength() {
		return pathLength;
	}
	
}
