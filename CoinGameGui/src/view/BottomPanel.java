package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import model.enumeration.BetType;

@SuppressWarnings("serial")
public class BottomPanel extends JPanel{
	
	private JTable table;
	private JScrollPane playerInfoPanel;
	private DefaultTableModel tableModel;
	private StatusBar statusBar;
	private TitledBorder summaryPanelBorder;
	
	public BottomPanel() {
		setLayout(new BorderLayout());
		
		createComponents();
	}
	
	private void createComponents() {
		statusBar = new StatusBar();
		createTable();
		add(statusBar, BorderLayout.SOUTH);
	}
	
	private void createTable() {
		playerInfoPanel = new JScrollPane();
		tableModel = new DefaultTableModel();
		tableModel.addColumn("ID");
		tableModel.addColumn("Name");
		tableModel.addColumn("Points");
		tableModel.addColumn("Bet");
		tableModel.addColumn("Bet Type");
		tableModel.addColumn("Recent Win/Loss");
		
		remove(playerInfoPanel);
		table = new JTable(tableModel);
		table.setDefaultEditor(Object.class, null);
		table.setPreferredScrollableViewportSize(new Dimension(500, 50));
		table.setFillsViewportHeight(true);
		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.getTableHeader().setReorderingAllowed(false);
		
		playerInfoPanel = new JScrollPane(table);
		
		summaryPanelBorder = new TitledBorder("Summary Panel");
		summaryPanelBorder.setTitlePosition(TitledBorder.TOP);
		summaryPanelBorder.setTitleJustification(TitledBorder.LEFT);
		playerInfoPanel.setBorder(summaryPanelBorder);
		
		add(playerInfoPanel, BorderLayout.CENTER);
	}
	
	public void addBet(String ID, int betAmount, BetType betType) {
		int row = getRow(ID);
		int column = tableModel.findColumn("Bet");
		tableModel.setValueAt(betAmount, row, column);
		column = tableModel.findColumn("Bet Type");
		tableModel.setValueAt(betType, row, column);
	}
	
	public void removeBet(String ID) {
		int row = getRow(ID);
		int column = tableModel.findColumn("Bet");
		tableModel.setValueAt(0, row, column);
		column = tableModel.findColumn("Bet Type");
		tableModel.setValueAt(BetType.NO_BET, row, column);
	}
	
	public void addPlayer(String ID, String name, int points) {
		int row = getRow(ID);
		if (row != -1) {
			tableModel.removeRow(row);
		}
		tableModel.addRow(new Object[] {ID, name, points, 0, BetType.NO_BET});
	}
	
	public void removePlayer(String ID) {
		int row = getRow(ID);
		if (row != -1) {
			tableModel.removeRow(row);
		}
	}
	
	// Called to update the results for every player in the summary panel.
	// First calculates win/loss and then sets the value of the player's new points.
	public void updateResults(String ID, int newPoints, int oldPoints) {
		int row = getRow(ID);
		int column = tableModel.findColumn("Points");
		int winLoss = newPoints - oldPoints;
		column = tableModel.findColumn("Recent Win/Loss");
		tableModel.setValueAt(winLoss, row, column);
		column = tableModel.findColumn("Points");
		tableModel.setValueAt(newPoints, row, column);
		column = tableModel.findColumn("Bet");
		tableModel.setValueAt(0, row, column);
		column = tableModel.findColumn("Bet Type");
		tableModel.setValueAt(BetType.NO_BET, row, column);
	}
	
	// Uses the ID of a player to find their respective row
	private int getRow(String ID) {
		for (int row = 0; row < tableModel.getRowCount(); row++) {
			if (tableModel.getValueAt(row, 0).equals(ID)) {
				return row;
			}
		}
		return -1;
	}
	
	public void showSummaryPanel() {
		add(playerInfoPanel, BorderLayout.CENTER);
		playerInfoPanel.setVisible(true);
	}
	
	public void hideSummaryPanel() {
		remove(playerInfoPanel);
		playerInfoPanel.setVisible(false);
	}
	
	public StatusBar getStatusBar() {
		return statusBar;
	}
	
}
