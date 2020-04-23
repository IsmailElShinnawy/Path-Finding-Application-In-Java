package model.maps;

import java.awt.Color;
import java.awt.Graphics;

public class Node implements Comparable<Node> {

	private int i, j;
	private int size;
	private Color color = Color.white;

	private boolean wall = false;
	private boolean start = false;
	private boolean end = false;

	private double fScore = 0, gScore = 0, hScore = 0;
	private Node previous;
	private Node[] neighbours;

	private static final int NUMBER_OF_NEIGHBOURS = 8;

	public Node(int i, int j, int size) {
		this.i = i;
		this.j = j;
		this.size = size;
	}

	public Node(int i, int j, int size, Color color) {
		this(i, j, size);
		this.color = color;
	}

	public void setNeighbours(Node[][] nodes) {
		neighbours = new Node[NUMBER_OF_NEIGHBOURS];
		if (j > 0) {
			if (!nodes[j - 1][i].isWall())
				neighbours[0] = nodes[j - 1][i];
			if (i < nodes[j].length - 1) {
				if (!nodes[j - 1][i + 1].isWall())
					neighbours[1] = nodes[j - 1][i + 1];
			}
		}
		if (i < nodes[j].length - 1) {
			if (!nodes[j][i + 1].isWall()) {
				neighbours[2] = nodes[j][i + 1];
			}
			if (j < nodes.length - 1) {
				if (!nodes[j + 1][i + 1].isWall())
					neighbours[3] = nodes[j + 1][i + 1];
			}
		}
		if (j < nodes.length - 1) {
			if (!nodes[j + 1][i].isWall())
				neighbours[4] = nodes[j + 1][i];
			if (i > 0) {
				if (!nodes[j + 1][i - 1].isWall())
					neighbours[5] = nodes[j + 1][i - 1];
			}
		}
		if (i > 0) {
			if (!nodes[j][i - 1].isWall())
				neighbours[6] = nodes[j][i - 1];
			if (j > 0) {
				if (!nodes[j - 1][i - 1].isWall())
					neighbours[7] = nodes[j - 1][i - 1];
			}
		}
	}

	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect(i * size, j * size, size, size);
		g.setColor(Color.BLACK);
		g.drawRect(i * size, j * size, size, size);
	}

	public void reset() {
		color = Color.white;
		wall = false;
	}

	public void resetForSearch() {
		fScore = 0;
		gScore = 0;
		hScore = 0;
		previous = null;
		neighbours = null;
	}
	
	public void resetForDijkstraSearch() {
		gScore = Double.POSITIVE_INFINITY;
		previous = null;
		neighbours = null;
	}

	public boolean equals(Node other) {
		return i == other.i && j == other.j;
	}

	public int compareTo(Node other) {
		if (fScore < other.fScore) {
			return 1;
		} else if (fScore > other.fScore) {
			return -1;
		} else {
			return 0;
		}
	}

	public String toString() {
		return "col: " + i + " row " + j + " " + color;
	}

	public boolean isWall() {
		return this.wall;
	}

	public void setWall(boolean wall) {
		this.wall = wall;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean isStart() {
		return this.start;
	}

	public boolean isEnd() {
		return this.end;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

	public void setEnd(boolean end) {
		this.end = end;
	}

	public Node[] getNeighbours() {
		return this.neighbours;
	}

	public double getfScore() {
		return fScore;
	}

	public void setfScore(double fScore) {
		this.fScore = fScore;
	}

	public double getgScore() {
		return gScore;
	}

	public void setgScore(double gScore) {
		this.gScore = gScore;
	}

	public double gethScore() {
		return hScore;
	}

	public void sethScore(double hScore) {
		this.hScore = hScore;
	}

	public int getI() {
		return i;
	}

	public int getJ() {
		return j;
	}

	public void setPrevious(Node previous) {
		this.previous = previous;
	}

	public Node getPrevious() {
		return previous;
	}

}
