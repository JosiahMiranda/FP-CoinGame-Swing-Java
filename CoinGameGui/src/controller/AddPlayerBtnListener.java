package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.SimplePlayer;
import view.MainFrame;
import view.PlayerPanel;

public class AddPlayerBtnListener implements ActionListener{

	private MainFrame mainFrame;
	private PlayerPanel playerPanel;
	
	public AddPlayerBtnListener(PlayerPanel playerPanel, MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.playerPanel = playerPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		try {
			String ID = playerPanel.getIDField();
			String name = playerPanel.getNameField();
			
			int initialPoints = playerPanel.getInitialPointsField();
			
			if (ID.trim().equals("") || name.trim().equals("")) {
				JOptionPane.showMessageDialog(mainFrame, "Fields cannot be empty.");
				return;
			} else if (initialPoints <= 0) {
				JOptionPane.showMessageDialog(mainFrame, "Initial Points cannot be lower than or equal to 0.");
				return;
			}
			
			SimplePlayer player = new SimplePlayer(ID, name, initialPoints);
			mainFrame.addPlayer(player);
		} catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(mainFrame, "Initial Points must be an Integer!");
		}
		mainFrame.updateButtons();
	}

}
