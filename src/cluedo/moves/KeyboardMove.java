package cluedo.moves;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import cluedo.control.Location;
import cluedo.control.Player;
import cluedo.control.gameOfCluedo;
import cluedo.gui.CluedoFrame;

public class KeyboardMove{
	private Player player;
	private gameOfCluedo game;
	private Location l;
	private int x;
	private int y;
	private int movesLeft;
	private boolean moveToCharIsE;
	private CluedoFrame frame;
	private int movesTaken;
	
	/**
	 * Constructor for making a keyboard move
	 * @param game the current game, holds all the information about players, cards etc
	 * @param currentPlayer the current player
	 * @param moves the number of moves a player is allowed
	 * @param frame the main frame of the GUI 
	 */
	public KeyboardMove(gameOfCluedo game, Player currentPlayer, int moves, CluedoFrame frame){
		this.player = currentPlayer;
		this.game = game;
		this.frame = frame;
		this.movesLeft = moves;
		this.moveToCharIsE = false;
		this.movesTaken = 0;
		
		l = player.getCharacter().getCurrentLocation();
		x = l.getY();
		y = l.getX();
		
	}
	
	/**
	 * increment and/decrement x and y depending on move
	 * @param move the direction of the move
	 */
	public void makeMove(String move){
		move = move.toLowerCase();
		if(move.equals("w")){
			y--;
			move();
		}
		else if(move.equals("a")){
			x--;
			move();
		}
		else if(move.equals("s")){
			y++;
			move();
		}
		else if(move.equals("d")){
			x++;
			move();
		}
	}
	
	/**
	 * Updates the players position to its new location	 
	 */
	private void move(){
		Location newLoc = new Location(y, x);
		player.takeTurn(newLoc);
		if(moveToCharIsE){
			player.moveToRoom(game);
			movesTaken = movesLeft;
		}
	}
	
	/**
	 * Checks the proposed move for a illegal move
	 * @param move the direction of the move
	 * @return if the passed in move was legal or not
	 */
	public boolean isLegalMove(String move){
		char moveToChar = ' ';
		move = move.toLowerCase();
		if(move.equals("w")){       moveToChar = game.getBoard().getCharAt(y-1, x);}
		else if(move.equals("a")){  moveToChar = game.getBoard().getCharAt(y, x-1);}
		else if(move.equals("s")){  moveToChar = game.getBoard().getCharAt(y+1, x);}
		else if(move.equals("d")){  moveToChar = game.getBoard().getCharAt(y, x+1);}
		
		if(moveToChar == 'e'){
			moveToCharIsE = true;
		}
		
		//not allowed to enter a room unless from the door
		if(moveToChar == 'K' || moveToChar == 'D'
				|| moveToChar == 'L' || moveToChar == 'l'
				|| moveToChar == 'H' || moveToChar == 'B'
				|| moveToChar == 'b' || moveToChar == 's'
				|| moveToChar == 'C'){
			
			displayWallError();
			return false;
		}
		
		//not allowed on '#' chars
		if(moveToChar == '#') { 
			displayError();
			return false; 
		}
		
		//not allowed to move to a square that is already occupied
		ArrayList<Player> players = game.getPlayerArray();
		Location newLoc = new Location(y-1, x-1);
		
		for(Player p : players){
			if(p.getCharacterID() != player.getCharacterID()){
				if(p.getCharacter().getCurrentLocation().equals(newLoc)){
					displayPlayerError();
					return false;
				}
			}
		}
		return true;
	}
	
	private void displayError() {
		JOptionPane.showMessageDialog(frame,
			    "You cannot walk there, try again",
			    "Warning",
			    JOptionPane.WARNING_MESSAGE);
		
	}
	
	private void displayWallError() {
		JOptionPane.showMessageDialog(frame,
			    "You cannot walk through walls!",
			    "Wall Warning",
			    JOptionPane.WARNING_MESSAGE);
		
	}
	
	private void displayPlayerError() {
		JOptionPane.showMessageDialog(frame,
			    "Cannot move on top of other players",
			    "Player Warning",
			    JOptionPane.WARNING_MESSAGE);
		
	}
}
