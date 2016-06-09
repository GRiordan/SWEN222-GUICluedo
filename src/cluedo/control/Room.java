package cluedo.control;

import java.util.ArrayList;

public class Room {
	private char ID;
	private String name;
	private Weapon weapon = null;
	private ArrayList<Location> doors;
	private ArrayList<Player> playersInRoom;
	private ArrayList<Location> playerLocations;
	
	public Room(char ID, String name, ArrayList<Location> doors, ArrayList<Location> playerLocations) {
		this.ID = ID;
		this.name = name;
		
		this.doors = new ArrayList<Location>();
		for(Location d : doors){
			this.doors.add(d);
		}
		
		this.playerLocations = new ArrayList<Location>();
		for(Location l : playerLocations){
			this.playerLocations.add(l);
		}
		
		playersInRoom = new ArrayList<Player>();
	}
	
	/**
	 * determines a location for a player to 'stand' when they enter a room
	 * rather than staying in the doorway.
	 * @param i how many players are initially in the room
	 * @return the location to move the new player to enter
	 */
	public Location getFreeSpace(int i){
		return playerLocations.get(i);
		
	}
	
	public void addPlayer(Player player){
		playersInRoom.add(player);
	}
	
	public void removePlayer(Player player){
		playersInRoom.remove(player);
	}
	
	
	public Weapon getWeapon() {
		return weapon;
	}
	
	public boolean hasWeapon(){
		if(weapon == null){
			return true;
		}
		return false;
	}
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public char getID() {
		return ID;
	}

	public String getName() {
		return name;
	}

	public ArrayList<Location> getDoors() {
		return doors;
	}

	public ArrayList<Player> getPlayers() {
		return playersInRoom;
	}
	
	

}
