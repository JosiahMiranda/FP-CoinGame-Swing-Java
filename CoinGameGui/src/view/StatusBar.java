package view;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class StatusBar extends JPanel{
	
	private JLabel spinningLabel;
	private JLabel viewingLabel;
	private String viewing;
	
	public StatusBar() {
		createComponents();
		setVisible(true);
	}
	
	private void createComponents() {
		setLayout(new GridLayout(1, 2));
		Border lineBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
		
		spinningLabel = new JLabel("Status: idle");
		viewingLabel = new JLabel("Currently Viewing: N/A");
		
		spinningLabel.setBorder(lineBorder);
		viewingLabel.setBorder(lineBorder);
		
		add(spinningLabel);
		add(viewingLabel);
		
	}
	
	public void setSpinningLabel() {
		spinningLabel.setText("Status: spinning");
	}
	
	public void resetSpinningLabel() {
		spinningLabel.setText("Status: idle");
	}
	
	public void setViewingLabel(String name) {
		this.viewing = name;
		viewingLabel.setText(String.format("Currently Viewing: %s", name));
	}
	
	public void resetViewingLabel() {
		this.viewing = null;
		viewingLabel.setText("Currently Viewing: N/A");
	}
	
	public String getCurrentlyViewing() {
		return viewing;
	}
	
}
