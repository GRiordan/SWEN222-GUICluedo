package cluedo.moves;

import cluedo.control.*;

public class Accusation {
	protected gameOfCluedo game;

	public Accusation(gameOfCluedo game) {
		this.game = game;
	}
	
	/**
	 * checks the accusation against the cards in the solution
	 * @param character the name of the character accused
	 * @param room the room thought to be the murder room
	 * @param weapon the weapon thought to be used
	 * @return whether the accusation was correct or not
	 */
	public boolean checkAccusation(String character, String room, String weapon) {
		Card[] solution = game.getSolution();
		
		return solution[0].getName().equals(character)
				&& solution[1].getName().equals(room)
				&& solution[2].getName().equals(weapon);
		
	}
}
