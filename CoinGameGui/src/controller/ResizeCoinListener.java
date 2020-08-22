package controller;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import view.CoinPanel;

public class ResizeCoinListener extends ComponentAdapter {

	private CoinPanel coinPanel;
	
	public ResizeCoinListener(CoinPanel coinPanel) {
		this.coinPanel = coinPanel;
	}
	
	@Override
	public void componentResized(ComponentEvent e) {
		
		// The x needs to be divided by 2 because each coin uses half of the width of the coin panel
		int coinx = coinPanel.getWidth()/2;
		int coiny = coinPanel.getHeight();

		if (coinx >= coiny) {
			coinPanel.rescaleCoins(coiny);
		} else {
			coinPanel.rescaleCoins(coinx);
		}
		
	}
}
