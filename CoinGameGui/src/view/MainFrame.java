package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import cellrenderer.PlayerCellRenderer;
import model.CoinPairImpl;
import model.enumeration.BetType;
import model.interfaces.CoinPair;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.model.PlayerState;
import view.model.ViewModel;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private GameEngine gameEngine;
	private GameEngineCallbackGUI gecGUI;
	private ToolBar toolBar;
	private Menu menu;
	private CoinPanel coinPanel;
	private CoinPanel lastSpunCoinPanel;
	private PlayerPanel playerPanel;
	private BottomPanel bottomPanel;
	private StatusBar statusBar;
	private ViewModel viewModel;
	private DefaultComboBoxModel<Player> model;
	private PlayerCellRenderer renderer;

	public MainFrame(GameEngine gameEngine) {
		super("Coin Game GUI");
		setSize(900,600);
		this.gameEngine = gameEngine;
	}
	
	public void run() {
		viewModel = new ViewModel();

		setMinimumSize(new Dimension(600, 400));
		createComponents();
		
		gecGUI = new GameEngineCallbackGUI(this, coinPanel);
		gameEngine.addGameEngineCallback(gecGUI);
		setVisible(true);
	}

	private void createComponents() {
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		menu = new Menu(this);
		setJMenuBar(menu);

		toolBar = new ToolBar(this);
		coinPanel = new CoinPanel();
		lastSpunCoinPanel = new CoinPanel();
		bottomPanel = new BottomPanel();
		statusBar = bottomPanel.getStatusBar();
		playerPanel = new PlayerPanel(this);

		add(toolBar, BorderLayout.NORTH);
		add(playerPanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);

	}

	public void placeBet() {
		try {
			String betAmountStr = JOptionPane.showInputDialog("Please enter a Bet Amount");
			if (betAmountStr == null) {
				return;
			}

			int betAmount = Integer.parseInt(betAmountStr);

			if (betAmount <= 0) {
				JOptionPane.showMessageDialog(this, "Bet Amount cannot be lower than or equal to 0.");
				return;
			}

			BetType betType = toolBar.getSelectedBetType();
			Player player = (Player) toolBar.getPlayerComboBox().getSelectedItem();

			if (betAmount > player.getPoints()) {
				JOptionPane.showMessageDialog(this,
						String.format("%s does not have enough points for this bet.", player.getPlayerName()));
				return;
			}

			gameEngine.placeBet(player, betAmount, betType);
			bottomPanel.addBet(player.getPlayerId(), betAmount, betType);
			viewModel.setCanSpin(player, true);
			viewModel.setNeedsSpin(player, true);
			viewModel.setPreviousPoints(player, player.getPoints());
			String message = String.format("Bet of Type %s and amount %d has been placed for %s.", player.getBetType(),
					player.getBet(), player.getPlayerName());
			JOptionPane.showMessageDialog(this, message);

		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Bet Amount must be an Integer!");
		}

	}

	public void removeBet() {
		Player player = (Player) toolBar.getPlayerComboBox().getSelectedItem();
		gameEngine.placeBet(player, 0, BetType.NO_BET);
		bottomPanel.removeBet(player.getPlayerId());
		viewModel.setCanSpin(player, false);
		viewModel.setNeedsSpin(player, false);
		viewModel.setPlayerSpun(player, false);
		viewModel.setCanRemove(player, true);
		if (viewModel.everyOtherPlayerSpun(player)) {
			new Thread() {
				@Override
				public void run() {
					spinSpinner();
				}
			}.start();
		}
	}

	// Depending on the state of the program, it will display the spinning coin panel or the coin panel with
	// the selected player's last results.
	public void openPanel(String string) {
		clearPanels();
		if (string.equals("CoinPanel")) {
			try {
				Player player = (Player) toolBar.getPlayerComboBox().getSelectedItem();
				if (!player.equals(viewModel.getPlayerSpinning()) && viewModel.isGameSpinning()) {
					lastSpunCoinPanel.setCoinIcon(player.getResult().getCoin1().getFace(),
							lastSpunCoinPanel.getCoin1());
					lastSpunCoinPanel.setCoinIcon(player.getResult().getCoin2().getFace(),
							lastSpunCoinPanel.getCoin2());
					add(lastSpunCoinPanel, BorderLayout.CENTER);
					lastSpunCoinPanel.setVisible(true);
					return;
				} else if (player.equals(viewModel.getPlayerSpinning()) && viewModel.isGameSpinning()){
					add(coinPanel, BorderLayout.CENTER);
					coinPanel.setVisible(true);
					coinPanel.refreshCoins();
					return;
				}
				coinPanel.setCoinIcon(player.getResult().getCoin1().getFace(), coinPanel.getCoin1());
				coinPanel.setCoinIcon(player.getResult().getCoin2().getFace(), coinPanel.getCoin2());
				add(coinPanel, BorderLayout.CENTER);
				coinPanel.setVisible(true);
			} catch (NullPointerException e) {

			}
		} else if (string.equals("PlayerPanel")) {
			add(playerPanel, BorderLayout.CENTER);
			playerPanel.setVisible(true);
		}
	}

	private void clearPanels() {
		remove(coinPanel);
		remove(playerPanel);
		remove(lastSpunCoinPanel);
		coinPanel.setVisible(false);
		playerPanel.setVisible(false);
		lastSpunCoinPanel.setVisible(false);
	}

	public void addPlayer(Player player) {
		// Setting Players default values so there are no null issues.
		CoinPair coinPair = new CoinPairImpl();
		player.setResult(coinPair);
		gameEngine.addPlayer(player);
		viewModel.addPlayerState(player);
		viewModel.setPreviousPoints(player, player.getPoints());
		updatePlayerComboBox();
		bottomPanel.addPlayer(player.getPlayerId(), player.getPlayerName(), player.getPoints());
	}

	public void removeSelectedPlayer() {
		try {
			Player player = (Player) toolBar.getPlayerComboBox().getSelectedItem();
			viewModel.removePlayerState(player);
			gameEngine.removePlayer(player);
			bottomPanel.removePlayer(player.getPlayerId());
			updatePlayerComboBox();
			openPanel("PlayerPanel");
			if (viewModel.everyOtherPlayerSpun(null)) {
				new Thread() {
					@Override
					public void run() {
						spinSpinner();
					}
				}.start();
			}
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(this, "No Player Selected in the Tool Bar.");
		}

	}

	public void spinPlayer() {
		openPanel("CoinPanel");
		Player player = (Player) toolBar.getPlayerComboBox().getSelectedItem();
		updateCurrentlyViewing(true);
		viewModel.setCanSpin(player, false);
		viewModel.setNeedsSpin(player, false);
		viewModel.setCanRemove(player, false);
		statusBar.setSpinningLabel();
		viewModel.setPlayerSpinning(player);
		viewModel.setPlayerSpun(player, true);
		updateButtons();
		gameEngine.spinPlayer(player, 100, 1000, 100, 50, 500, 50);
		viewModel.resetPlayerSpinning();
		statusBar.resetSpinningLabel();
		if (viewModel.everyOtherPlayerSpun(player)) {
			revalidate();
			spinSpinner();
		}
		updateButtons();
	}

	public void spinSpinner() {
		if (!viewModel.canSpinnerSpin()) {
			return;
		}

		openPanel("CoinPanel");
		updateCurrentlyViewing(true);
		statusBar.setSpinningLabel();
		viewModel.setSpinnerSpinning(true);
		updateButtons();
		statusBar.setViewingLabel("Spinner");
		gameEngine.spinSpinner(100, 1000, 100, 50, 500, 50);
		viewModel.setSpinnerSpinning(false);
		statusBar.resetSpinningLabel();
		resetPlayers();
	}

	private void resetPlayers() {
		viewModel.resetSpins();
		for (Player player : gameEngine.getAllPlayers()) {
			player.resetBet();
		}
	}

	// Every time a player is added or removed the combo box in the toolbar will be
	// updated.
	private void updatePlayerComboBox() {
		model = new DefaultComboBoxModel<Player>();
		if (gameEngine.getAllPlayers() != null) {
			for (Player player : gameEngine.getAllPlayers()) {
				model.addElement(player);
			}
		}
		toolBar.getPlayerComboBox().setModel(model);
		renderer = new PlayerCellRenderer();
		toolBar.getPlayerComboBox().setRenderer(renderer);
		toolBar.getPlayerComboBox().setMinimumSize(new Dimension(100, 50));
	}

	public void removeLostPlayers() {
		for (Player player : gameEngine.getAllPlayers()) {
			if (player.getPoints() <= 0) {
				gameEngine.removePlayer(player);
				bottomPanel.removePlayer(player.getPlayerId());
			}
		}
		updatePlayerComboBox();
		updateButtons();
	}

	public void updateSummaryWinLoss() {
		for (Player player : gameEngine.getAllPlayers()) {
			PlayerState playerState = viewModel.getPlayerState(player);
			bottomPanel.updateResults(player.getPlayerId(), player.getPoints(), playerState.getPreviousPoints());
		}
	}

	public void updateCurrentlyViewing(boolean isOnCoinPanel) {
		if (!isOnCoinPanel) {
			statusBar.setViewingLabel("N/A");
			return;
		}
		try {
			Player player = (Player) toolBar.getPlayerComboBox().getSelectedItem();
			statusBar.setViewingLabel(player.getPlayerName());
		} catch (NullPointerException e) {
			statusBar.resetViewingLabel();
		}
	}
	
	// Will display/hide the summary panel depending on the boolean passed in
	public void enableSummaryPanel(boolean show) {
		if (show) {
			bottomPanel.showSummaryPanel();
		} else {
			bottomPanel.hideSummaryPanel();
		}
		revalidate();
	}

	// Called after every user interaction so that it updates frequently.
	// Will update all of the buttons so that you can only use them if certain
	// conditions are met.
	public void updateButtons() {
		try {
			Player player = (Player) toolBar.getPlayerComboBox().getSelectedItem();

			if (player.getBetType() != BetType.NO_BET) {
				toolBar.enablePlaceBetBtn(false);
				if (viewModel.isGameSpinning()) {
					toolBar.enableRemoveBetBtn(false);
					toolBar.enablePlaceBetBtn(false);
				} else {
					toolBar.enableRemoveBetBtn(true);
				}
			} else {
				toolBar.enableRemoveBetBtn(false);
				toolBar.enablePlaceBetBtn(true);
			}
			
			if (viewModel.isSpinnerSpinning()) {
				toolBar.enablePlaceBetBtn(false);
			}

			if (viewModel.getCanSpin(player) && !viewModel.isGameSpinning()) {
				toolBar.enableSpinPlayerBtn(true);
				menu.enableSpinPlayerOption(true);
			} else {
				toolBar.enableSpinPlayerBtn(false);
				menu.enableSpinPlayerOption(false);
			}

			if (viewModel.canSpinnerSpin() && !viewModel.isGameSpinning()) {
				toolBar.enableSpinSpinnerBtn(true);
				menu.enableSpinSpinnerOption(true);
			} else {
				toolBar.enableSpinSpinnerBtn(false);
				menu.enableSpinSpinnerOption(false);
			}

			if (viewModel.getCanRemove(player)) {
				toolBar.enableRemovePlayerButton(true);
				menu.enableRemovePlayerOption(true);
			} else {
				toolBar.enableRemovePlayerButton(false);
				menu.enableRemovePlayerOption(false);
			}

		} catch (NullPointerException e) {
			toolBar.enablePlaceBetBtn(false);
			toolBar.enableRemoveBetBtn(false);
			toolBar.enableSpinPlayerBtn(false);
			toolBar.enableRemovePlayerButton(false);
			
			menu.enableSpinPlayerOption(false);
			menu.enableSpinSpinnerOption(false);
			menu.enableRemovePlayerOption(false);
		}

	}
}
