package pd.zugsystem;

import java.util.Vector;

public abstract class Feld {
	protected Feld naechstes;
	protected Feld vorheriges;
	
	protected Figur figur;
	
	/*
	 * TODO: Durchdenken wegen Endlosschleife.
	 */
	public Vector<Feld> getWeg(Feld ziel) {
		Vector<Feld> weg = new Vector<Feld>();
		Feld feld = this;
		while (feld != ziel) {
			weg.add(feld);
			feld = feld.getNaechstes();
		}
		weg.add(feld);
		return weg;
	}
	
	public Vector<Feld> getWegRueckwaerts(Feld ziel) {
		Vector<Feld> weg = new Vector<Feld>();
		Feld feld = this;
		while (feld != ziel) {
			weg.add(feld);
			feld = feld.getVorheriges();
		}
		weg.add(feld);
		return weg;
	}
	
	/*
	 * TODO: Implementieren. Auch für LagerFeld, etc.
	 */
	public Vector<Feld> getWeg(HimmelFeld ziel) {
		return null;
	}

	public Feld getNaechstes() {
    	return naechstes;
    }

	public void setNaechstes(Feld naechstesFeld) {
    	this.naechstes = naechstesFeld;
    }

	public Feld getVorheriges() {
    	return vorheriges;
    }

	public void setVorheriges(Feld vorherigesFeld) {
    	this.vorheriges = vorherigesFeld;
    }

	public Figur getFigur() {
    	return figur;
    }

	public void setFigur(Figur figur) {
    	this.figur = figur;
    }
}
