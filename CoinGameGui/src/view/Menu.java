package view;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import controller.AddPlayerPanelListener;
import controller.HideSummaryPanelListener;
import controller.RemovePlayerListener;
import controller.ShowSummaryPanelListener;
import controller.SpinPlayerListener;
import controller.SpinSpinnerListener;

@SuppressWarnings("serial")
public class Menu extends JMenuBar{
	
	private MainFrame mainFrame;
	
	private JMenu file;
	private JMenu window;
	private JMenuItem addPlayerOption;
	private JMenuItem removeSelectedPlayerOption;
	private JMenuItem spinPlayerOption;
	private JMenuItem spinSpinnerOption;
	private JMenuItem showSummaryPanel;
	private JMenuItem hideSummaryPanel;
	
	public Menu(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		createComponents();
		setListeners();
		setVisible(true);
	}
	
	private void createComponents() {
		file = new JMenu("Options");
		window = new JMenu("Window");
		add(file);
		add(window);
		
		addPlayerOption = new JMenuItem("Add Player");
		removeSelectedPlayerOption = new JMenuItem("Remove Selected Player");
		spinPlayerOption = new JMenuItem("Spin Selected Player");
		spinSpinnerOption = new JMenuItem("Spin Spinner");
		
		showSummaryPanel = new JMenuItem("Show Summary Pannel");
		hideSummaryPanel = new JMenuItem("Hide Summary Pannel");
		
		
		file.add(addPlayerOption);
		file.add(removeSelectedPlayerOption);
		file.add(spinPlayerOption);
		file.add(spinSpinnerOption);
		
		window.add(showSummaryPanel);
		window.add(hideSummaryPanel);
	}
	
	private void setListeners() {
		AddPlayerPanelListener addPlayerOptionListener = new AddPlayerPanelListener(mainFrame);
		RemovePlayerListener removePlayerListener = new RemovePlayerListener(mainFrame);
		SpinPlayerListener spinPlayerListener = new SpinPlayerListener(mainFrame);
		SpinSpinnerListener spinSpinnerListener = new SpinSpinnerListener(mainFrame);
		ShowSummaryPanelListener showSummaryPanelListener = new ShowSummaryPanelListener(mainFrame);
		HideSummaryPanelListener hideSummaryPanelListener = new HideSummaryPanelListener(mainFrame);
		
		addPlayerOption.addActionListener(addPlayerOptionListener);
		removeSelectedPlayerOption.addActionListener(removePlayerListener);
		
		spinPlayerOption.addActionListener(spinPlayerListener);
		spinSpinnerOption.addActionListener(spinSpinnerListener);
		
		showSummaryPanel.addActionListener(showSummaryPanelListener);
		hideSummaryPanel.addActionListener(hideSummaryPanelListener);
		
	}
	
	public void enableAddPlayerOption(boolean bool) {
		addPlayerOption.setEnabled(bool);
	}
	
	public void enableRemovePlayerOption(boolean bool) {
		removeSelectedPlayerOption.setEnabled(bool);;
	}
	
	public void enableSpinPlayerOption(boolean bool) {
		spinPlayerOption.setEnabled(bool);
	}
	
	public void enableSpinSpinnerOption(boolean bool) {
		spinSpinnerOption.setEnabled(bool);
	}
}
