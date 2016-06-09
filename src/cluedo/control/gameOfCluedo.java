package cluedo.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import cluedo.board.*;
import cluedo.moves.Accusation;
import cluedo.moves.KeyboardMove;
import cluedo.moves.Suggestion;

public class gameOfCluedo {
	private int nPlayers;
	private HashSet<Card> cards;
	private ArrayList<Character> characters;
	private ArrayList<Player> players;
	private ArrayList<Room> rooms;
	private ArrayList<Weapon> weapons;
	private Player currentPlayer;
	private Board board;
	private Card[] solution;
	private boolean gameHasFinished;

	public Board getBoard() {
		return board;
	}

	public gameOfCluedo(int nPlayers) throws IOException {
		this.nPlayers = nPlayers;
		this.solution = new Card[3];
		this.cards = new HashSet<Card>();
		this.players = new ArrayList<Player>();
		this.characters = new ArrayList<Character>();
		this.rooms = new ArrayList<Room>();
		this.weapons  = new ArrayList<Weapon>();
		this.gameHasFinished = false;
		
		board = new Board("cluedoBoard.txt", this);
		
		createCharacters();
		createRooms();
		createCornerRooms();
		setWeapons();
		createCards();
		generatePlayers();
		dealCardsToPlayers();
		currentPlayer = players.get(0);
		
		//printSolution();
	}
	
	private void printSolution(){
		for(Card c : solution){
			System.out.println(c.getName());
		}
	}

	/**
	 * set up weapons
	 */
	private void setWeapons() {
		
		Weapon candlestick = new Weapon("Candlestick");
		Weapon dagger = new Weapon("Dagger");
		Weapon leadPipe = new Weapon("Lead Pipe");
		Weapon revolver = new Weapon("Revolver");
		Weapon rope = new Weapon("Rope");
		Weapon spanner = new Weapon("Spanner");
		
		weapons.add(candlestick);
		weapons.add(dagger);
		weapons.add(leadPipe);
		weapons.add(revolver);
		weapons.add(rope);
		weapons.add(spanner);
		
		ArrayList<Room> tempRoom = new ArrayList<Room>();
		for(Room r : rooms){
			tempRoom.add(r);
		}
		//add weapons to rooms
		for(int i = 0; i != weapons.size(); i++){
			int randNum = (int) (Math.random()*tempRoom.size());
			tempRoom.get(randNum).setWeapon(weapons.get(i));
			weapons.get(i).setRoom(tempRoom.get(randNum));
			tempRoom.remove(randNum);
		}
	}

	/**
	 * set up corner rooms with portals 
	 */
	private void createCornerRooms() {
		ArrayList<Location> doors = new ArrayList<Location>();
		ArrayList<Location> playerLocations = new ArrayList<Location>();
		
		//create the corner rooms
		
		//create the Kitchen
		doors.add(new Location(6, 4));
		playerLocations.add(new Location(3, 2));
		playerLocations.add(new Location(3, 3));
		playerLocations.add(new Location(3, 4));
		playerLocations.add(new Location(4, 2));
		playerLocations.add(new Location(4, 3));
		playerLocations.add(new Location(4, 4));
		CornerRoom kitchen = new CornerRoom('K', "Kitchen", doors, playerLocations, null);
		doors.clear();
		playerLocations.clear();
		
		doors.add(new Location(4, 18));
		playerLocations.add(new Location(2, 20));
		playerLocations.add(new Location(2, 21));
		playerLocations.add(new Location(2, 22));
		playerLocations.add(new Location(3, 20));
		playerLocations.add(new Location(3, 21));
		playerLocations.add(new Location(3, 22));
		CornerRoom conservatory = new CornerRoom('C', "Conservatory", doors, playerLocations, null);
		doors.clear();
		playerLocations.clear();
		
		doors.add(new Location(19, 6));
		playerLocations.add(new Location(21, 2));
		playerLocations.add(new Location(21, 3));
		playerLocations.add(new Location(21, 4));
		playerLocations.add(new Location(22, 2));
		playerLocations.add(new Location(22, 3));
		playerLocations.add(new Location(22, 4));
		CornerRoom lounge = new CornerRoom('L', "Lounge", doors, playerLocations, null);
		doors.clear();
		playerLocations.clear();
		
		doors.add(new Location(21, 17));
		playerLocations.add(new Location(23, 19));
		playerLocations.add(new Location(23, 20));
		playerLocations.add(new Location(23, 21));
		playerLocations.add(new Location(24, 19));
		playerLocations.add(new Location(24, 20));
		playerLocations.add(new Location(24, 21));
		CornerRoom study = new CornerRoom('S', "Study", doors, playerLocations, null);
		doors.clear();
		playerLocations.clear();
		
		//add 'portals'
		kitchen.setPortal(study);
		study.setPortal(kitchen);
		conservatory.setPortal(lounge);
		lounge.setPortal(conservatory);
		
		//add to rooms
		rooms.add(kitchen);
		rooms.add(study);
		rooms.add(conservatory);
		rooms.add(lounge);
	}

	/**
	 * set up normal rooms
	 */
	private void createRooms() {
		ArrayList<Location> doors = new ArrayList<Location>();
		ArrayList<Location> playerLocations = new ArrayList<Location>();
		
		//add dining room
		doors.add(new Location(12, 7));
		doors.add(new Location(15, 6));
		playerLocations.add(new Location(12, 2));
		playerLocations.add(new Location(12, 3));
		playerLocations.add(new Location(12, 4));
		playerLocations.add(new Location(13, 2));
		playerLocations.add(new Location(13, 3));
		playerLocations.add(new Location(13, 4));
		rooms.add(new Room('D', "Dining Room", doors, playerLocations));
		doors.clear();
		playerLocations.clear();
		
		//add the Hall
		doors.add(new Location(18, 11));
		doors.add(new Location(18, 12));
		playerLocations.add(new Location(21, 11));
		playerLocations.add(new Location(21, 12));
		playerLocations.add(new Location(21, 13));
		playerLocations.add(new Location(22, 11));
		playerLocations.add(new Location(22, 12));
		playerLocations.add(new Location(22, 13));
		rooms.add(new Room('H', "Hall", doors, playerLocations));
		doors.clear();
		playerLocations.clear();
		
		//add the library
		doors.add(new Location(16, 17));
		doors.add(new Location(14, 20));
		playerLocations.add(new Location(16, 19));
		playerLocations.add(new Location(16, 20));
		playerLocations.add(new Location(16, 21));
		playerLocations.add(new Location(17, 19));
		playerLocations.add(new Location(17, 20));
		playerLocations.add(new Location(17, 21));
		rooms.add(new Room('l', "Library", doors, playerLocations));
		doors.clear();
		playerLocations.clear();
		
		//add the Billards
		doors.add(new Location(9, 18));
		doors.add(new Location(12, 22));
		playerLocations.add(new Location(10, 20));
		playerLocations.add(new Location(10, 21));
		playerLocations.add(new Location(10, 22));
		playerLocations.add(new Location(11, 20));
		playerLocations.add(new Location(11, 21));
		playerLocations.add(new Location(11, 22));
		rooms.add(new Room('B', "Billards", doors, playerLocations));
		doors.clear();
		playerLocations.clear();
		
		//add the Ball room
		doors.add(new Location(5, 8));
		doors.add(new Location(7, 9));
		doors.add(new Location(7, 14));
		doors.add(new Location(5, 15));
		playerLocations.add(new Location(4, 10));
		playerLocations.add(new Location(4, 11));
		playerLocations.add(new Location(4, 12));
		playerLocations.add(new Location(5, 10));
		playerLocations.add(new Location(5, 11));
		playerLocations.add(new Location(5, 12));
		rooms.add(new Room('b', "Ball Room", doors, playerLocations));
		doors.clear();
		playerLocations.clear();
	}

	/**
	 * set up players
	 * assign characters to players
	 */
	private void generatePlayers() {
		for(int i = 0; i != nPlayers; i++){
			players.add(new Player((i+1), characters.get(i))); 
		}
	}
	
	private void dealCardsToPlayers(){
		ArrayList<Card> duplicateOfCards = new ArrayList<Card>();
		for(Card c : cards){
			duplicateOfCards.add(c);
		}

		while(duplicateOfCards.size() >= 1){
			for(Player p : players){
				if(duplicateOfCards.size() != 0){
					p.addCardToHand(duplicateOfCards.get(0));
					duplicateOfCards.remove(0);
				}
			}
		}
	}

	/**
	 * create all 6 characters regardless of number of players
	 */
	private void createCharacters() {
		characters.add(new Character(0, 9, '1', "Miss Scarlett", "Player1.png"));
		characters.add(new Character(0, 14, '2', "Colonel Mustard", "Player2.png"));
		characters.add(new Character(6, 23, '3', "Mrs_White", "Player3.png"));
		characters.add(new Character(19, 23, '4', "The Reverend Green", "Player4.png"));
		characters.add(new Character(24, 7, '5', "Mrs_Peacock", "Player5.png"));
		characters.add(new Character(17, 0, '6', "Professor Plum", "Player6.png"));
	}
	
	/**
	 * create card to deal to players and determine the solution
	 */
	public void createCards(){
		ArrayList<Card> weaponCards = new ArrayList<Card>();
		ArrayList<Card> characterCards = new ArrayList<Card>();
		ArrayList<Card> roomCards = new ArrayList<Card>();
		
		//create cards
		for(Weapon w : weapons){
			weaponCards.add(new Card(w.getName(), "weapon"));
		}
		for(Character c : characters){
			characterCards.add(new Card(c.getName(), "character"));
		}
		for(Room r : rooms){
			roomCards.add(new Card(r.getName(), "room"));
		}
		
		
		//determine the murder scene cards
		solution[0] = characterCards.get((int) (Math.random()*characterCards.size()));
		solution[1] = roomCards.get((int) (Math.random()*roomCards.size()));
		solution[2] = weaponCards.get((int) (Math.random()*weaponCards.size()));
		
		//add cards to set of cards
		for(Card c : weaponCards){
			cards.add(c);
		}
		for(Card c : characterCards){
			cards.add(c);
		}
		for(Card c : roomCards){
			cards.add(c);
		}
		
		//remove the solution cards so they are not dealt to players
		cards.remove(solution[0]);
		cards.remove(solution[1]);
		cards.remove(solution[2]);
	
	}
	
	public void removePlayer(Player p){
		players.remove(p);
	}
	
	//--------------------------------------------//
	//    below are all getters and setters       //
	//--------------------------------------------//

	public HashSet<Card> getCards() {
		return cards;
	}

	public Card[] getSolution() {
		return solution;
	}

	public ArrayList<Character> getCharacters() {
		return characters;
	}

	public ArrayList<Room> getRooms() {
		return rooms;
	}

	public ArrayList<Weapon> getWeapons() {
		return weapons;
	}
	
	public boolean getGameHasFinished(){
		return gameHasFinished;
	}
	

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public ArrayList<Player> getPlayerArray() {
		return players;
	}
	
	

}
