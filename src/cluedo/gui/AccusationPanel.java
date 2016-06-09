package cluedo.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import cluedo.moves.Accusation;

/**
 * used when a user picks a accusation move
 *
 */
public class AccusationPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JButton ok;
	private JComboBox character, room, weapon;
	private CluedoFrame frame;
	private JFrame parent;

	/**
	 * 
	 * @param frame the main frame
	 * @param parent the frame this object is added to
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public AccusationPanel(CluedoFrame frame, JFrame parent) {
		super(new FlowLayout());
		this.frame = frame;
		this.parent = parent;
		
		//create the drop down menu for characters
		character = new JComboBox(charactersList);
		character.setSelectedIndex(0);
		character.addActionListener(this);
		
		//create the drop down menu for rooms
		room = new JComboBox(roomsList);
		room.setSelectedIndex(0);
		room.addActionListener(this);
		
		//create the drop down menu for weapons
		weapon = new JComboBox(weaponsList);
		weapon.setSelectedIndex(0);
		weapon.addActionListener(this);
		
		//add the button to handle the accuse event
		ok = new JButton("Accuse");
		ok.setActionCommand("accuse");
		ok.addActionListener(this);
		
		//add the components to this
		add(character);
		add(room);
		add(weapon);
		add(ok);
		setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
	}
	
	/**
	 * Handles the event when the user presses the accuse button
	 */
	public void actionPerformed(ActionEvent e) {
		if("accuse".equals(e.getActionCommand())){
			String ch = (String) character.getSelectedItem();
			String rm = (String) room.getSelectedItem();
			String wpn = (String) weapon.getSelectedItem();
			Accusation acc = new Accusation(frame.getGame());
			if(acc.checkAccusation(ch, rm, wpn)){
				frame.endGame(parent);
			}
			else{
				frame.removePlayer(parent);
			}
		}
	}
	
	//-----------------------------//
	//   The lists of the items    //
	//-----------------------------//
	
	private String[] weaponsList = {
			"Candlestick",
			"Dagger",
			"Lead Pipe",
			"Revolver",
			"Rope",
			"Spanner"
	};
	private String[] roomsList = {
			"Ball Room",
			"Billards",
			"Conservatory",
			"Dining Room",
			"Hall",
			"Kitchen",
			"Library",
			"Lounge",
			"Study"
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
