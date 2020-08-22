package model;

import java.util.Objects;

import model.interfaces.Coin;
import model.interfaces.CoinPair;

public class CoinPairImpl implements CoinPair {

	private Coin coin1;
	private Coin coin2;
	
	public CoinPairImpl() {
		this.coin1 = new CoinImpl(1);
		this.coin2 = new CoinImpl(2);
	}

	@Override
	public Coin getCoin1() {
		return this.coin1;
	}

	@Override
	public Coin getCoin2() {
		return this.coin2;
	}

	@Override
	public int hashCode() {
		return Objects.hash(coin1.getFace(), coin2.getFace());
	}

	@Override
	public boolean equals(CoinPair coinPair) {
		if (coin1.equals(coinPair.getCoin1()) && coin2.equals(coinPair.getCoin2())) {
			return true;
		} else {
			return false;
		}
		
	}
	
	@Override
	public boolean equals(Object coinPair) {
		if (coinPair instanceof CoinPair && this.equals(coinPair)) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return String.format("%s, %s", coin1.toString(), coin2.toString());
	}

}
