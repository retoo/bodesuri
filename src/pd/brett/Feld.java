package pd.brett;

import java.util.Vector;

import dienste.serialisierung.CodierbaresObjekt;

import pd.spieler.Spieler;
import pd.zugsystem.Figur;

public abstract class Feld extends CodierbaresObjekt {
	protected Feld naechstes;
	protected Feld vorheriges;
	
	protected Figur figur;
	
	private int nummer;
	
	public Feld(int nummer) {
		this.nummer = nummer;
	}
	
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
	
	public Feld getNtesFeld(int n) {
		Feld f = this;
		while (n-- > 0)
			f = f.naechstes;
		
		return f;
	}
	
	public void versetzeFigurAuf(Feld ziel) {
		ziel.setFigur(getFigur());
		setFigur(null);
	}
	
	public boolean istBesetzt() {
	    return getFigur() != null;
    }
	
	public boolean istBesetztVon(Spieler spieler) {
		return istBesetzt() && getFigur().getSpieler() == spieler;
	}
	
    public String getCode() {
	    return "Feld " + nummer;
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
