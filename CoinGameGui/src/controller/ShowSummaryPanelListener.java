package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.MainFrame;

public class ShowSummaryPanelListener implements ActionListener{

	private MainFrame mainFrame;
	
	public ShowSummaryPanelListener(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		mainFrame.enableSummaryPanel(true);
	}

}
