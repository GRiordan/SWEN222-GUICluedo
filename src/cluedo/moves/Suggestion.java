package cluedo.moves;

import java.util.ArrayList;
import java.util.HashSet;

import cluedo.control.*;

	public class Suggestion extends Accusation{
		
	public Suggestion(gameOfCluedo game) {
		super(game);
	}
	
	/**
	 * returns the card that was refuted 
	 * @param c the suggested character
	 * @param w the suggested weapon
	 * @return the card that was refuted or null if no cards are refuted
	 */
	public Card refuteSuggestion(String c, String w){
		Room currentRoom = game.getCurrentPlayer().getRoom();
		HashSet<Card> cards = game.getCards();
		
		//removes current players cards from the set so that their own card can't be refuted
		ArrayList<Card> thisPlayersCards;
		thisPlayersCards = game.getCurrentPlayer().getHand();
		for(Card d : thisPlayersCards){
			cards.remove(d);
		}
		
		for(Card d : cards){
			if(d.getName().equals(c) 
					|| d.getName().equals(currentRoom.getName()) 
					|| d.getName().equals(w)){
				return d;
			}
		}
		return null;
	}

}
