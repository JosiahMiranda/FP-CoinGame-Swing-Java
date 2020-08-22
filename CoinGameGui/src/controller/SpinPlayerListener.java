package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.MainFrame;

public class SpinPlayerListener implements ActionListener{

	private MainFrame mainFrame;
	
	public SpinPlayerListener(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		new Thread() {
			@Override
			public void run() {
				mainFrame.spinPlayer();
			}
		}.start();
		mainFrame.updateButtons();
	}

}
