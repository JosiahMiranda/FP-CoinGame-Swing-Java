package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.MainFrame;

public class SpinSpinnerListener implements ActionListener{

	private MainFrame mainFrame;
	
	public SpinSpinnerListener(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		new Thread() {
			@Override
			public void run() {
				mainFrame.spinSpinner();
			}
		}.start();
		mainFrame.updateButtons();
	}

}
