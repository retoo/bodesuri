package applikation.bot;

import java.util.IdentityHashMap;
import java.util.List;

import pd.karten.Karte;
import pd.zugsystem.Bewegung;
import pd.zugsystem.ZugEingabe;
import applikation.client.events.ZugErfasstEvent;
import applikation.client.pd.Spiel;

public class Stupidbot implements Bot {
	public ZugErfasstEvent macheZug(
					Spiel spiel,
					List<ZugEingabe> moeglicheZuege,
					IdentityHashMap<Karte, applikation.client.pd.Karte> kartenRegister) {
		if (moeglicheZuege.isEmpty()) {
			System.out.println("nix möglich");
			return null;
		} else {
			System.out.println("möglich sind: " + moeglicheZuege.size());

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
