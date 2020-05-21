package viewer;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import model.maps.Map;

@SuppressWarnings("serial")
public class Sketch extends JPanel{
	
	private Map map;
	private int width;
		
	public Sketch(int width, int height) {
		super();
		this.width = width;
		setSize(new Dimension(width, height));
		setPreferredSize(new Dimension(width, height));
		setFocusable(false);
		setVisible(true);
	}
	
	public void init(int size) {
		map = new Map(width/size, size);
	}
		
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(map==null) return;
		map.render(g);
	}
	
	public Map getMap() {
		return map;
	}
	
}
