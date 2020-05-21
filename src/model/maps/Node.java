package model.maps;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Node  {

	private int i, j;
	private int size;
	private Color color = Color.white;

	private boolean wall = false;
	private boolean start = false;
	private boolean end = false;
	private boolean visited = false;

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
	
	public void setNeighbours(Node[][] nodes, int numberOfNeighbours) {
		neighbours = new Node[numberOfNeighbours];
		if (j > 0) {
			if (!nodes[j - 1][i].isWall())
				neighbours[0] = nodes[j - 1][i];
		}
		if (i < nodes[j].length - 1) {
			if (!nodes[j][i + 1].isWall()) {
				neighbours[1] = nodes[j][i + 1];
			}
		}
		if (j < nodes.length - 1) {
			if (!nodes[j + 1][i].isWall())
				neighbours[2] = nodes[j + 1][i];
		}
		if (i > 0) {
			if (!nodes[j][i - 1].isWall())
				neighbours[3] = nodes[j][i - 1];
		}
	}
	
	public void setMazeNeighbours(Node[][] nodes) {
		neighbours = new Node[4];
		if(!wall) {
			if(j>1) {
				neighbours[0] = nodes[j-2][i];
			}
			if(j<nodes.length-2) {
				neighbours[1] = nodes[j+2][i];
			}
			if(i>1) {
				neighbours[2] = nodes[j][i-2];
			}
			if(i<nodes[j].length-2) {
				neighbours[3] = nodes[j][i+2];
			}
		}
	}
	
	public Node getRandomMazeNeighbour() {
		ArrayList<Node> tmp = new ArrayList<Node>();

		for (Node n : neighbours)
			if (n != null)
				if (!n.isVisited())
					tmp.add(n);

		if (tmp.size() == 0)
			return null;
		return tmp.get((int) (Math.random() * tmp.size()));
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
		start = false;
		end = false;
		//
		visited = false;
	}

	public void resetForSearch() {
		fScore = 0;
		gScore = 0;
		hScore = 0;
		previous = null;
		neighbours = null;
		visited = false;
		if(!wall) color = Color.white;
		if(start) color = Color.green;
		if(end) color = Color.red;
	}
	
	public void resetForDijkstraSearch() {
		resetForSearch();
		gScore = Double.POSITIVE_INFINITY;
	}

	public boolean equals(Node other) {
		return i == other.i && j == other.j;
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
	
	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public Node[] getNeighbours() {
		return this.neighbours;
	}

	public double getfScore() {
		return fScore;
	}
	
	public void setPrevious(Node previous) {
		this.previous = previous;
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

	public Node getPrevious() {
		return previous;
	}
	
	public boolean isVisited() {
		return visited;
	}
	
	

}
