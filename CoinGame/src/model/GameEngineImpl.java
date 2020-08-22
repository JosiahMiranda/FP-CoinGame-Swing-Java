package model;

import java.util.ArrayList;
import java.util.Collection;

import model.enumeration.BetType;
import model.interfaces.Coin;
import model.interfaces.CoinPair;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.interfaces.GameEngineCallback;

public class GameEngineImpl implements GameEngine {

	private Collection<Player> players = new ArrayList<Player>();
	private Collection<GameEngineCallback> gameEngineCallbacks = new ArrayList<GameEngineCallback>();

	// Make a separate method in the class for code reuse. Don't use instanceof or
	// upcasting

	@Override
	public void spinPlayer(Player player, int initialDelay1, int finalDelay1, int delayIncrement1, int initialDelay2,
			int finalDelay2, int delayIncrement2) throws IllegalArgumentException {
		if (isValid(initialDelay1, finalDelay1, delayIncrement1, initialDelay2, finalDelay2, delayIncrement2)) {
			spinCoinPair(player, initialDelay1, finalDelay1, delayIncrement1, initialDelay2, finalDelay2, delayIncrement2);
		} else {
			throw new IllegalArgumentException();
		}

	}

	@Override
	public void spinSpinner(int initialDelay1, int finalDelay1, int delayIncrement1, int initialDelay2, int finalDelay2,
			int delayIncrement2) throws IllegalArgumentException {
		if (isValid(initialDelay1, finalDelay1, delayIncrement1, initialDelay2, finalDelay2, delayIncrement2)) {
			spinCoinPair(null, initialDelay1, finalDelay1, delayIncrement1, initialDelay2, finalDelay2, delayIncrement2); // Passes in null since it's not a player
		} else {
			throw new IllegalArgumentException();
		}
	}

	// Checking that the arguments entered are valid or if an exception must be
	// thrown
	private boolean isValid(int initialDelay1, int finalDelay1, int delayIncrement1, int initialDelay2, int finalDelay2,
			int delayIncrement2) {
		if (initialDelay1 < 0 || finalDelay1 < 0 || delayIncrement1 < 0 || initialDelay2 < 0 || finalDelay2 < 0
				|| delayIncrement1 < 0) {
			return false;
		}
		if (finalDelay1 < initialDelay1 || finalDelay2 < initialDelay2) {
			return false;
		}
		if (delayIncrement1 > (finalDelay1 - initialDelay1) || delayIncrement2 > (finalDelay2 - initialDelay2)) {
			return false;
		}
		return true;
	}

	// Separate method for code reuse. So I don't need to have multiple for loops in
	// separate methods.
	private void spinCoinPair(Player player, int initialDelay1, int finalDelay1, int delayIncrement1, int initialDelay2, int finalDelay2, int delayIncrement2) {
		CoinPair coinPair = new CoinPairImpl();
		boolean isPlayer = true;
		if (player == null) {
			isPlayer = false;
		}
		
		Thread coin1 = new Thread(new Runnable( ) {
			@Override
			public void run() {
				spinCoin(coinPair.getCoin1(), player, initialDelay1, finalDelay1, delayIncrement1);
			}
		});
		
		Thread coin2 = new Thread(new Runnable( ) {
			@Override
			public void run() {
				spinCoin(coinPair.getCoin2(), player, initialDelay2, finalDelay2, delayIncrement2);
			}
		});
		
		coin1.start();
		coin2.start();
		
		// Join the two threads so that we will wait for them to finish before doing the rest of the code
		try {
			coin1.join();
			coin2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (isPlayer) {
			player.setResult(coinPair);
		} else {
			applyBetResults(coinPair);
		}

		// logs the player and spinner results
		for (GameEngineCallback gameEC : gameEngineCallbacks) {
			if (isPlayer) {
				gameEC.playerResult(player, coinPair, this);
			} else {
				gameEC.spinnerResult(coinPair, this);
			}

		}
	}

	// Spins individual coins so that we can multithread at different delays
	private void spinCoin(Coin coin, Player player, int initialDelay, int finalDelay, int delayIncrement) {
		boolean isPlayer = true;
		if (player == null) {
			isPlayer = false;
		}
		while (initialDelay < finalDelay) {
			coin.flip();
			for (GameEngineCallback gameEC : gameEngineCallbacks) {
				if (isPlayer) {
					gameEC.playerCoinUpdate(player, coin, this);
				} else {
					gameEC.spinnerCoinUpdate(coin, this);
				}
			}
			try {
				Thread.sleep(initialDelay); // To do the delay
			} catch (InterruptedException e) {
				System.out.println(e.toString());
			}
			initialDelay += delayIncrement;
		}
	}

	@Override
	public void applyBetResults(CoinPair spinnerResult) {
		for (Player player : players) {
			player.getBetType().applyWinLoss(player, spinnerResult);
		}
	}

	@Override
	public void addPlayer(Player player) {
		Player toRemove = getPlayer(player.getPlayerId());
		if (toRemove != null) {
			removePlayer(toRemove); // First we remove any player that has the same Unique ID as to replace it.
		}
		players.add(player);
	}

	@Override
	public Player getPlayer(String id) {
		for (Player p : players) {
			if (p.getPlayerId().equals(id)) {
				return p;
			}
		}
		return null;
	}

	@Override
	public boolean removePlayer(Player player) {
		return players.remove(player);
	}

	@Override
	public void addGameEngineCallback(GameEngineCallback gameEngineCallback) {
		gameEngineCallbacks.add(gameEngineCallback);
	}

	@Override
	public boolean removeGameEngineCallback(GameEngineCallback gameEngineCallback) {
		gameEngineCallbacks.remove(gameEngineCallback);
		return true;
	}

	@Override
	public Collection<Player> getAllPlayers() {
		return new ArrayList<Player>(players); // Returning a new array list with the same contents as 'players'
	}

	@Override
	public boolean placeBet(Player player, int bet, BetType betType) {
		if (player.setBet(bet)) {
			player.setBetType(betType);
			return true;
		} else {
			player.resetBet();
			return false;
		}
	}

}
