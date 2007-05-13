package pd.brett;

import pd.spieler.Figur;
import pd.spieler.Spieler;
import dienste.serialisierung.CodierbaresObjekt;

public abstract class Feld extends CodierbaresObjekt {
	protected Feld naechstes;
	protected Feld vorheriges;
	
	protected Figur figur;
	
	public Feld(int nummer) {
		super("Feld " + nummer);
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
