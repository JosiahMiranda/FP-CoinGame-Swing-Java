package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.MainFrame;

public class SelectedPlayerListener implements ActionListener{

	private MainFrame mainFrame;
	
	public SelectedPlayerListener(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		mainFrame.openPanel("CoinPanel");
		mainFrame.updateCurrentlyViewing(true);
		mainFrame.updateButtons();
	}

}
