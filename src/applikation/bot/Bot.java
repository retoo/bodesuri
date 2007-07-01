package applikation.bot;

import java.util.IdentityHashMap;
import java.util.List;

import pd.karten.Karte;
import pd.regelsystem.ZugEingabe;
import applikation.client.events.ZugErfasstEvent;
import applikation.client.pd.Spiel;

public interface Bot {
	public ZugErfasstEvent macheZug(Spiel spiel, List<ZugEingabe> moeglich, IdentityHashMap<Karte, applikation.client.pd.Karte> karten);
}
