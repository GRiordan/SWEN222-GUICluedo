package cluedo.gui;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import cluedo.board.Board;
import cluedo.control.CornerRoom;
import cluedo.control.Location;
import cluedo.control.Player;
import cluedo.control.Room;
import cluedo.control.gameOfCluedo;
import cluedo.main.GUIClient;
import cluedo.moves.PortalMove;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.io.IOException;

import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
 * The main window and where most of the logic for the GUI
 *
 */
public class CluedoFrame extends JFrame implements WindowListener{
	private ArrowPanel arrowPanel;
	private MovePanel movePanel;
	private CardPanel cardPanel;
	private CluedoCanvas BOARD;
	private GUIClient client;
	private gameOfCluedo game;
	private int player;

	public CluedoFrame(String title, Board game, GUIClient client, int uid, gameOfCluedo game2) throws IOException {
		super(title);
		this.client = client;
		this.game = game2;
		player = 0;
		
		//add the components
		arrowPanel = new ArrowPanel(this);
		
		BOARD = new CluedoCanvas(client, this);
		getContentPane().add(BOARD);
		
		movePanel = new MovePanel(this);
		cardPanel = new CardPanel(client.getCurrentPlayer().getHand());
		
		//create the layout
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		
		//set the horizontal group
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(BOARD, GroupLayout.PREFERRED_SIZE, 611, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(movePanel, GroupLayout.PREFERRED_SIZE, 304, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(69)
							.addComponent(cardPanel, GroupLayout.PREFERRED_SIZE, 192, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(61)
							.addComponent(arrowPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(59))
		);
		//set the vertical group
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(BOARD, GroupLayout.PREFERRED_SIZE, 608, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(movePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(arrowPanel, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cardPanel, GroupLayout.PREFERRED_SIZE, 314, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		getContentPane().setLayout(groupLayout);
		
		// Display window
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		pack();
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);

		//add window listener
		addWindowListener(this);
		
		//create the menu
		MenuPanel menu = new MenuPanel(this);
		menu.createMenu();
		
	}
	
	
	//----------------------------------//
	// The following is the game logic  //
	//----------------------------------//
	
	/**
	 * Returns the game of cluedo
	 * @return
	 */
	public gameOfCluedo getGame(){
		return game;
	}
	
	/**
	 * greys out the arrow buttons
	 */
	public void greyOutArrowButtons() {
		arrowPanel.disableButtons();
		
	}

	/**
	 * this gets called at the start of each turn
	 * it will look at whereabouts on the board you are and from that 
	 * decide what moves are allowed and then pass that onto the 
	 * movePanel so the player can choose a move
	 * @param currentPlayer the current player
	 * @param game that game of cluedo
	 */
	public void takeTurn(Player currentPlayer, gameOfCluedo game) {
		//show the players hand
		cardPanel.setLabels(currentPlayer.getHand());
		cardPanel.setLabel("Player "+currentPlayer.getCharacterID()+"'s cards");
		repaint();
		
		Location l = currentPlayer.getCharacter().getCurrentLocation();
		char playerPosition = game.getBoard().getCharAt(l.getX(), l.getY());
		
		movePanel.setPlayerLabel(currentPlayer);
		
		if(playerPosition == '_' || playerPosition == 'e' || playerPosition == '$'){
			//normal move
			greyOutArrowButtons();
			movePanel.normalMove();
		}
		else {
			Room room = currentPlayer.getRoom();

			//has the added option of a portal move
			if(room instanceof CornerRoom){
				//set buttons to corner room 
				greyOutArrowButtons();
				movePanel.cornerRoomMove();
			}
			//make a suggestion with the current room, make a accusation, exit the room
			else {
				//set buttons to normal rooms
				greyOutArrowButtons();
				movePanel.roomMove();
			}
		}
	}		
	
	/**
	 * this gets called from the move panel,
	 * when the player selects a move. It then
	 * goes into the arrow panel which handels the 
	 * movement
	 */
	public void normalMove(){
		arrowPanel.normalMove();
	}

	/**
	 * This method is used at the end of each turn
	 * it increments the player and repaints the display
	 * then calls takeTurn.
	 */
	public void endCurrentTurn() {
		if(player == game.getPlayerArray().size()-1){
			player = 0;
			game.setCurrentPlayer(game.getPlayerArray().get(player));
		}
		else {
			player++;
			game.setCurrentPlayer(game.getPlayerArray().get(player));
		}
		repaint();
		
		takeTurn(game.getCurrentPlayer(), game);
	}
	
	/**
	 * Sets the next player
	 * 
	 * Helper method for endCurrentTurn() and endGame()
	 */
	private void setNextPlayer() {
		if(player == game.getPlayerArray().size()-1){
			player = 0;
			game.setCurrentPlayer(game.getPlayerArray().get(player));
		}
		else {
			player++;
			game.setCurrentPlayer(game.getPlayerArray().get(player));
		}
		repaint();
	}
	
	/**
	 * Displays the endgame option pane
	 * Tells you you won and asks if you would like
	 * to play again or quit
	 */
	public void endGame(){
		Object[] options = {"New Game",
		                    "Exit",};
		int n = JOptionPane.showOptionDialog(this,
		    "Congratulations "+game.getCurrentPlayer().getPlayerName()+", you have won the game!"
		    + "\nWould you like to start again or exit?",
		    "Congratulations!",
		    JOptionPane.YES_NO_OPTION,
		    JOptionPane.QUESTION_MESSAGE,
		    null,
		    options,
		    options[1]);
		
		if(n == JOptionPane.YES_OPTION){
			try {
				newGame();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else{
			System.exit(0);
		}
	}
	
	/**
	 * Used when a player wins from an accusation
	 *
	 * @param f the frame of the accusation panel
	 */
	public void endGame(JFrame f) {
		f.dispose();
		endGame();
	}
	
	/**
	 * Removes a player when they make a wrong accusation
	 * @param parent the frame the accusation panel was drawn on
	 */
	public void removePlayer(JFrame parent) {
		parent.dispose();
		
		//if only two people are left the last player wins
		if(game.getPlayerArray().size() == 2){
			setNextPlayer();
			endGame();
		}
		//else remove the player and keep playing
		else{
			int i = player;
			Player toRemove = game.getCurrentPlayer();
			setNextPlayer();
			game.removePlayer(toRemove);
			player = i;
			takeTurn(game.getCurrentPlayer(), game);
		}
	}
	
	/**
	 * Called by the move panel
	 * Represents an exit of a room
	 */
	public void exitMove() {
		Room room = game.getCurrentPlayer().getRoom();
		
		String[] doors = new String[room.getDoors().size()];
		int i =0;
		
		//create the list of available doors
		for(Location door : room.getDoors()){
			String temp = doorToString(door); 
			doors[i] = temp;
			i+=1;
		}
		 
		//show the exit options
		String s = (String)JOptionPane.showInputDialog(
		                    this,
		                    "Which door would you like to exit from?",
		                    "Choose a door",
		                    JOptionPane.PLAIN_MESSAGE,
		                    null,
		                    doors,
		                    "");
		
		//Removing player from the room
		room.removePlayer(game.getCurrentPlayer());
		game.getCurrentPlayer().setRoom(null);
		
		//updating players position
		Location newLoc = room.getDoors().get(0);
		
			for(i = 0; i<room.getDoors().size(); i++){
				if(s.equals(doorToString(room.getDoors().get(i)))){
					newLoc = room.getDoors().get(i);
				}
			
		}		
			
		game.getCurrentPlayer().takeTurn(newLoc);
		repaint();
		movePanel.normalMove();
	}
	
	/*
	 * Helper method for exitMove()
	 */
	private String doorToString(Location door){
		String[] alpha = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
				"J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y"};
		int x = door.getY();
		int y = door.getX()+1;
		String toReturn = alpha[x]+(Integer.toString(y));
		
		return toReturn;
	}

	/**
	 * Called by the move panel
	 * Represents a portal move
	 */
	public void portalMove() {
		Room room  = game.getCurrentPlayer().getRoom();
		CornerRoom portal = ((CornerRoom) room).getPortalTo();
		Player currentPlayer = game.getCurrentPlayer();
		
		new PortalMove(room, portal, currentPlayer);
	}
	
	/**
	 * Called by the move panel
	 * Represents a accusation
	 */
	public void accusationMove(){
		JFrame f = new JFrame("Choose your accusation");
		AccusationPanel p = new AccusationPanel(this, f);
		f.add(p);
		f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		f.pack();
		f.setVisible(true);
	}
	
	/**
	 * Called by the move panel
	 * Represents a suggestion
	 */
	public void suggestionMove() {
		JFrame f = new JFrame("Choose your suggestion");
		SuggestionPanel p = new SuggestionPanel(this, f);
		f.add(p);
		f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		f.pack();
		f.setVisible(true);
	}

	/**
	 * used to create a new game
	 * @throws IOException
	 */
	public void newGame() throws IOException {
		new GUIClient();
		this.dispose();
		
		
	}
	
	
	//---------------------------------//
	//  Handles the window listeners   //
	//---------------------------------//

	@Override
	public void windowOpened(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	public void windowClosing(WindowEvent e) {
		JOptionPane closingOption = new JOptionPane("Are You sure?",JOptionPane.QUESTION_MESSAGE,
				JOptionPane.YES_NO_OPTION);
		
		int result = JOptionPane.showConfirmDialog(null,
				"Are you sure you want to exit?",
				"Exit",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
	
				if(result == JOptionPane.YES_OPTION){
					System.exit(0);
				}
	}
	

}
