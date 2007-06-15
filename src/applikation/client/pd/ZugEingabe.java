package applikation.client.pd;

import pd.regelsystem.RegelVerstoss;
import pd.zugsystem.Bewegung;

// TODO: Muss das hier sein?
public class ZugEingabe {
	private pd.zugsystem.ZugEingabe zugEingabe;
	
	Karte karte;

	public ZugEingabe(Spieler spieler, Karte karte, Bewegung bewegung) {
		zugEingabe = new pd.zugsystem.ZugEingabe(spieler.getSpieler(),
		                                         karte.getKarte(), bewegung);
		this.karte = karte;
	}

	public void validiere() throws RegelVerstoss {
		zugEingabe.validiere();
	}

	public pd.zugsystem.ZugEingabe getZugEingabe() {
	    return zugEingabe;
    }
	
	public Karte getKarte() {
		return karte;
	}
}
