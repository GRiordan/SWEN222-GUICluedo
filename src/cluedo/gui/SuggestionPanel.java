package cluedo.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cluedo.control.Card;
import cluedo.control.Room;
import cluedo.moves.Accusation;
import cluedo.moves.Suggestion;

/**
 * Used when a player makes a suggestion move
 *
 */
public class SuggestionPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JButton ok;
	private JComboBox character, weapon;
	private CluedoFrame frame;
	private JFrame parent;
	private Room room;
	
	public SuggestionPanel(CluedoFrame frame, JFrame parent) {
		super(new FlowLayout());
		this.frame = frame;
		this.parent = parent;
		room = frame.getGame().getCurrentPlayer().getRoom();
		
		character = new JComboBox(charactersList);
		character.setSelectedIndex(0);
		character.addActionListener(this);
		
		weapon = new JComboBox(weaponsList);
		weapon.setSelectedIndex(0);
		weapon.addActionListener(this);
		
		ok = new JButton("Suggest");
		ok.setActionCommand("suggest");
		ok.addActionListener(this);
		
		add(character);
		add(weapon);
		add(ok);
		setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
	}

	public void actionPerformed(ActionEvent e) {
		if("suggest".equals(e.getActionCommand())){
			String ch = (String) character.getSelectedItem();
			String wpn = (String) weapon.getSelectedItem();
			
			//create a new suggestion move, this handles the logic of it
			Suggestion suggest = new Suggestion(frame.getGame());
			Card c = suggest.refuteSuggestion(ch, wpn);
			
			if(c == null){
				JOptionPane.showMessageDialog(frame, "No player refuted a card");
				
			}
			else {
				JOptionPane.showMessageDialog(frame, c.getName() + " was refuted");
			}
			parent.dispose();
		}
	}
	
	private String[] weaponsList = {
			"Candlestick",
			"Dagger",
			"Lead Pipe",
			"Revolver",
			"Rope",
			"Spanner"
	};
	private String[] charactersList= {
			"Colonel Mustard",
			"Miss Scarlett",
			"Mrs_Peacock",
			"Mrs_White",
			"Professor Plum",
			"The Reverend Green"
	};
	
}
