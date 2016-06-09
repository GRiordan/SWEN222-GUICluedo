package cluedo.control;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import cluedo.gui.CluedoCanvas;

public class Character {
	private Location currentLocation;
	private final Location startLocation;
	private final char boardID;
	private final String name;
	private String imgUrl;
	
	public Character(int x, int y, char boardID, String name, String imgUrl) {
		startLocation = new Location(x, y);
		currentLocation = startLocation;
		this.boardID = boardID;
		this.name = name;
		this.imgUrl = imgUrl;
	}

	public Location getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(Location currentLocation) {
		this.currentLocation = currentLocation;
	}

	public Location getStartLocation() {
		return startLocation;
	}
	
	public char getBoardID(){
		return boardID;
	}

	public String getName() {
		return name;
	}

	public BufferedImage getImage() throws IOException {
		java.net.URL imageURL = CluedoCanvas.class.getResource("images/"
				+ imgUrl);
	    return ImageIO.read(imageURL);
	}
	
	

}
