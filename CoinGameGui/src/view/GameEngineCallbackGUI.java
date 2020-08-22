package view;

import javax.swing.SwingUtilities;

import model.interfaces.Coin;
import model.interfaces.CoinPair;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.interfaces.GameEngineCallback;

public class GameEngineCallbackGUI implements GameEngineCallback{

	private MainFrame mainFrame;
	private CoinPanel coinPanel;
	
	public GameEngineCallbackGUI(MainFrame mainFrame, CoinPanel coinPanel) {
		this.mainFrame = mainFrame;
		this.coinPanel = coinPanel;
	}
	
	private void guiSpin(Coin coin) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				if (coin.getNumber() == 1) {
					coinPanel.setCoinIcon(coin.getFace(), coinPanel.getCoin1());
				} else {
					coinPanel.setCoinIcon(coin.getFace(), coinPanel.getCoin2());
				}
			}
		});
	}
	
	@Override
	public void playerCoinUpdate(Player player, Coin coin, GameEngine engine) {
		guiSpin(coin);
	}

	@Override
	public void spinnerCoinUpdate(Coin coin, GameEngine engine) {
		guiSpin(coin);
	}

	@Override
	public void playerResult(Player player, CoinPair coinPair, GameEngine engine) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				mainFrame.updateButtons();
			}
			
		});
		
	}

	@Override
	public void spinnerResult(CoinPair coinPair, GameEngine engine) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				mainFrame.updateSummaryWinLoss();
				mainFrame.removeLostPlayers();
				mainFrame.updateButtons();
			}
			
		});
		
	}

}
