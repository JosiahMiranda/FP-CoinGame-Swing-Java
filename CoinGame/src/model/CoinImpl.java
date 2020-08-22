package model;

import java.util.Objects;
import java.util.Random;

import model.enumeration.CoinFace;
import model.interfaces.Coin;
import model.util.GameUtilities;

public class CoinImpl implements Coin {

	private int number;
	private CoinFace coinFace;

	public CoinImpl(int number) {
		this.coinFace = randomFace();
		this.number = number;
	}

	// Method for creating a coin used in the coinpairs. Assigns a random value between HEADS and TAILS.
	private CoinFace randomFace() {
		Random generator = new Random();
		return CoinFace.values()[generator.nextInt(CoinFace.values().length)];
	}

	@Override
	public int getNumber() {
		return this.number;
	}

	@Override
	public CoinFace getFace() {
		return this.coinFace;
	}

	@Override
	public void flip() {
		if (this.coinFace.equals(CoinFace.HEADS)) {
			this.coinFace = CoinFace.TAILS;
		} else {
			this.coinFace = CoinFace.HEADS;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getFace());
	}

	@Override
	public boolean equals(Coin coin) {
		return number == coin.getNumber() && coinFace.equals(coin.getFace());
	}
	
	@Override
	public boolean equals(Object coin) {
		if (coin instanceof Coin && this.equals(coin)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return String.format("Coin %d: %s", number, GameUtilities.caseString(coinFace));
	}

}
