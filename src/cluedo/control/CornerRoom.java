package cluedo.control;

import java.util.ArrayList;

public class CornerRoom extends Room{
	private CornerRoom portalTo;
	
	
	public CornerRoom(char ID, String name, ArrayList<Location> doors, ArrayList<Location> playerLocations, CornerRoom portalTo) {
		super(ID, name, doors, playerLocations);
		this.portalTo = portalTo;
	}
	
	public CornerRoom getPortalTo(){
		return portalTo;
	}
	
	public void setPortal(CornerRoom room){
		portalTo = room;
	}
}
