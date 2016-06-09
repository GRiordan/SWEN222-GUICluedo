package cluedo.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * Used to create and show the menu items
 *
 */
public class MenuPanel implements ActionListener {
	private CluedoFrame frame;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem menuItemRules;
	private JMenuItem menuItemKeyShrtcuts;
	private JMenuItem menuItemNewGame;
	
	public MenuPanel(CluedoFrame frame){
		this.frame = frame;
	}
	
	

	public void createMenu() {
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		menuItemRules = new JMenuItem("Rules");
		menuItemKeyShrtcuts = new JMenuItem("Keyboard Shortcuts");
		menuItemNewGame = new JMenuItem("New Game");
		
		//add action commands
		menuItemRules.setActionCommand("rules");
		menuItemKeyShrtcuts.setActionCommand("shortcuts");
		menuItemNewGame.setActionCommand("newGame");
		
		//add action listeners
		menuItemRules.addActionListener(this);
		menuItemKeyShrtcuts.addActionListener(this);
		menuItemNewGame.addActionListener(this);
		
		
		menu = new JMenu("Menu");
		menu.add(menuItemRules);
		menu.add(menuItemKeyShrtcuts);
		menu.add(menuItemNewGame);
		
		//add mouse listener
		menu.addMouseListener(new MouseListener(){
		    public void mouseEntered(MouseEvent e) {menu.doClick();}
		    public void mousePressed(MouseEvent e) {}
		    public void mouseReleased(MouseEvent e) {}
		    public void mouseClicked(MouseEvent e) {}
		    public void mouseExited(MouseEvent e) {}
		});
		
		menuBar.add(menu);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if("rules".equals(e.getActionCommand())){
			displayRules();
		}
		else if("shortcuts".equals(e.getActionCommand())){
			displayShortcuts();
		}
		else if("newGame".equals(e.getActionCommand())){
			try {
				frame.newGame();
			} catch (IOException e1) {
				System.out.println("Failed to create new game");
				e1.printStackTrace();
			}
		}
	}


	/**
	 * display the shortcuts in a option pane
	 */
	private void displayShortcuts() {
		JOptionPane.showMessageDialog(frame,
				"Shortcut                                   Description\n"+
			    "ALT + arrowkey...................Used to control player movement\n"
			  + "ALT + a...............................Make an accusation\n"
			  + "ALT + s...............................Make a suggestion\n"
			  + "ALT + p...............................Take a secret passageway\n"
			  + "ALT + e...............................Exit the current room\n"
			  + "ALT + m..............................Make a move\n"
			  + "ALT + q..............................End current turn"
			  + "");
		
	}
	
	/**
	 * display the rules in a option pane
	 */
	private void displayRules() {
		JOptionPane.showMessageDialog(frame, 
				"Make sure the txt file 'cluedoBoard' is in the project dir not the src folder\n\n"
				+ "At the start of your turn you will be given a choice as\n"
				+ "what to do, this will depend on where abouts on the board you are\n\n"
				+ "If you want to enter a room you must move to a door opening\n\n"
				+ "If you are in a room you will be given a chioce of exiting,\n"
				+ "making a suggestion or accusation. If it is a corner room you can choose\n"
				+ "to take a secret passage to the room diagonially opposite. Once you have\n"
				+ " exited a room you have the optioin of making a normal move or accusation.\n\n"
				+ "+-------------  Created by George Riordan and Lauren Hucker  -------------+");
		
	}
}
