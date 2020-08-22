package view.model;

import model.interfaces.Player;

public class PlayerState {
	
	private Player player;
	private boolean canSpin;
	private boolean needsToSpin;
	private int previousPoints;
	private boolean canRemove;
	private boolean hasSpun;
	
	public PlayerState(Player player) {
		this.player = player;
		canSpin = false;
		needsToSpin = false;
		canRemove = true;
		hasSpun = false;
	}
	
	public boolean isPlayer(Player player) {
		return this.player.equals(player);
	}
	
	public void setCanSpin(boolean bool) {
		canSpin = bool;
	}
	
	public boolean getCanSpin() {
		return canSpin;
	}
	
	public void setHasSpun(boolean bool) {
		hasSpun = bool;
	}
	
	public boolean getHasSpun() {
		return hasSpun;
	}
	
	public void setNeedsSpin(boolean bool) {
		needsToSpin = bool;
	}
	
	public boolean getNeedsSpin() {
		return needsToSpin;
	}
	
	public void setPreviousPoints(int points) {
		previousPoints = points;
	}
	
	public int getPreviousPoints() {
		return previousPoints;
	}
	
	public void setCanRemove(boolean bool) {
		canRemove = bool;
	}
	
	public boolean getCanRemove() {
		return canRemove;
	}
	
	public String toString() {
		return player.getPlayerName();
	}
}
