package applikation.bot;

import java.util.List;
import java.util.Map;

import pd.regelsystem.ZugEingabe;
import pd.regelsystem.karten.Karte;
import pd.zugsystem.Bewegung;
import applikation.client.events.ZugErfasstEvent;
import applikation.client.pd.Spiel;

/**
 * Ziemlich dummer Bot der zufällig aus einem der möglichen Züge auswählt.
 */
public class Stupidbot implements Bot {
	public ZugErfasstEvent macheZug(
					Spiel spiel,
					List<ZugEingabe> moeglicheZuege,
					Map<Karte, applikation.client.pd.Karte> kartenRegister) {
		if (moeglicheZuege.isEmpty()) {
			return null;
		} else {
			int rand = (int) Math.floor(Math.random() * moeglicheZuege.size());
			ZugEingabe ze = moeglicheZuege.get(rand);
			List<Bewegung> bewegungen = ze.getBewegungen();
			applikation.client.pd.Karte karte = kartenRegister.get(ze.getKarte());

			/* Spezialfall Joker:
			 * Der Bot erhält mit getMoeglicheZüge() eines Jokers alle möglichen
			 * Züge, da der Joker alle möglichen Regeln verodert hat. Daraus
			 * kann er aber nicht rückschliessen welche konkrete Karte er
			 * spielen soll.
			 * Da der Server beim Validieren des Jokers aber eh alle Regeln
			 * akzeptiert, ist dies kein Problem.*/
			ZugErfasstEvent zee = new ZugErfasstEvent(spiel.spielerIch, karte, karte, bewegungen);
			return zee;
		}
	}
}
