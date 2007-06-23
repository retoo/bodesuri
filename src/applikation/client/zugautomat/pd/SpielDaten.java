package applikation.client.zugautomat.pd;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import pd.zugsystem.Weg;
import applikation.client.pd.Brett;
import applikation.client.pd.Feld;
import applikation.client.pd.Karte;
import applikation.client.pd.Spiel;
import dienste.netzwerk.EndPunktInterface;

public class SpielDaten {
	public EndPunktInterface endpunkt;

	public Karte karte;
	/**
	 * Wenn der Joker ausgewählt ist, wird hier die Karte für die der Joker
	 * steht gespeichert.
	 */
	public Karte konkreteKarte;
	public Spiel spiel;

	public LinkedList<Bewegung> bewegungen;

	private Weg aktuellerWeg;

	public SpielDaten() {
		bewegungen = new LinkedList<Bewegung>();
		bewegungen.addLast(new Bewegung());
	}

	public boolean neueBewegungHinzufuegen() {
		if (bewegungen.getLast().getStart() != null && bewegungen.getLast().getZiel() != null) {
			bewegungen.addLast(new Bewegung());
			return true;
		} else {
			return false;
		}
	}

	public Feld getStart() {
    	return bewegungen.getLast().getStart();
    }

	public void setStart(Feld start) {
		deaktivereAuswahl(getStart());
		bewegungen.getLast().setStart(start);
		aktivereAuswahl(getStart());
    }


	public Feld getZiel() {
    	return bewegungen.getLast().getZiel();
    }

	public void setZiel(Feld ziel) {
		deaktivereAuswahl(getZiel());
		bewegungen.getLast().setZiel(ziel);
		aktivereAuswahl(getZiel());
    }

	private void aktivereAuswahl(Feld feld) {
		if (feld != null)
			feld.setAusgewaehlt(true);
	}

	private void deaktivereAuswahl(Feld feld) {
		if (feld != null)
			feld.setAusgewaehlt(false);
	}

	public void felderDeaktivieren() {
		deaktivereAuswahl(getStart());
		deaktivereAuswahl(getZiel());
	}

	public List<Feld> getAlleFelder() {
		List<Feld> felder = new Vector<Feld>();
		for (Bewegung bewegung : bewegungen) {
			if (bewegung.getStart() != null) {
				felder.add(bewegung.getStart());
			}
			if (bewegung.getZiel() != null) {
				felder.add(bewegung.getZiel());
			}
		}
		return felder;
	}

	public List<pd.zugsystem.Bewegung> getPdBewegungen() {
		List<pd.zugsystem.Bewegung> pdBewegungen = new Vector<pd.zugsystem.Bewegung>();
		for (Bewegung bewegung : bewegungen) {
			pdBewegungen.add(bewegung.toBewegung());
		}
		return pdBewegungen;
	}

	/* TODO: Reto: eigene klasse schreiben (-reto)*/
    public Weg getAktuellerWeg() {
    	return aktuellerWeg;
    }

    public void setAktuellerWeg(Weg neuerWeg) {
    	if (aktuellerWeg != null)
    		unmarkiere(aktuellerWeg);

    	this.aktuellerWeg = neuerWeg;

    	if (aktuellerWeg != null)
    		markiere(aktuellerWeg);
    }

	private void markiere(Weg aktuellerWeg) {
		Brett brett = spiel.getBrett();

		for (pd.brett.Feld f : aktuellerWeg) {
			brett.getFeld(f).setWeg(true);
		}
    }

	private void unmarkiere(Weg aktuellerWeg) {
		Brett brett = spiel.getBrett();

		for (pd.brett.Feld f : aktuellerWeg) {
			brett.getFeld(f).setWeg(false);
		}
    }
}
