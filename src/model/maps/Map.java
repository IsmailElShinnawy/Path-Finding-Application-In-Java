package model.maps;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;

public class Map {

	private Node[][] nodes;
	private int size;

	private Point start;
	private Point end;

	public Map(int dim, int size) {
		this.size = size;
		nodes = new Node[dim][dim];
		for (int j = 0; j < nodes.length; j++)
			for (int i = 0; i < nodes[j].length; i++)
				nodes[j][i] = new Node(i, j, size);
	}

	public void render(Graphics g) {
		for (int i = 0; i < nodes.length; i++) {
			for (int j = 0; j < nodes[i].length; j++) {
				nodes[i][j].render(g);
			}
		}
	}

	public void randomize(double density) {
		reset();
		for (int i = 0; i < nodes.length; i++) {
			for (int j = 0; j < nodes[i].length; j++) {
				if (nodes[i][j].isWall()) {
					nodes[i][j].setWall(false);
					nodes[i][j].setColor(Color.WHITE);
				}
				if (Math.random() < density) {
					nodes[i][j].setWall(true);
					nodes[i][j].setColor(Color.BLACK);
				}
			}
		}
	}

	public void reset() {
		if (start != null) {
			nodes[start.y][start.x].setStart(false);
			nodes[start.y][start.x].setColor(Color.WHITE);
			start = null;
		}
		if (end != null) {
			nodes[end.y][end.x].setEnd(false);
			nodes[end.y][end.x].setColor(Color.WHITE);
			end = null;
		}
		for (int i = 0; i < nodes.length; i++) {
			for (int j = 0; j < nodes[i].length; j++) {
				nodes[i][j].reset();
			}
		}
	}

	public void updateToWall(int x, int y) {
		int targetI = x / size;
		int targetJ = y / size;

		nodes[targetJ][targetI].setWall(true);
		nodes[targetJ][targetI].setColor(Color.BLACK);
	}

	public void updateToRemoveWall(int x, int y) {
		int targetI = x / size;
		int targetJ = y / size;

		nodes[targetJ][targetI].setWall(false);
		if(nodes[targetJ][targetI].isStart()) {
			nodes[targetJ][targetI].setStart(false);
			start = null;
		}
		if(nodes[targetJ][targetI].isEnd()) {
			nodes[targetJ][targetI].setEnd(false);
			end = null;
		}
		nodes[targetJ][targetI].setColor(Color.WHITE);
	}

	public void updateToStart(int x, int y) {
		int targetI = x / size;
		int targetJ = y / size;
		if (start != null) {
			nodes[start.y][start.x].setStart(false);
			if(!nodes[start.y][start.x].isWall()) {
				nodes[start.y][start.x].setColor(Color.WHITE);
			}
		}
		start = new Point(targetI, targetJ);
		if (end != null) {
			if (start.x == end.x && start.y == end.y) {
				end = null;
			}
		}
		nodes[targetJ][targetI].setStart(true);
		nodes[targetJ][targetI].setEnd(false);
		nodes[targetJ][targetI].setWall(false);
		nodes[targetJ][targetI].setColor(Color.GREEN);
	}

	public void updateToEnd(int x, int y) {
		int targetI = x / size;
		int targetJ = y / size;

		if (end != null) {
			nodes[end.y][end.x].setEnd(false);
			if(!nodes[start.y][start.x].isWall()) {
				nodes[end.y][end.x].setColor(Color.WHITE);
			}
		}
		end = new Point(targetI, targetJ);
		if (start != null) {
			if (start.x == end.x && start.y == end.y) {
				start = null;
			}
		}
		nodes[targetJ][targetI].setEnd(true);
		nodes[targetJ][targetI].setStart(false);
		nodes[targetJ][targetI].setWall(false);
		nodes[targetJ][targetI].setColor(Color.RED);
	}

	public void updateMap(ArrayList<Node> openSet, ArrayList<Node> closedSet) {
		for (Node node : openSet) {
			nodes[node.getJ()][node.getI()].setColor(Color.GREEN);
		}
		if (closedSet != null) {
			for (Node node : closedSet) {
				nodes[node.getJ()][node.getI()].setColor(Color.RED);
			}
		}
	}
	
	public void updateMap(Queue<Node> openSet, ArrayList<Node> closedSet) {
		for (Node node : openSet) {
			nodes[node.getJ()][node.getI()].setColor(Color.GREEN);
		}
		if (closedSet != null) {
			for (Node node : closedSet) {
				nodes[node.getJ()][node.getI()].setColor(Color.RED);
			}
		}
	}
	
	public void updateMap(Node[][] nodes) {
		for(int i = 0;  i<nodes.length; i++) {
			for(int j = 0; j<nodes[i].length; j++) {
				if(nodes[i][j].isWall()) {
					this.nodes[i][j].setWall(true);
					this.nodes[i][j].setColor(Color.BLACK);
				}else {
					this.nodes[i][j].setWall(false);
					this.nodes[i][j].setColor(Color.WHITE);
				}
			}
		}
	}

	public void updatePath(Stack<Node> path) {
		for (Node node : path) {
			nodes[node.getJ()][node.getI()].setColor(Color.BLUE);
		}
	}

	public Node[][] getNodes() {
		return this.nodes;
	}

	public Point getStart() {
		return start;
	}

	public Point getEnd() {
		return this.end;
	}

}
