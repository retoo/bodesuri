package pd.spieler;

import java.util.List;
import java.util.Vector;

import pd.Spiel;
import dienste.serialisierung.CodierbaresObjekt;

/**
 * Spieler, der bei einem {@link Spiel} mitspielt, und 4 {@link Figur Figuren}
 * hat.
 */
public class Spieler extends CodierbaresObjekt {
	private static final long serialVersionUID = 1L;
	
	private int nummer;
	private String name;
	
	private Spiel spiel;
	
	private Vector<Figur> figuren = new Vector<Figur>();
	
	/**
	 * Erstellt einen Spieler.
	 * 
	 * @param nummer Eindeutige Spielernummer
	 * @param spiel Spiel, bei dem der Spieler mitspielt
	 */
	public Spieler(int nummer, Spiel spiel) {
		super("Spieler " + nummer);
		this.nummer = nummer;
		this.spiel = spiel;
		for (int i = 0; i < 4; ++i) {
			figuren.add(new Figur(this));
		}
	}

	/**
	 * Sieht zum Beispiel so aus: "Spieler Zoë"
	 */
	public String toString() {
		return "Spieler " + getName();
	}

	/**
	 * @return Name des Spielers
	 */
	public String getName() {
    	return name;
    }

	/**
	 * @param name Name des Spielers
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Figuren, mit denen der Spieler spielt
	 */
	public List<Figur> getFiguren() {
    	return figuren;
    }

	/**
	 * @return Spielernummer
	 */
	public int getNummer() {
    	return nummer;
    }

	/**
	 * @return Spiel, bei dem der Spieler mitspielt
	 */
	public Spiel getSpiel() {
		return spiel;
	}
}
