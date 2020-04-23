package main;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.pathfinding.AStar;
import model.pathfinding.Dijkstra;
import viewer.MainFrame;

public class Controller implements ActionListener, MouseListener, MouseMotionListener, ChangeListener {

	private MainFrame view;
	private AStar astar;
	private Dijkstra dij;
	private Point mouse;
	private boolean mouseInFrame;

	public Controller() {
		view = new MainFrame("Path Finding App");
		mouse = new Point();

		int size = view.getOptionsPanel().getNodeSize();
		view.getSketch().init(size);
		view.getOptionsPanel().setActionListener(this);
		view.getOptionsPanel().setChangeListener(this);
		view.getSketch().addMouseListener(this);
		view.getSketch().addMouseMotionListener(this);
		view.repaint();
		view.revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			JButton b = (JButton) e.getSource();
			if (b.getActionCommand().equals("generateRandomMap")) {
				double density = view.getOptionsPanel().getDensity();
				view.getSketch().getMap().randomize(density);
				view.getSketch().repaint();
			} else if (b.getActionCommand().equals("resetMap")) {
				view.getSketch().getMap().reset();
				view.getSketch().repaint();
			} else if (b.getActionCommand().equals("Start") && view.getSketch().getMap().getStart() != null
					&& view.getSketch().getMap().getEnd() != null) {
//				System.out.println("Started Search...");
				if (view.getOptionsPanel().getAlgorithm().equals("A* search")) {
					startAStarSearch();
				} else if (view.getOptionsPanel().getAlgorithm().equals("Dijkstra's")) {
					startDijkstraSearch();
				}
			}
		} 
	}

	public void startDijkstraSearch() {
		dij = new Dijkstra(view.getSketch().getMap().getStart(), view.getSketch().getMap().getEnd(),
				view.getSketch().getMap().getNodes());
		while (dij.isRunning()) {
			dij.tick();
			view.getSketch().getMap().updateMap(dij.getOpenSet(), dij.getClosedSet());
			view.getSketch().paintImmediately(0, 0, view.getSketch().getWidth(), view.getSketch().getHeight());
			view.getSketch().revalidate();
			try {
				Thread.sleep(30);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (dij.isFound()) {
			view.getSketch().getMap().updatePath(dij.constructPath());
			view.getSketch().repaint();
			view.displayPathDetails(dij.getPathLength(), dij.getChecks());
		}else {
			view.displayNoPathMessage();
		}
	}

	public void startAStarSearch() {
		astar = new AStar(view.getSketch().getMap().getStart(), view.getSketch().getMap().getEnd(),
				view.getSketch().getMap().getNodes());

		while (astar.isRunning()) {
			astar.tick();
			view.getSketch().getMap().updateMap(astar.getOpenSet(), astar.getClosedSet());
			view.getSketch().paintImmediately(0, 0, view.getSketch().getWidth(), view.getSketch().getHeight());
			view.getSketch().revalidate();
			try {
				Thread.sleep(30);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (astar.isFound()) {
			view.getSketch().getMap().updatePath(astar.constructPath());
			view.getSketch().repaint();
			view.displayPathDetails(astar.getPathLength(), astar.getChecks());
		}else {
			view.displayNoPathMessage();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouse = e.getPoint();
		String tool = view.getOptionsPanel().getTool();
		if (tool.equals("Wall") && mouseInFrame) {
			view.getSketch().getMap().updateToWall(mouse.x, mouse.y);
			view.getSketch().repaint();
		} else if (tool.equals("Eraser") && mouseInFrame) {
			view.getSketch().getMap().updateToRemoveWall(mouse.x, mouse.y);
			view.getSketch().repaint();
		} else if (tool.equals("Start")) {
			view.getSketch().getMap().updateToStart(mouse.x, mouse.y);
			view.getSketch().repaint();
		} else if (tool.equals("End")) {
			view.getSketch().getMap().updateToEnd(mouse.x, mouse.y);
			view.getSketch().repaint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		mouseInFrame = true;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		mouseInFrame = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {

		mouse = e.getPoint();
		String tool = view.getOptionsPanel().getTool();
		if (tool.equals("Wall") && mouseInFrame) {
			view.getSketch().getMap().updateToWall(mouse.x, mouse.y);
			view.getSketch().repaint();
		} else if (tool.equals("Eraser") && mouseInFrame) {
			view.getSketch().getMap().updateToRemoveWall(mouse.x, mouse.y);
			view.getSketch().repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() instanceof JSlider) {
			JSlider slider = (JSlider) e.getSource();
			if (slider.getName().equals("Size")) {
				if (slider.getValue() % 10 == 0) {
					view.getSketch().init(slider.getValue());
					view.getSketch().repaint();
				}
			}
		}
	}

	public static void main(String[] args) {
		new Controller();
	}

}
