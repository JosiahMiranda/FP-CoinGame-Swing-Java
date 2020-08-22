package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.MainFrame;

public class AddPlayerPanelListener implements ActionListener{

	private MainFrame mainFrame;
	
	public AddPlayerPanelListener(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		mainFrame.openPanel("PlayerPanel");
		mainFrame.updateCurrentlyViewing(false);
		mainFrame.updateButtons();
	}

}
