package pd.zugsystem;

import java.io.Serializable;

import pd.brett.BankFeld;
import pd.brett.Brett;
import pd.brett.Feld;
import pd.brett.SpielerFeld;

/**
 * Hilfsklasse für eine Bewegung, die ein Start und ein Ziel hat.
 */
public class Bewegung implements Serializable {
	/** Start der Bewegung. */
	public final Feld start;
	/** Ziel der Bewegung. */
	public final Feld ziel;

	public Bewegung(Feld start, Feld ziel) {
		this.start = start;
		this.ziel = ziel;
	}

	public String toString() {
		return "von " + start + " nach " + ziel;
	}
	
	public Weg getWeg() {
		if ((start.istRing()   && ziel.istRing()) ||
		    (start.istRing()   && ziel.istHimmel()) ||
		    (start.istHimmel() && ziel.istHimmel()) ||
		    (start.istLager()  && ziel.istBank())) {
			// Alles gut
		} else {
			return null;
		}
		
		int startNr = start.getNummer();
		int zielNr  =  ziel.getNummer();
		int startBisZiel = (zielNr - startNr + Brett.ANZAHL_FELDER) % Brett.ANZAHL_FELDER;
		int zielBisStart = (startNr - zielNr + Brett.ANZAHL_FELDER) % Brett.ANZAHL_FELDER;
		
		Weg weg;
		if (startBisZiel <= zielBisStart) {
			// Vorwaerts
			weg = new Weg();
		} else {
			// Rückwärts
			weg = new Weg(true);
		}
		
		Feld feld = start;
		while (feld != ziel) {
			weg.add(feld);
			if (weg.istRueckwaerts()) {
				feld = feld.getVorheriges();
			} else {
				if (feld.istBank() && ziel.istHimmel() 
				    && ((SpielerFeld) feld).getSpieler() ==
				       ((SpielerFeld) ziel).getSpieler())
				{
					feld = ((BankFeld) feld).getHimmel();
				} else {
					feld = feld.getNaechstes();
				}
			}
		}
		weg.add(feld);
		
		return weg;
	}
}
