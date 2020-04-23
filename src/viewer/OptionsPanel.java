package viewer;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class OptionsPanel extends JPanel{
	
	private JLabel algorithmsLabel;
	private JComboBox<String> algorithmsComboBox;
	private final String[] algorithms = {"A* search", "Dijkstra's", "BFS"};
	
	private JButton generateRandomMap;
	private JButton resetMap;
	private JButton loadMap;
	private JButton saveMap;
	
	private JLabel densityLabel;
	private JSlider densitySlider;
	private JLabel sizeLabel;
	private JSlider sizeSlider;
	
	private JButton startSearchButton;
	private JComboBox<String> toolsComboBox;
	private final String[] tools = {"Start", "End", "Wall", "Eraser"};
	
	public OptionsPanel(int width, int height) {
		
		//setting up panel
		
		setSize(width, height);
		setBorder(BorderFactory.createTitledBorder(" Options "));
		setVisible(true);
		
		//setting up controls
		
			//algorithms combo box
		
		algorithmsLabel = new JLabel("Algoritm:");
		algorithmsComboBox = new JComboBox<String>();
		DefaultComboBoxModel<String> list = new DefaultComboBoxModel<String>();
		for(String algorithm: algorithms) {
			list.addElement(algorithm);
		}
		list.setSelectedItem(list.getElementAt(0));
		algorithmsComboBox.setModel(list);
		
			//map buttons
		
		generateRandomMap = new JButton("Generate Random Map");
		resetMap = new JButton("Reset Map");
		loadMap = new JButton("Load Map");
		saveMap = new JButton("Save Map");
		
		generateRandomMap.setActionCommand("generateRandomMap");
		resetMap.setActionCommand("resetMap");
		
			//sliders
		
		densityLabel = new JLabel("Density: ");
		densitySlider = new JSlider(0, 100);
		densitySlider.setName("Density");
		densitySlider.setMajorTickSpacing(10);
		densitySlider.setPaintLabels(true);
		
		sizeLabel = new JLabel("Cell size: ");
		sizeSlider = new JSlider(10, 60);
		sizeSlider.setName("Size");
		sizeSlider.setMajorTickSpacing(10);
		sizeSlider.setPaintLabels(true);
		sizeSlider.setSnapToTicks(true);
		
			//start button and tools
		
		startSearchButton = new JButton("Start Search");
		startSearchButton.setActionCommand("Start");
		toolsComboBox = new JComboBox<String>();
		DefaultComboBoxModel<String> toolList = new DefaultComboBoxModel<String>();
		for(String tool: tools)
			toolList.addElement(tool);
		toolsComboBox.setModel(toolList);
		
		//set layout used and add components
		setLayout(new GridLayout(12, 1));
		addComponents();
	}
	
	public void setActionListener(ActionListener listener) {
		generateRandomMap.addActionListener(listener);
		resetMap.addActionListener(listener);
		startSearchButton.addActionListener(listener);
	}
	
	public void setChangeListener(ChangeListener listener) {
		sizeSlider.addChangeListener(listener);
	}
	
	public void addComponents() {
		add(algorithmsLabel);
		add(algorithmsComboBox);
		
		add(generateRandomMap);
		add(resetMap);
		add(loadMap);
		add(saveMap);
		
		add(toolsComboBox);
		
		add(densityLabel);
		add(densitySlider);
		add(sizeLabel);
		add(sizeSlider);
		
		add(startSearchButton);
	}
	
	public String getTool() {
		return (String) (this.toolsComboBox.getSelectedItem());
	}
	
	public double getDensity() {
		return this.densitySlider.getValue()/100.0;
	}
	
	public int getNodeSize() {
		return this.sizeSlider.getValue();
	}
	
	public String getAlgorithm() {
		return (String)(this.algorithmsComboBox.getSelectedItem());
	}	
}
