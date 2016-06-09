package cluedo.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cluedo.control.Player;

/**
 * Used to display the available moves the player can pick
 *
 */
public class MovePanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JButton move, accusation, suggestion, portal, exit, endTurn;
	private JLabel player;
	private CluedoFrame frame;
	
	public MovePanel(CluedoFrame frame){
		super(new GridLayout(2, 1));
		this.frame = frame;
		
		player = new JLabel("It is ");
		
		move = new JButton("Move");
		move.setActionCommand("move");
		move.setMnemonic(KeyEvent.VK_M);
		
		accusation = new JButton("Accusation");
		accusation.setActionCommand("accusation");
		accusation.setMnemonic(KeyEvent.VK_A);
		
		suggestion = new JButton("Suggestion");
		suggestion.setActionCommand("suggestion");
		suggestion.setMnemonic(KeyEvent.VK_S);
		
		portal = new JButton("Portal");
		portal.setActionCommand("portal");
		portal.setMnemonic(KeyEvent.VK_P);
		
		exit = new JButton("Exit Room");
		exit.setActionCommand("exit");
		exit.setMnemonic(KeyEvent.VK_E);
		
		endTurn = new JButton("End Turn");
		endTurn.setActionCommand("endTurn");
		endTurn.setMnemonic(KeyEvent.VK_Q);
		
		//Group the buttons
        ButtonGroup group = new ButtonGroup();
        group.add(move);
        group.add(accusation);
        group.add(suggestion);
        group.add(portal);
        group.add(exit);
        group.add(endTurn);
 
        //add listeners to the buttons
        move.addActionListener(this);
        accusation.addActionListener(this);
        suggestion.addActionListener(this);
        portal.addActionListener(this);
        exit.addActionListener(this);
        endTurn.addActionListener(this);
        
        JPanel movePanel = new JPanel(new GridLayout(3, 2));
        movePanel.add(move);
        movePanel.add(accusation);
        movePanel.add(suggestion);
        movePanel.add(portal);
        movePanel.add(exit);
        movePanel.add(endTurn);
        movePanel.setBorder(BorderFactory.createEtchedBorder(1));
        
        add(player);
        add(movePanel);
        setBorder(BorderFactory.createEtchedBorder(1));
	}

	

	@Override
	public void actionPerformed(ActionEvent e) {
		if("move".equals(e.getActionCommand())){
			setAllFalse();
			frame.normalMove();
		}
		else if("accusation".equals(e.getActionCommand())){
			setAllFalse();
			frame.accusationMove();
		}
		else if("suggestion".equals(e.getActionCommand())){
			setAllFalse();
			frame.suggestionMove();
		}
		else if("portal".equals(e.getActionCommand())){
			setAllFalse();
			frame.portalMove();
		}
		else if("exit".equals(e.getActionCommand())){
			setAllFalse();
			frame.exitMove();
		}
		else if("endTurn".equals(e.getActionCommand())){
			frame.endCurrentTurn();
		}
	}
	
	/**
	 * enable/disable buttons for a normal move
	 */
	public void normalMove(){
		move.setEnabled(true);
		accusation.setEnabled(true);
		suggestion.setEnabled(false);
		exit.setEnabled(false);
		portal.setEnabled(false);
	}
	
	/**
	 * enable/disable buttons for a room move
	 */
	public void roomMove(){
		move.setEnabled(false);
		accusation.setEnabled(true);
		suggestion.setEnabled(true);
		exit.setEnabled(true);
		portal.setEnabled(false);
	}
	
	/**
	 * enable/disable buttons for a corner room move
	 */
	public void cornerRoomMove(){
		roomMove();
		portal.setEnabled(true);
	}
	
	/**
	 * disable all buttons
	 */
	public void setAllFalse(){
		move.setEnabled(false);
		accusation.setEnabled(false);
		suggestion.setEnabled(false);
		exit.setEnabled(false);
		portal.setEnabled(false);
	}
	
	/**
	 * enable all buttons
	 */
	public void setAllTrue(){
		move.setEnabled(true);
		accusation.setEnabled(true);
		suggestion.setEnabled(true);
		exit.setEnabled(true);
		portal.setEnabled(true);
	}

	public void setPlayerLabel(Player currentPlayer) {
		player.setText("It is "+currentPlayer.getPlayerName()+"("+currentPlayer.getCharacterID()+")'s turn");
	}
}

