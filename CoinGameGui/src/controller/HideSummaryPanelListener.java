package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.MainFrame;

public class HideSummaryPanelListener implements ActionListener{

	private MainFrame mainFrame;
	
	public HideSummaryPanelListener(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		mainFrame.enableSummaryPanel(false);
	}

}
