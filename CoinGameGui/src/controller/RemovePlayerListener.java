package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.MainFrame;

public class RemovePlayerListener implements ActionListener{

	private MainFrame mainFrame;
	
	public RemovePlayerListener(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		mainFrame.removeSelectedPlayer();
		mainFrame.updateCurrentlyViewing(false);
		mainFrame.updateButtons();
	}

}
