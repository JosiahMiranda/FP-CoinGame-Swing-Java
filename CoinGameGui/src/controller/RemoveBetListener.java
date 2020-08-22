package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.MainFrame;

public class RemoveBetListener implements ActionListener{

	private MainFrame mainFrame;
	
	public RemoveBetListener(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		mainFrame.removeBet();
		mainFrame.updateButtons();
	}

}
