package PD.spielerverwaltung;

import spielplatz.Briefkasten;

public class Spieler {
	private String name;
	public Briefkasten endpunkt;
	
	public Spieler(String name) {
		this.name = name;
	}

	public Spieler(Briefkasten client, String name) {
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
