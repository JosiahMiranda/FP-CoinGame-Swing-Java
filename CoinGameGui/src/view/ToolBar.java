package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import controller.AddPlayerPanelListener;
import controller.PlaceBetListener;
import controller.RemoveBetListener;
import controller.RemovePlayerListener;
import controller.SelectedPlayerListener;
import controller.SpinPlayerListener;
import controller.SpinSpinnerListener;
import model.enumeration.BetType;
import model.interfaces.Player;

@SuppressWarnings("serial")
public class ToolBar extends JPanel {

	private MainFrame mainFrame;
	private JButton spinPlayerBtn;
	private JButton spinSpinnerBtn;
	private JButton addPlayersBtn;
	private JButton removePlayersBtn;
	private JButton placeBetBtn;
	private JButton removeBetBtn;
	private JComboBox<Player> playerListBox;
	private JComboBox<BetType> betTypeBox;
	private GridBagConstraints c;

	public ToolBar(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		createComponents();
		setListeners();
	}

	private void createComponents() {
		setLayout(new GridBagLayout());
		addPlayersBtn = new JButton("Add Players");
		removePlayersBtn = new JButton("Remove Player");
		playerListBox = new JComboBox<Player>();
		betTypeBox = createBetComboBox();
		placeBetBtn = new JButton("Place Bet");
		removeBetBtn = new JButton("Remove Bet");
		spinPlayerBtn = new JButton("Spin Player");
		spinSpinnerBtn = new JButton("Spin Spinner");

		removePlayersBtn.setEnabled(false);
		placeBetBtn.setEnabled(false);
		removeBetBtn.setEnabled(false);
		spinPlayerBtn.setEnabled(false);
		spinSpinnerBtn.setEnabled(false);

		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		c.weightx = 0.5;
		add(addPlayersBtn, c);
		add(removePlayersBtn, c);
		add(playerListBox, c);
		add(betTypeBox, c);
		add(placeBetBtn, c);
		add(removeBetBtn, c);
		add(spinPlayerBtn, c);
		add(spinSpinnerBtn, c);

		setVisible(true);
	}

	// Bet types gets included into the comboBox for the player to choose
	private JComboBox<BetType> createBetComboBox() {
		DefaultComboBoxModel<BetType> model = new DefaultComboBoxModel<BetType>();
		model.addElement(BetType.COIN1);
		model.addElement(BetType.COIN2);
		model.addElement(BetType.BOTH);
		JComboBox<BetType> box = new JComboBox<BetType>();
		box.setModel(model);
		return box;
	}

	private void setListeners() {
		AddPlayerPanelListener addPlayerPanelListener = new AddPlayerPanelListener(mainFrame);
		SelectedPlayerListener selectedPlayerListener = new SelectedPlayerListener(mainFrame);
		PlaceBetListener placeBetListener = new PlaceBetListener(mainFrame);
		RemoveBetListener removeBetListener = new RemoveBetListener(mainFrame);
		SpinPlayerListener spinPlayerListener = new SpinPlayerListener(mainFrame);
		SpinSpinnerListener spinSpinnerListener = new SpinSpinnerListener(mainFrame);
		RemovePlayerListener removePlayerListener = new RemovePlayerListener(mainFrame);
		
		addPlayersBtn.addActionListener(addPlayerPanelListener);
		playerListBox.addActionListener(selectedPlayerListener);
		placeBetBtn.addActionListener(placeBetListener);
		removeBetBtn.addActionListener(removeBetListener);
		spinPlayerBtn.addActionListener(spinPlayerListener);
		spinSpinnerBtn.addActionListener(spinSpinnerListener);
		removePlayersBtn.addActionListener(removePlayerListener);
	}

	public JComboBox<Player> getPlayerComboBox() {
		return playerListBox;
	}

	public BetType getSelectedBetType() {
		return (BetType) betTypeBox.getSelectedItem();
	}
	
	public void enablePlaceBetBtn(boolean bool) {
		placeBetBtn.setEnabled(bool);
	}

	public void enableRemoveBetBtn(boolean bool) {
		removeBetBtn.setEnabled(bool);
	}
	
	public void enableSpinPlayerBtn(boolean bool) {
		spinPlayerBtn.setEnabled(bool);
	}
	
	public void enableSpinSpinnerBtn(boolean bool) {
		spinSpinnerBtn.setEnabled(bool);
	}
	
	public void enableRemovePlayerButton(boolean bool) {
		removePlayersBtn.setEnabled(bool);
	}
}
