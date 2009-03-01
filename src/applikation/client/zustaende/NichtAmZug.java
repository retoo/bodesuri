/*
 * Copyright (C) 2007  Danilo Couto, Philippe Eberli,
 *                     Pascal Hobus, Reto Schüttel, Robin Stocker
 *
 * This file is part of Bodesuri.
 *
 * Bodesuri is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.
 *
 * Bodesuri is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Bodesuri; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


package applikation.client.zustaende;


import intelliDOG.ai.utils.DebugMsg;
import pd.regelsystem.ZugEingabe;
import pd.regelsystem.verstoesse.RegelVerstoss;
import applikation.client.pd.Karte;
import applikation.client.pd.Spieler;
import applikation.nachrichten.AktuellerSpielerInformation;
import applikation.nachrichten.AufgabeInformation;
import applikation.nachrichten.RundenStart;
import applikation.nachrichten.SpielFertigNachricht;
import applikation.nachrichten.ZugAufforderung;
import dienste.automat.zustaende.Zustand;

/**
 * Zustand wenn der Spieler nicht am Zug ist.
 * <ul>
 * <li>Wenn eine {@link ZugAufforderung} eintrifft, wird der Zustand
 * {@link AmZug} aufgerufen.</li>
 * <li>Wenn eine {@link AktuellerSpielerInformation} eintrifft, wird der
 * aktuelle Spieler dargestellt, der Zustand aber nicht gewechselt.</li>
 * <li>Wenn eine {@link ZugEingabe} eintrifft, wird Zug aus der Eingabe
 * visualisiert. Der Zustand wird nicht gewechselt.</li>
 * <li>Wenn eine {@link RundenStart}-Nachricht eintrifft, werden die
 * erhaltenen Karten gespeichert und der Zustand {@link StarteRunde} aufgerufen.</li>
 * <li>Wenn eine {@link AufgabeInformation} eintrifft, wird der Spieler, welcher
 * aufgegeben hat, im UI angepasst. Der Zustand wird nicht gewechselt.</li>
 * <li>Wenn eine {@link SpielFertigNachricht} eintrifft, wird der Gewinner
 * angezeigt und {@link SpielEnde} aufgerufen.</li>
 * </ul>
 */
public class NichtAmZug extends ClientZustand {
	Class<? extends Zustand> zugAufforderung(ZugAufforderung zugAufforderung) {
		controller.zugAufforderung();
		return AmZug.class;
	}

	Class<? extends Zustand> aktuellerSpielerInformation(AktuellerSpielerInformation information) {
		Spieler neuerSpieler = spiel.findeSpieler(information.spieler);

		if (spiel.aktuellerSpieler != null) {
			spiel.aktuellerSpieler.setAmZug(false);
		}

		neuerSpieler.setAmZug(true);
		spiel.aktuellerSpieler = neuerSpieler;

		spiel.hinweis.neuerHinweis(neuerSpieler + " ist am Zug.", true, neuerSpieler.getFarbe());
		return this.getClass();
	}

	Class<? extends Zustand> zugWurdeGemacht(ZugEingabe zug) {
		spiel.hinweis.neuerHinweis(zug.getSpieler() + " spielt " + zug.getKarte(), false, zug.getSpieler().getFarbe());

		try {
			zug.validiere().ausfuehren();
		} catch (RegelVerstoss e) {
			controller.zeigeFehlermeldung("Ungültigen Zug (" + e
			                              + ") vom Server erhalten!");
			return SchwererFehler.class;
		}

		return this.getClass();
	}

	Class<? extends Zustand> rundenStart(RundenStart rundenStart) {
		spiel.hinweis.neuerHinweis("Start einer neuen Runde", false, null);
		spiel.spielerIch.getKarten().clear();
		for (pd.regelsystem.Karte karte : rundenStart.neueKarten) {
			spiel.spielerIch.getKarten().add(new Karte(karte));
		}

		return StarteRunde.class;
	}

	Class<? extends Zustand> aufgabe(AufgabeInformation aufgabe) {
		Spieler spieler = spiel.findeSpieler(aufgabe.spieler);
		spiel.hinweis.neuerHinweis(aufgabe.spieler + " gibt auf.", false, spieler.getFarbe());
		spieler.setHatAufgebeben(true);
		return this.getClass();
	}

	Class<? extends Zustand> spielFertig(SpielFertigNachricht nachricht) {
//		System.out.println("hallo ich bin fertisch");
		controller.zeigeMeldung("Das Spiel ist fertig, gewonnen haben "  + nachricht.gewinner);
		DebugMsg.getInstance().log2file(this, "gewonnen haben" + nachricht.gewinner.toString());

		return SpielEnde.class;
	}
}
