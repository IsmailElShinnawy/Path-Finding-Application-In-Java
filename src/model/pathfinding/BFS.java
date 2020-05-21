package model.pathfinding;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import model.maps.Node;

public class BFS {
	
	private Node startNode;
	private Node endNode;
	private Node[][] nodes;
	private boolean running = true;
	
	private Queue<Node> q;
	private ArrayList<Node> closedSet;
	private Node current;
	private boolean found = false;
	
	private int checks = 0;
	private int pathLength = 0;
	
	public BFS(Point start, Point end, Node[][] nodes, boolean maze) {
		this.nodes = nodes;
		for (int i = 0; i < this.nodes.length; i++) {
			for (int j = 0; j < this.nodes[i].length; j++) {
				this.nodes[i][j].resetForSearch();
				if(maze) {
					this.nodes[i][j].setNeighbours(this.nodes, 4);
				}else {
					this.nodes[i][j].setNeighbours(this.nodes);
				}
			}
		}

		startNode = this.nodes[start.y][start.x];
		endNode = this.nodes[end.y][end.x];
		
		q = new LinkedList<Node>();
		closedSet = new ArrayList<Node>();

		startNode.setVisited(true);
		q.add(startNode);
	}
	
	public void tick() {
		if(!q.isEmpty()) {
			
			current = q.poll();
			
			checks++;
			if(current.equals(endNode)) {
				running = false;
				found = true;
			}
			
			closedSet.add(current);
			for(Node n: current.getNeighbours()) {
				if(n==null) continue;
				if(!n.isVisited()) {
					n.setVisited(true);
					n.setPrevious(current);
					q.add(n);
				}
			}
			
		}else {
			running = false;
			found = false;
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
	
	public Queue<Node> getQ() {
		return this.q;
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
