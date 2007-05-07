package pd.spielerverwaltung;

import java.util.Vector;


import dienste.netzwerk.EndPunkt;
import dienste.serialisierung.CodierbaresObjekt;
import pd.zugsystem.Figur;

public class Spieler extends CodierbaresObjekt {
	private static final long serialVersionUID = 1L;
	
	private int nummer;
	
	private String name;
	public EndPunkt endpunkt;
	
	private Vector<Figur> figuren = new Vector<Figur>();
	
	public Spieler(int nummer) {
		this.nummer = nummer;
		for (int i = 0; i < 4; ++i) {
			figuren.add(new Figur(this));
		}
	}
	
	public Spieler(int nummer, String name) {
		this(nummer);
		this.name = name;
	}

	public Spieler(int nummer, EndPunkt client, String name) {
		this(nummer, name);
		this.endpunkt = client;
	}

	public Spieler(EndPunkt absender, String string) {
	    // TODO Auto-generated constructor stub
    }

	public String toString() {
		return "Spieler " + getName();
	}

    public String getCode() {
	    return "Spieler " + nummer;
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
}
