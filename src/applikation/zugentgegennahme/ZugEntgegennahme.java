/**
 * @(#) ZugEntgegennahme.java
 */

package applikation.zugentgegennahme;

import pd.brett.Feld;
import pd.spieler.Spieler;
import pd.zugsystem.Bewegung;
import pd.zugsystem.Zug;
import pd.zugsystem.ZugEingabe;

public class ZugEntgegennahme{
	
	public static Zug zugBestaetigen(Feld start, Feld ziel) {
		Bewegung bewegung = new Bewegung(start, ziel);
		ZugEingabe ze = new ZugEingabe(new Spieler(0, null), null, bewegung);	// FIXME: woher Spieler nehmen?
		//return regel.validiere(ze);
		return null;
	}
}
