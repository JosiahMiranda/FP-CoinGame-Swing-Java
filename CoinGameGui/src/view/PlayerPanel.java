package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.AddPlayerBtnListener;

@SuppressWarnings("serial")
public class PlayerPanel extends JPanel{

	private MainFrame mainFrame;
	private JButton addPlayerBtn;
	private JLabel idLabel;
	private JLabel nameLabel;
	private JLabel initialPointsLabel;
	private JTextField idTextField;
	private JTextField nameTextField;
	private JTextField initialPointsTextField;
	private GridBagConstraints c;
	
	public PlayerPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		createComponents();
		setListeners();
	}
	
	private void createComponents() {
		setLayout(new GridBagLayout());
		
		idLabel = new JLabel("Enter ID:");
		nameLabel = new JLabel("Enter Name:");
		initialPointsLabel = new JLabel("Enter Points:");
		idTextField = new JTextField();
		nameTextField = new JTextField();
		initialPointsTextField = new JTextField();
		addPlayerBtn = new JButton("Add Player");
		
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		add(idLabel, c);
		
		c.gridx = 1;
		add(nameLabel, c);
		
		c.gridx = 2;
		add(initialPointsLabel, c);
		
		c.gridx = 0;
		c.gridy = 1;
		add(idTextField, c);
		
		c.gridx = 1;
		add(nameTextField, c);
		
		c.gridx = 2;
		add(initialPointsTextField, c);
		
		c.gridx = 1;
		c.gridy = 2;
		add(addPlayerBtn, c);
	}
	
	private void setListeners() {
		AddPlayerBtnListener addPlayerBtnListener = new AddPlayerBtnListener(this, mainFrame);
		
		addPlayerBtn.addActionListener(addPlayerBtnListener);
	}
	
	public String getIDField() {
		return idTextField.getText();
	}
	
	public String getNameField() {
		return nameTextField.getText();
	}
	
	public int getInitialPointsField() throws NumberFormatException{
		return Integer.parseInt(initialPointsTextField.getText());
	}
	
}
