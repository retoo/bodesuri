package applikation.client.zugautomat.zustaende;

import java.util.Vector;

import pd.zugsystem.Bewegung;
import applikation.client.controller.Controller;
import applikation.client.events.FeldAbgewaehltEvent;
import applikation.client.events.FeldGewaehltEvent;
import applikation.client.events.HoverEndeEvent;
import applikation.client.events.HoverStartEvent;
import applikation.client.events.KarteGewaehltEvent;
import applikation.client.pd.Karte;
import applikation.client.zugautomat.pd.SpielDaten;
import dienste.automat.zustaende.Zustand;
import dienste.eventqueue.Event;

public class ClientZugZustand extends Zustand {
	protected SpielDaten spielDaten;
	protected Controller controller;

	public Class<? extends Zustand> handle(Event event) {
		if (event instanceof KarteGewaehltEvent)
			return karteGewaehlt((KarteGewaehltEvent) event);
		else if (event instanceof FeldGewaehltEvent)
			return feldGewaehlt((FeldGewaehltEvent) event);
		else if (event instanceof FeldAbgewaehltEvent)
			return feldAbgewaehlt((FeldAbgewaehltEvent) event);
		else if (event instanceof HoverStartEvent)
			return hoverStart((HoverStartEvent) event);
		else if (event instanceof HoverEndeEvent)
			return hoverEnde((HoverEndeEvent) event);

		return super.handle(event);
	}

	Class<? extends Zustand> hoverStart(HoverStartEvent event) {
		return ignoriereEvent();
	}

	Class<? extends Zustand> hoverEnde(HoverEndeEvent event) {
		return ignoriereEvent();
	}

	Class<? extends Zustand> karteGewaehlt(KarteGewaehltEvent event) {
		return ignoriereEvent("karteGewaehlt");
	}

	Class<? extends Zustand> feldGewaehlt(FeldGewaehltEvent event) {
		return ignoriereEvent("feldGewaehlt");
	}

	Class<? extends Zustand> feldAbgewaehlt(FeldAbgewaehltEvent event) {
		return ignoriereEvent("feldAbgewaehlt");
	}

	/**
	 * Eine Karte als ausgewählt markieren.
	 * 
	 * @param karte
	 */
	protected void karteAuswaehlen(Karte karte) {
		if (spielDaten.karte != null && spielDaten.karte.istAusgewaehlt()) {
			spielDaten.karte.setAusgewaehlt(false);
		}
		spielDaten.karte = karte;
		spielDaten.karte.setAusgewaehlt(true);
	}

	/**
	 * Start und Ziel entselektieren & löschen.
	 */
	protected void brettZuruecksetzen() {
		if (spielDaten.start != null) {
			spielDaten.start.setAusgewaehlt(false);
			spielDaten.start = null;
		}
		if (spielDaten.ziel != null) {
			spielDaten.ziel.setAusgewaehlt(false);
			spielDaten.ziel = null;
		}
	}

	/**
	 * Den Vektor mit den erfassten Bewegungen löschen.
	 */
	protected void bewegungenZuruecksetzen() {
		spielDaten.bewegungen = new Vector<Bewegung>();
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public void setspielDaten(SpielDaten spielDaten) {
		this.spielDaten = spielDaten;
	}
}
