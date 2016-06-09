package cluedo.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cluedo.control.Location;
import cluedo.control.Player;
import cluedo.control.Weapon;
import cluedo.main.GUIClient;

public class CluedoCanvas extends JPanel implements MouseListener{
	private static final long serialVersionUID = 1L;
	private GUIClient client;
	private CluedoFrame frame;
	private static final String IMAGE_PATH = "images/";
	private BufferedImage boardImage;
	
	public CluedoCanvas(GUIClient client, CluedoFrame frame) throws IOException {
		this.client = client;
		this.frame = frame;
		
		java.net.URL imageURL = CluedoCanvas.class.getResource(IMAGE_PATH
				+ "board.jpg");
	    boardImage = ImageIO.read(imageURL);
	    //createWeaponIcons();
	    addMouseListener(this);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//draw the underlying board
		g.drawImage(boardImage, 0, 0,  this);
		
		//paint the players
		BufferedImage img = null;
		for(Player p : client.getPlayers()){
			try {
				img = p.getCharacter().getImage();
			} catch (IOException e) {
				System.out.println("Could not find image:");
				e.printStackTrace();
			}
			int x = p.getCharacter().getCurrentLocation().getY()*24;
			int y = p.getCharacter().getCurrentLocation().getX()*24;
			g.drawImage(img, x, y, this);
		}
		
		//paint the weapons
		ArrayList<Weapon> weapons = frame.getGame().getWeapons();
		for(Weapon w : weapons){
			int x = w.getLocation().getY()*24;
			int y = w.getLocation().getX()*24;
			try {
				g.drawImage(w.getImage(), x, y, this);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Dimension getPreferredSize(){
		Dimension d = new Dimension();
		d.setSize(600,576);
		return d.getSize();
	}
	
	/**
	 * open a option pane to show the weapon information
	 * @param w the weapon
	 */
	private void displayWeaponInformation(Weapon w){
		JOptionPane.showMessageDialog(frame,
			    "This is the "+w.getName()+" it is in the "+w.getRoom().getName());
	}
	
	public String getToolTipText(MouseEvent e){
		int x = e.getX();
		int y = e.getY();
		
		for(Weapon w : frame.getGame().getWeapons()){
			Location l = w.getLocation();
			int xLoc = l.getY()*24;
			int yLoc = l.getX()*24;
			if(x >= xLoc && x<=(xLoc+24) && y >= yLoc && y<=(yLoc+24)){
				return w.getName();
			}
		}
		return null;
	}
	
	
	//---------------------------//
	// Handles the mouse events  //
	//---------------------------//
	
	
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}


	@Override
	public void mouseClicked(MouseEvent e) {
		//if mouse is in the same place as a weapon, display an options pane with info
		//about it
		int x = e.getX();
		int y = e.getY();
		
		setToolTipText(getToolTipText(e));
		for(Weapon w : frame.getGame().getWeapons()){
			Location l = w.getLocation();
			int xLoc = l.getY()*24;
			int yLoc = l.getX()*24;
			if(x >= xLoc && x<=(xLoc+24) && y >= yLoc && y<=(yLoc+24)){
				displayWeaponInformation(w);
			}
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		//if mouse is in the same place as a weapon, display the weapons name
		setToolTipText(getToolTipText(e));
	}

}