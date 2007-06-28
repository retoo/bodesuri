package applikation.client.pd;

import java.text.DateFormat;
import java.util.Date;

import applikation.nachrichten.AufgabeInformation;

import pd.zugsystem.ZugEingabe;

import dienste.observer.ObservableList;

/**
 * Der Chat. Text wird in einer ObserveableList gespeichert.
 */
public class Chat extends ObservableList<String> {
	private DateFormat dateFormat = DateFormat.getTimeInstance();
	
	public void neueNachricht(String absender, String nachricht) {
		add(getZeit() + ": " + absender + "> " + nachricht);
	}
	
	public void meldeZug(ZugEingabe zug) {
		add(getZeit() + ": " + zug.getSpieler() + " spielt " + zug.getKarte());
	}
	
	public void meldeAufgabe(AufgabeInformation aufgabe) {
		add(getZeit() + ": " + aufgabe.spieler + " gibt auf.");
	}
	
	public void meldeRundenStart() {
		add(getZeit() + ": Rundenstart");
	}

	private final String getZeit() {
		String time = dateFormat.format(new Date());
		return time;
	}

	public String getLetzteZeile() {
	    return this.get(size() - 1);
    }
}
