package pd.spielerverwaltung;

import java.util.Vector;

import pd.zugsystem.Figur;
import spielplatz.EndPunkt;

public class Spieler {
	private String name;
	public EndPunkt endpunkt;
	public int nummer;
	
	private Vector<Figur> figuren = new Vector<Figur>();
	
	public Spieler(String name) {
		this.name = name;
		for (int i = 0; i < 4; ++i) {
			figuren.add(new Figur(this));
		}
	}

	public Spieler(EndPunkt client, String name) {
		this(name);
		this.endpunkt = client;
	}
	
	public Spieler(String name, int nummer){
		this.name = name;
		this.nummer = nummer;
		for (int i = 0; i < 4; ++i) {
			figuren.add(new Figur(this));
		}
	}

	public String getName() {
    	return name;
    }
	
	public int getNummer(){
		return nummer;
	}
	
	public String toString() {
		return "Spieler " + getName();
	}

	// Wird für das CLI verwendet, um die Spieler anzeigen zu können
	public Vector<Figur> getFiguren() {
    	return figuren;
    }
}
