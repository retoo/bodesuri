package applikation.client.zugautomat.pd;

import applikation.client.pd.Brett;

/**
 * Decorator für die Klasse pd.zugsystem.Weg. Kümmert sicht um das Handling
 * der Weg-Markierung.
 */
public class Weg {
	private pd.zugsystem.Weg aktuellerWeg;
	private Brett brett;

    public Weg(Brett brett) {
    	this.brett = brett;
    }

	public pd.zugsystem.Weg getAktuellerWeg() {
    	return aktuellerWeg;
    }

    public void setAktuellerWeg(pd.zugsystem.Weg neuerWeg) {
    	if (aktuellerWeg != null)
    		unmarkiere(aktuellerWeg);

    	this.aktuellerWeg = neuerWeg;

    	if (aktuellerWeg != null)
    		markiere(aktuellerWeg);
    }

	private void markiere(pd.zugsystem.Weg aktuellerWeg) {
		for (pd.spiel.brett.Feld f : aktuellerWeg) {
			brett.getFeld(f).setWeg(true);
		}
    }

	private void unmarkiere(pd.zugsystem.Weg aktuellerWeg) {
		for (pd.spiel.brett.Feld f : aktuellerWeg) {
			brett.getFeld(f).setWeg(false);
		}
    }
}
