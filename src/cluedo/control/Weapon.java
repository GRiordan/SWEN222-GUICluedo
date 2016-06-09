package cluedo.control;

import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import cluedo.gui.CluedoCanvas;

public class Weapon {
	private String name;
	private Location l;
	private Room room;
	
	public Weapon(String name) {
		this.name = name;
		this.l = null;
		this.room = null;
	}
	
	public Location getLocation(){
		return l;
	}
	
	public void setRoom(Room r){
		this.room = r;
		setLocation(r);
	}
	
	/**
	 * Sets the location of a weapon dependent on what room it is in
	 * @param r the room
	 */
	public void setLocation(Room r){
		if(r == null) {l = null;}
		else{
			String name = r.getName();
			if("Ball Room".equals(name)){ l = new Location(2, 10); }
			else if("Billards".equals(name)){ l = new Location(8, 22); }
			else if("Conservatory".equals(name)){ l = new Location(1, 22); }
			else if("Dining Room".equals(name)){ l = new Location(10, 1); }
			else if("Hall".equals(name)){ l = new Location(23, 10); }
			else if("Kitchen".equals(name)){ l = new Location(2, 1); }
			else if("Library".equals(name)){ l = new Location(15, 22); }
			else if("Lounge".equals(name)){ l = new Location(23, 1); }
			else if("Study".equals(name)){ l = new Location(23, 22); }
		}
	}
	
	/**
	 * 
	 * @return the image of the weapon
	 * @throws IOException
	 */
	public BufferedImage getImage() throws IOException{
		String url = "Weapons/"+name+".png";
		java.net.URL imageURL = Weapon.class.getResource(url);
	    BufferedImage img = ImageIO.read(imageURL);
	    return img;
	}
	
	public String getName(){
		return name;
	}

	public Room getRoom() {
		return room;
	}

}
