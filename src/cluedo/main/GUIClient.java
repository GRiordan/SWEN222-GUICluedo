package cluedo.main;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

import cluedo.control.gameOfCluedo;
import cluedo.control.Player;
import cluedo.gui.CluedoFrame;
import cluedo.gui.NPlayersPanel;

/**
 * The main class, sets up the window frame and initializes the game
 * @author George Riordan, Lauren Hucker
 *
 */
public class GUIClient {
	private JFrame nPlayersFrame;
	private gameOfCluedo game;
	private int nPlayers;
	private CluedoFrame display;
	
	public GUIClient() throws IOException{
		this.nPlayers = 2; 
		initGame();
	}
	
	/**
	 * Is called by the NPlayersPanel to create the main frame
	 * @throws IOException
	 */
	public void startGame() throws IOException{
		//create a new game of cluedo and dispose the nPlayersFrame
		game = new gameOfCluedo(nPlayers);
		nPlayersFrame.dispose();
		
		//set current player
		Player currentPlayer = game.getPlayerArray().get(0);
		game.setCurrentPlayer(currentPlayer);
		
		//create and display the main frame
		display = new CluedoFrame("Cluedo", game.getBoard(), this, 1, game);
		display.takeTurn(currentPlayer, game);
	}
	
	/**
	 * Is called by NPlayersPanel to set the users choice
	 * @param n number of players
	 */
	public void setPlayers(int n){
		nPlayers = n;
	}
	
	/**
	 * create a new NPlayersFrame which lets the user pick the amount of players
	 */
	private void initGame() {
		nPlayersFrame  = new JFrame();
		NPlayersPanel panel = new NPlayersPanel(this);
		nPlayersFrame.add(panel);
		nPlayersFrame.pack();
		nPlayersFrame.setResizable(false);
		nPlayersFrame.setVisible(true);
		nPlayersFrame.setLocationRelativeTo(null);
	}
	
	public ArrayList<Player> getPlayers() {
		return game.getPlayerArray();
	}

	public Player getCurrentPlayer() {
		return game.getCurrentPlayer();
	}
	
	/**
	 * create a new GUIClient object
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		new GUIClient();
	}

}
