package model.pathfinding;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Stack;

import model.maps.Node;

public class AStar {

	private Node startNode;
	private Node endNode;
	private Node[][] nodes;
	private boolean running = true;

	private ArrayList<Node> openSet;
	private ArrayList<Node> closedSet;
	private Node current;
	private boolean found = false;
	
	private int checks = 0;
	private int pathLength = 0;

	public AStar(Point start, Point end, Node[][] nodes) {
		this.nodes = nodes;
		for (int i = 0; i < this.nodes.length; i++) {
			for (int j = 0; j < this.nodes[i].length; j++) {
				this.nodes[i][j].resetForSearch();
				this.nodes[i][j].setNeighbours(this.nodes);
			}
		}

		startNode = this.nodes[start.y][start.x];
		endNode = this.nodes[end.y][end.x];

		openSet = new ArrayList<Node>();
		closedSet = new ArrayList<Node>();

		openSet.add(startNode);
	}

	public void tick() {
		if (!openSet.isEmpty()) {
			int winnerIndex = 0;
			for (int i = 0; i < openSet.size(); i++) {
				if (openSet.get(i).getfScore() < openSet.get(winnerIndex).getfScore()) {
					winnerIndex = i;
				}
			}
			current = openSet.remove(winnerIndex);
			checks++;
			if (current.equals(endNode)) {
				running = false;
				found = true;
			}

			closedSet.add(current);
			for (Node neighbour : current.getNeighbours()) {
				if (neighbour == null)
					continue;
				if (!closedSet.contains(neighbour)) {
					double tmpGScore = 1 + current.getgScore();
					if (openSet.contains(neighbour)) {
						if (tmpGScore < neighbour.getgScore()) {
							neighbour.setgScore(tmpGScore);
							neighbour.sethScore(eucideanDistance(neighbour.getI(), neighbour.getJ(), endNode.getI(),
									endNode.getJ()));
							neighbour.setfScore(neighbour.getgScore() + neighbour.gethScore());
							neighbour.setPrevious(current);
						}
					} else {
						neighbour.setgScore(tmpGScore);
						neighbour.sethScore(
								eucideanDistance(neighbour.getI(), neighbour.getJ(), endNode.getI(), endNode.getJ()));
						neighbour.setfScore(neighbour.getgScore() + neighbour.gethScore());
						neighbour.setPrevious(current);
						openSet.add(neighbour);
					}
				}
			}

		} else {
			running = false;
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

	public static double eucideanDistance(int x1, int y1, int x2, int y2) {
		return Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2);
	}

}
