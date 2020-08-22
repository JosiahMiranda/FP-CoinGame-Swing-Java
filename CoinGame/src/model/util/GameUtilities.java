package model.util;

import model.enumeration.CoinFace;

public class GameUtilities {
	
	// Just for turning HEADS to Heads and TAILS to Tails
	public static String caseString(CoinFace coinFace) {
		return coinFace.toString().substring(0,1) + coinFace.toString().substring(1).toLowerCase();
	}
	
}
