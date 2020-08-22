package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.ResizeCoinListener;
import model.enumeration.CoinFace;

@SuppressWarnings("serial")
public class CoinPanel extends JPanel {

	private JLabel coin1;
	private JLabel coin2;
	private ImageIcon headsIcon;
	private ImageIcon tailsIcon;
	private GridBagConstraints c;
	private CoinFace coinFace1;
	private CoinFace coinFace2;
	private ResizeCoinListener resizeListener;

	public CoinPanel() {
		setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		createComponents();
		addListeners();
		setVisible(true);
		
	}
	
	private void createComponents() {
		headsIcon = new ImageIcon("heads.png");
		tailsIcon = new ImageIcon("tails.png");

		coin1 = new JLabel();
		coin2 = new JLabel();
		
		coinFace1 = CoinFace.HEADS;
		coinFace2 = CoinFace.TAILS;
		
		setCoinIcon(coinFace1, coin1);
		setCoinIcon(coinFace2, coin2);
		
		coin1.setHorizontalAlignment(SwingConstants.CENTER);
		coin2.setHorizontalAlignment(SwingConstants.CENTER);

		add(coin1, c);
		add(coin2, c);
	}
	
	private void addListeners() {
		resizeListener = new ResizeCoinListener(this);
		addComponentListener(resizeListener);
	}
	
	// This method is used to for constant re-scaling of the coin images whenever the window size is changed.
	public void rescaleCoins(int coin) {
		
		if (coin == 0) {
			return;
		}
		Image newHeadsIcon = new ImageIcon("heads.png").getImage().getScaledInstance(coin, coin, Image.SCALE_SMOOTH);
		Image newTailsIcon = new ImageIcon("tails.png").getImage().getScaledInstance(coin, coin, Image.SCALE_SMOOTH);
		
		headsIcon = new ImageIcon(newHeadsIcon);
		tailsIcon = new ImageIcon(newTailsIcon);
		
		refreshCoins();
		
	}
	
	public void setCoinIcon(CoinFace coinFace, JLabel coin) {
		if (coinFace.equals(CoinFace.HEADS)) {
			coin.setIcon(headsIcon);
		} else {
			coin.setIcon(tailsIcon);
		}
		
		if (coin.equals(coin1)) {
			coinFace1 = coinFace;
		} else {
			coinFace2 = coinFace;
		}
	}
	
	public JLabel getCoin1() {
		return coin1;
	}

	public JLabel getCoin2() {
		return coin2;
	}
	
	// Every time a coinPanel is opened, refresh the coins to fit the selected player
	public void refreshCoins() {
		setCoinIcon(coinFace1, coin1);
		setCoinIcon(coinFace2, coin2);
	}
}
