package cluedo.control;

public class Card {
	String name;
	String type;
	
	public Card(String n, String t){
		this.name = n;
		this.type = t;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}
	
}
