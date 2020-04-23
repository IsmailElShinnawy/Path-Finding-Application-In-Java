package viewer;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class MainFrame extends JFrame{
	
	public static final int WIDTH = 800, HEIGHT = 600;
	
	private OptionsPanel optionsPanel;
	private Sketch sketch;
		
	public MainFrame(String windowName) {
		super(windowName);
		
		setSize(new Dimension(WIDTH, HEIGHT));
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		optionsPanel = new OptionsPanel(150, HEIGHT);
		sketch = new Sketch(getWidth()-200, HEIGHT);
		
		optionsPanel.setBounds(0, 0, 200, getHeight());
		sketch.setBounds(200, 0, getWidth()-200, getHeight());
		add(optionsPanel, BorderLayout.WEST);
		add(sketch, BorderLayout.CENTER);
		pack();
	}
	
	public void displayPathDetails(int pathLength, int checks) {
		String message = "Path found in "+ checks +" check(s)\nLength of path = "+pathLength+" node(s)";
		JOptionPane.showMessageDialog(new JFrame(), message, "Path Found", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void displayNoPathMessage() {
		String message = "No path between start and end";
		JOptionPane.showMessageDialog(new JFrame(), message, "No Path", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public OptionsPanel getOptionsPanel() {
		return this.optionsPanel;
	}
	
	public Sketch getSketch() {
		return this.sketch;
	}
	
}
