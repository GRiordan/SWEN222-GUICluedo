package cluedo.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JLabel;

import cluedo.moves.KeyboardMove;

import java.awt.GridLayout;

/**
 *Used to show and handle the events from the arrow panel
 */
public class ArrowPanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JButton up, down, right, left;
	private JLabel lblMoves;
	private int moves = 12;
	private CluedoFrame frame;
	
	public ArrowPanel(CluedoFrame frame){
		this.frame = frame;
		
		//create the images for the arrow keys
		ImageIcon upIcon = createImageIcon("sprites/Up_key.png");
        ImageIcon downIcon = createImageIcon("sprites/Down_key.png");
        ImageIcon leftIcon = createImageIcon("sprites/Left_key.png");
        ImageIcon rightIcon = createImageIcon("sprites/Right_key.png");
		
        
        setLayout(new GridLayout(0, 3, 0, 0));
        
        //create the spaces between arrow keys and move label
        lblMoves = new JLabel("Moves: "+moves);
		JLabel label = new JLabel("");
		JLabel label_1 = new JLabel("");
		JLabel label_2 = new JLabel("");
		JLabel label_3 = new JLabel("");
		
		up = new JButton(upIcon);
		up.addActionListener(this);
		up.setActionCommand("up");
		up.setMnemonic(KeyEvent.VK_UP);
		
		left = new JButton(leftIcon);
		left.addActionListener(this);
		left.setActionCommand("left");
		left.setMnemonic(KeyEvent.VK_LEFT);
		
		right = new JButton(rightIcon);
		right.addActionListener(this);
		right.setActionCommand("right");
		right.setMnemonic(KeyEvent.VK_RIGHT);
		
		down = new JButton(downIcon);
		down.addActionListener(this);
		down.setActionCommand("down");
		down.setMnemonic(KeyEvent.VK_DOWN);
		
		add(label);
		add(up);
		add(label_1);
		add(left);
		add(lblMoves);
		add(right);
		add(label_2);
		add(down);
		add(label_3);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//check to make sure still have moves
		//check to see if it is valid move
		//if it is not valid create pop up error message
		//else move
		if("up".equals(e.getActionCommand())){
			makeMove("w");
		}
		else if("down".equals(e.getActionCommand())){
			makeMove("s");
		}
		else if("right".equals(e.getActionCommand())){
			makeMove("d");
		}
		else if("left".equals(e.getActionCommand())){
			makeMove("a");
		}
    }
	
	/**
	 * make a move
	 * @param move the direction of the move
	 */
	public void makeMove(String move){
		//check to make sure still have moves
		if(moves > 0){
			KeyboardMove m = new KeyboardMove(frame.getGame(), frame.getGame().getCurrentPlayer(), moves, frame);
			//check to see if it is valid move
			if(m.isLegalMove(move)){
				m.makeMove(move);
				moves--;
				lblMoves.setText("Moves: "+moves);
				frame.repaint();
			}
		}
		else{
			disableButtons();
			displayError();
		}
	}
	
	private void displayError() {
		JOptionPane.showMessageDialog(frame,
			    "You have no more moves left!.",
			    "Warning",
			    JOptionPane.WARNING_MESSAGE);
		
	}
	
	private static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = ArrowPanel.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
	
	/**
	 * disable the arrow keys
	 */
	public void disableButtons(){
		up.setEnabled(false);
        down.setEnabled(false);
        right.setEnabled(false);
        left.setEnabled(false);
	}
	
	/**
	 * enable the arrow keys
	 */
	public void enableButtons(){
		up.setEnabled(true);
        down.setEnabled(true);
        right.setEnabled(true);
        left.setEnabled(true);
	}
	
	/**
	 * Initialize the arrow panel
	 */
	public void normalMove() {
		enableButtons();
		moves = (int)(Math.random()*11)+2;
		lblMoves.setText("Moves: "+moves);
	}
}