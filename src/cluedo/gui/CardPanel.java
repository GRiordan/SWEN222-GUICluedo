package cluedo.gui;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import java.util.ArrayList;

import javax.swing.JLabel;

import cluedo.control.Card;

/**
 *Used to show the current players cards 
 *
 */
public class CardPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private ArrayList<JLabel> cardLabels;
	private JLabel player;
	
	public CardPanel(ArrayList<Card> cards){
		//create list of labels
		cardLabels = createCards();
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		player = new JLabel("Player 1's cards");
		add(player);
		
		//add labels to pane
		setLabels(cards);
		addLabels(cardLabels);
		setBorder(BorderFactory.createEtchedBorder(1));
	}
	
	/**
	 * set the labels to the right icon
	 * @param cards the cards to be drawn
	 */
	public void setLabels(ArrayList<Card> cards) {
		for(JLabel j : cardLabels){
			j.setIcon(null);
		}
		
		for(int i = 0; i<cards.size(); i++){
			ImageIcon img =createImageIcon("sprites/cards/"+cards.get(i).getName()+".png"); 
			cardLabels.get(i).setIcon(img);
		}
		repaint();
	}
	
	/**
	 * create the labels
	 * @return the list of labels used to display the cards
	 */
	private ArrayList<JLabel> createCards() {
		ArrayList<JLabel> labels = new ArrayList<JLabel>();
		for(int i = 0; i<9; i++){
			JLabel label = new JLabel("");
			labels.add(label);
		}
		
		return labels;
	}
	
	/**
	 * add the labels to the panel
	 * @param labels the array of labels to add
	 */
	private void addLabels(ArrayList<JLabel> labels) {
		for(JLabel l : labels){
			add(l);
		}
	}
	
	private static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = CardPanel.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
	
	/**
	 * set the name of the current player
	 * @param s player name
	 */
	public void setLabel(String s){
		player.setText(s);
	}
}