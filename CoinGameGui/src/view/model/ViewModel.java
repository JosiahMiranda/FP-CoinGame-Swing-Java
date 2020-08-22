package view.model;

import java.util.ArrayList;
import java.util.Collection;

import model.interfaces.Player;

public class ViewModel {
	
	private Collection<PlayerState> playerStates;
	private boolean gameIsSpinning;
	private Player playerSpinning;
	private boolean spinnerIsSpinning;
	
	public ViewModel() {
		gameIsSpinning = false;
		spinnerIsSpinning = false;
		playerStates = new ArrayList<PlayerState>();
	}
	
	public int getPreviousPoints(Player player) {
		PlayerState playerState = getPlayerState(player);
		return playerState.getPreviousPoints();
	}
	
	public void setPreviousPoints(Player player, int points) {
		PlayerState playerState = getPlayerState(player);
		playerState.setPreviousPoints(points);
	}
	
	public void addPlayerState(Player player) {
		PlayerState playerState = new PlayerState(player);
		playerStates.add(playerState);
	}
	
	public void removePlayerState(Player player) {
		PlayerState playerState = getPlayerState(player);
		playerStates.remove(playerState);
	}
	
	public void resetSpins() {
		for (PlayerState playerState : playerStates) {
			playerState.setNeedsSpin(false);
			playerState.setCanSpin(false);
			playerState.setCanRemove(true);
			playerState.setHasSpun(false);
		}
	}
	
	// Will set if a player needs to spin still. If not, then they won't be able to spin anymore
	public void setCanSpin(Player player, boolean bool) {
		PlayerState playerState = getPlayerState(player);
		playerState.setCanSpin(bool);
	}
	
	public boolean getCanSpin(Player player) {
		PlayerState playerState = getPlayerState(player);
		return playerState.getCanSpin();
	}
	
	public void setNeedsSpin(Player player, boolean bool) {
		PlayerState playerState = getPlayerState(player);
		playerState.setNeedsSpin(bool);
	}
	
	public boolean getNeedsSpin(Player player) {
		PlayerState playerState = getPlayerState(player);
		return playerState.getNeedsSpin();
	}
	
	public void setCanRemove(Player player, boolean bool) {
		PlayerState playerState = getPlayerState(player);
		playerState.setCanRemove(bool);
	}
	
	public boolean getCanRemove(Player player) {
		PlayerState playerState = getPlayerState(player);
		return playerState.getCanRemove();
	}
	
	// Will return the playerState of the given player
	public PlayerState getPlayerState(Player player) {
		for (PlayerState playerState : playerStates) {
			if (playerState.isPlayer(player)) {
				return playerState;
			}
		}
		return null;
	}
	
	public boolean isGameSpinning() {
		return gameIsSpinning;
	}
	
	public void setSpinnerSpinning(boolean bool) {
		spinnerIsSpinning = bool;
		gameIsSpinning = bool;
	}
	
	public boolean isSpinnerSpinning() {
		return spinnerIsSpinning;
	}
	
	public boolean onePlayerHasSpun() {
		for (PlayerState playerState : playerStates) {
			if (playerState.getHasSpun()) {
				return true;
			}
		}
		return false;
	}
	
	// If anyone still can spin, then return false because that means someone still needs to spin
	public boolean canSpinnerSpin() {
		if (!onePlayerHasSpun()) {
			return false;
		}
		for (PlayerState playerState : playerStates) {
			if (playerState.getNeedsSpin()) {
				return false;
			}
		}
		return true;
	}
	
	public void setPlayerSpun(Player player, boolean bool) {
		PlayerState playerState = getPlayerState(player);
		playerState.setHasSpun(bool);
	}
	
	// check if every player besides the passed in player has already spun
	public boolean everyOtherPlayerSpun(Player player) {
		PlayerState playerState = getPlayerState(player);
		ArrayList<PlayerState> tempPlayerStateArray = new ArrayList<PlayerState>(playerStates);
		tempPlayerStateArray.remove(playerState);
		
		for (PlayerState otherPlayerState : tempPlayerStateArray) {
			if (!otherPlayerState.getHasSpun()) {
				return false;
			}
		}
		return true;
	}
	
	public void setPlayerSpinning(Player player) {
		playerSpinning = player;
		gameIsSpinning = true;
	}
	
	public void resetPlayerSpinning() {
		playerSpinning = null;
		gameIsSpinning = false;
	}
	
	public Player getPlayerSpinning() {
		return playerSpinning;
	}
	
	
}
