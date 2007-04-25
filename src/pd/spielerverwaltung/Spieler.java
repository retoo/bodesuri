package pd.spielerverwaltung;

import java.util.Vector;

import pd.zugsystem.Figur;
import spielplatz.Briefkasten;

public class Spieler {
	private String name;
	public Briefkasten endpunkt;
	
	private Vector<Figur> figuren = new Vector<Figur>();
	
	public Spieler(String name) {
		this.name = name;
		for (int i = 0; i < 4; ++i) {
			figuren.add(new Figur(this));
		}
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

	public Vector<Figur> getFiguren() {
    	return figuren;
    }
}
