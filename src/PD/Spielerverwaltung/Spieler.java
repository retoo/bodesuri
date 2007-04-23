package PD.Spielerverwaltung;

import spielplatz.Briefkasten;
import spielplatz.EndPunkt;

public class Spieler {
	private String name;
	public EndPunkt endpunkt;
	
	public Spieler(String name) {
		this.name = name;
	}

	public Spieler(Briefkasten client) {
		this.endpunkt = client;
		this.name = client.name;
	}

	public String getName() {
    	return name;
    }
	
	public String toString() {
		return "Spieler " + getName();
	}
}
