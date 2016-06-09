package cluedo.control;

import java.util.ArrayList;

public class Player {
	private final int characterName;
	private final Character character;
	private ArrayList<Card> hand;
	private Room room = null;

	public Player(int i, Character c) {
		this.characterName = i;
		this.character = c;
		hand = new ArrayList<Card>();
	}


	/**
	 * check to see if a player is in a certain position
	 * @param x position
	 * @param y position
	 * @return boolean
	 */
	public boolean checkPlayer(int x, int y) {
		Location l = character.getCurrentLocation();
		return (l.getX() == x) && (l.getY() == y);
	}

	public int getCharacterID(){
		return characterName;
	}

	public Character getCharacter(){
		return character;

	}

	public void addCardToHand(Card c){
		hand.add(c);
	}


	/**
	 * moves player to a given board position once the move has be approved as valid
	 * @param moves - the number of moves rolled by the dice
	 * @param x - new x position
	 * @param y - new y position
	 */
	public void takeTurn(int moves, int x, int y) {
		Location newLoc = new Location(x-1,y-1);

		character.setCurrentLocation(newLoc);
	}
	
	public void takeTurn(Location l){
		character.setCurrentLocation(l);
	}

	/**
	 * register that a player has entered a room
	 * @param game
	 */
	public void moveToRoom(gameOfCluedo game) {
		ArrayList<Room> rooms = game.getRooms();

		//find out what room the door is connected to
		for(Room r : rooms){
			ArrayList<Location> doors = r.getDoors();
			for(Location l : doors){
				if(l.equals(character.getCurrentLocation())){

					this.room = r;
					
					Location newLoc = room.getFreeSpace(room.getPlayers().size());
					character.setCurrentLocation(newLoc);
					room.addPlayer(this);
					return;
				}
			}
		}

	}

	public String getPlayerName(){
		return character.getName();
	}

	public void setRoom(Room room){
		this.room = room;
	}

	public Room getRoom() {
		return room;
	}

	public ArrayList<Card> getHand(){
		return hand;
	}
}

