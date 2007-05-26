package applikation.server.zustaende;

import applikation.server.Spieler;
import applikation.server.Spielerschaft;
import applikation.server.nachrichten.ZugAufforderung;
import applikation.server.nachrichten.ZugInformation;
import dienste.automat.zustaende.Zustand;
import dienste.netzwerk.EndPunkt;

public class SpieleSpiel extends AktiverServerZustand {
	
	Zustand zugInfo(EndPunkt absender, ZugInformation zugInfo) {
		Spielerschaft spielers = automat.spielerschaft;
		Spieler aktuellerSpieler = spielers.getAktuellerSpieler();
		
		if (absender != aktuellerSpieler.endpunkt) {
			spielers.broadcast("HAH.. huere michi, de " + absender
			          + " wott voll bschisse");
			throw new RuntimeException("beschiss von " + absender + " an "
			                           + aktuellerSpieler);
		}

		spielers.broadcast(zugInfo);

		System.out.println("Ausgeführter Zug: "
		                   + zugInfo.zug.getKarte() + " wurde von "
		                   + zugInfo.zug.getSpieler() + " gespielt");

		spielers.rotiereSpieler();
		
		Spieler naechsterSpieler = spielers.getAktuellerSpieler();
		
		spielers.broadcast("Nächster Spieler ist " + naechsterSpieler + ".");

		naechsterSpieler.endpunkt.sende(new ZugAufforderung());
        
		System.out.println("Nächster Spieler: " + aktuellerSpieler);
		
		return this;
	}
}