package cluedo.moves;

import cluedo.control.CornerRoom;
import cluedo.control.Location;
import cluedo.control.Player;
import cluedo.control.Room;

public class PortalMove {
	private Player player;
	private CornerRoom roomMovingTo;
	private Room roomFrom;
	
	/**
	 * Handles the moving of a player to a different room
	 * @param room the current room of the player
	 * @param portal the room to move to
	 * @param player the current player
	 */
	public PortalMove(Room room, CornerRoom portal, Player player) {
		roomFrom = room;
		roomMovingTo = portal;
		this.player = player;
		
		movePlayer();
	}
	
	/**
	 * Move player to the opposite room
	 */
	private void movePlayer() {
		//add player to opposite room
		Location newLoc = roomMovingTo.getFreeSpace(roomMovingTo.getPlayers().size());
		player.getCharacter().setCurrentLocation(newLoc);
		roomMovingTo.addPlayer(player);
		player.setRoom(roomMovingTo);
		
		//remove from the previous
		roomFrom.removePlayer(player);
		
	}

}
