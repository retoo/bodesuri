package applikation.client.pd;

import java.text.DateFormat;
import java.util.Date;

import applikation.nachrichten.AufgabeInformation;

import pd.zugsystem.ZugEingabe;

import dienste.observer.ObservableList;

public class Chat extends ObservableList<String> {
	private static DateFormat dateFormat = DateFormat.getTimeInstance();

	private final static String getZeit() {
		String time = dateFormat.format(new Date());
		return time;
	}

	public String getLetzteZeile() {
	    return this.get(size() - 1);
    }

	public void neueNachricht(String absender, String nachricht) {
		add(getZeit() + ": " + absender + "> " + nachricht);
    }

	public void meldeZug(ZugEingabe zug) {
		add(getZeit() + ": " + zug.getSpieler() + " spielt " + zug.getKarte());
    }

	public void meldeAufgabe(AufgabeInformation aufgabe) {
		add(getZeit() + ": " + aufgabe.spieler + " kann nicht mehr ziehen");
    }

	public void meldeRundenStart() {
		System.out.println("Melde Rundenstart");
		add(getZeit() + ": Rundenstart");
	}
}
