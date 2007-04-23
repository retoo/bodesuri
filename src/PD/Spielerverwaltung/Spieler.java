package PD.Spielerverwaltung;

import spielplatz.EndPunkt;

public class Spieler {
	private String name;
	public EndPunkt endpunkt;
	
	public Spieler(String name) {
		this.name = name;
	}

	public Spieler(EndPunkt client, String name) {
		this(name);
		this.endpunkt = client;
		
	}

	public String getName() {
    	return name;
    }
	
	public String toString() {
		return "Spieler " + getName();
	}
}
