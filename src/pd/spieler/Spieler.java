package pd.spieler;

import java.util.Vector;

import dienste.serialisierung.CodierbaresObjekt;

public class Spieler extends CodierbaresObjekt {
	private static final long serialVersionUID = 1L;
	
	private int nummer;
	
	private String name;
	
	private Vector<Figur> figuren = new Vector<Figur>();
	
	public Spieler(int nummer) {
		super("Spieler " + nummer);
		this.nummer = nummer;
		for (int i = 0; i < 4; ++i) {
			figuren.add(new Figur(this));
		}
	}
	
	public Spieler(int nummer, String name) {
		this(nummer);
		this.name = name;
	}

	public String toString() {
		return "Spieler " + getName();
	}

	public String getName() {
    	return name;
    }

	public void setName(String name) {
		this.name = name;
	}

	// Wird für das CLI verwendet, um die Spieler anzeigen zu können
	public Vector<Figur> getFiguren() {
    	return figuren;
    }

	public int getNummer() {
    	return nummer;
    }
}
