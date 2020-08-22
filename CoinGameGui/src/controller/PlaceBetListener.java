package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.MainFrame;

public class PlaceBetListener implements ActionListener{

	private MainFrame mainFrame;
	
	public PlaceBetListener(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		mainFrame.placeBet();
		mainFrame.updateButtons();
	}

}
