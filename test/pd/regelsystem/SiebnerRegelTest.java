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


package pd.regelsystem;

import java.util.List;
import java.util.Vector;

import pd.regelsystem.SiebnerRegel;
import pd.regelsystem.verstoesse.RegelVerstoss;
import pd.spiel.brett.Feld;
import pd.zugsystem.Bewegung;
import pd.zugsystem.Zug;

/**
 * Testet die Funktionalität der Siebnerregel.
 */
public class SiebnerRegelTest extends RegelTestCase {
	private Feld[] start;
	private Feld[] ziel;
	
	protected void setUp() {
		super.setUp();
		regel = new SiebnerRegel();
		start = new Feld[7];
		ziel  = new Feld[7];
	}

	public void testArbeitetMitWeg() {
		assertTrue(regel.arbeitetMitWeg());
	}

	/**
	 * Ein aufteilbarer Zug darf nur immer vom gleichen Spieler 
	 * durchgeführt werden.
	 */
	public void testAndereMitMehrerenBewegungen() {
		start[0] = bank(0);
		ziel[0]  = start[0].getNtesFeld(3);
		lager(0).versetzeFigurAuf(start[0]);
		start[1] = ziel[0].getNaechstes();
		ziel[1]  = start[1].getNaechstes();
		lager(0, 1).versetzeFigurAuf(start[1]);
		sollteVerstossGeben(new VorwaertsRegel(4));
		sollteVerstossGeben(new TauschRegel());
		sollteVerstossGeben(new StartRegel());
	}
	
	/**
	 * Prüft, ob genau sieben Felder gefahren wird und ob
	 * Figur auch wirklich verschoben wurde.
	 * 
	 * @throws RegelVerstoss
	 */
	public void testSiebner() throws RegelVerstoss {
		start[0] = bank(0).getNaechstes();
		ziel[0]  = start[0].getNtesFeld(7);
		sollteVerstossGeben();
		
		lager(0).versetzeFigurAuf(start[0]);
		sollteValidieren();
		
		assertTrue(start[0].istFrei());
		assertTrue(ziel[0].istBesetztVon(spieler(0)));
	}
	
	/**
	 * Prüft, ob man den Zug auch auf die immer gleiche Figur aufteilen
	 * kann (was nicht Sinn macht, ausser aus Komfortgründen).
	 * 
	 * @throws RegelVerstoss
	 */
	public void testSiebnerUnsinnigAberGueltig() throws RegelVerstoss {
		start[0] = bank(0);
		ziel[0]  = start[0].getNtesFeld(2);
		start[1] = ziel[0];
		ziel[1]  = start[1].getNtesFeld(2);
		start[2] = ziel[1];
		ziel[2]  = start[2].getNtesFeld(3);
		lager(0).versetzeFigurAuf(start[0]);

		/*
		 * Nicht sollteValidieren verwenden, da dieser Zug nicht von
		 * getMoeglicheZuege zurückgegeben wird.
		 */
		ZugEingabe ze = new ZugEingabe(spieler, null, getBewegungen());
		Zug zug = regel.validiere(ze);
		zug.ausfuehren();

		assertTrue(start[0].istFrei());
		assertTrue(ziel[0].istFrei());
		assertTrue(start[1].istFrei());
		assertTrue(ziel[1].istFrei());
		assertTrue(start[2].istFrei());
		assertTrue(ziel[2].istBesetztVon(spieler(0)));
	}
	
	/**
	 * Testet, ob man mit der Siebnerregel auch nicht rückwärts fahren
	 * kann.
	 */
	public void testSiebnerRueckwaerts() {
		start[0] = bank(0).getNtesFeld(5);
		ziel[0]  = bank(0).getVorheriges().getVorheriges();
		lager(0).versetzeFigurAuf(start[0]);
		sollteVerstossGeben();
	}
	
	/**
	 * Prüft, ob eine Gegnerfigur, welche auf dem Weg eines Siebnerzuges
	 * liegt heimgeschickt wird und ob die heimgeschickte Figur auch wieder
	 * auf dem Lagerfeld ist.
	 * 
	 * @throws RegelVerstoss
	 */
	public void testSiebnerHeimSchicken() throws RegelVerstoss {
		start[0] = bank(0);
		ziel[0]  = start[0].getNtesFeld(7);
		lager(0).versetzeFigurAuf(start[0]);
		Feld gegnerFeld = start[0].getNtesFeld(3);
		lager(1).versetzeFigurAuf(gegnerFeld);
		sollteValidieren();
		
		assertTrue(start[0].istFrei());
		assertTrue(ziel[0].istBesetztVon(spieler(0)));
		assertTrue(gegnerFeld.istFrei());
		assertTrue(lager(1).istBesetztVon(spieler(1)));
	}
	
	/**
	 * Prüft, ob zwei Gegnerfiguren, welche auf dem Weg eines Siebnerzuges
	 * liegen heimgeschickt werden und ob sie auch wieder auf ihren Lagerfeldern
	 * sind.
	 * 
	 * @throws RegelVerstoss
	 */
	public void testSiebnerZweiHeimSchicken() throws RegelVerstoss {
		start[0] = bank(0);
		ziel[0]  = bank(0).getNtesFeld(7);
		lager(0).versetzeFigurAuf(start[0]);
		Feld gegner1 = bank(0).getNtesFeld(2);
		Feld gegner2 = bank(0).getNtesFeld(4);
		lager(1, 0).versetzeFigurAuf(gegner1);
		lager(1, 1).versetzeFigurAuf(gegner2);
		sollteValidieren();
		
		assertTrue(start[0].istFrei());
		assertTrue(ziel[0].istBesetztVon(spieler(0)));
		assertTrue(gegner1.istFrei());
		assertTrue(gegner2.istFrei());
		assertTrue(lager(1, 0).istBesetztVon(spieler(1)));
		assertTrue(lager(1, 1).istBesetztVon(spieler(1)));
	}
	
	/**
	 * Prüft, ob erkannt wird, wenn bei einem Siebnerzug zu wenige
	 * Schritte gemacht wurden.
	 */
	public void testSiebnerZuWenigSchritte() {
		sollteVerstossGeben();
		
		start[0] = bank(0).getNaechstes();
		ziel[0]  = start[0].getNtesFeld(6);
		lager(0).versetzeFigurAuf(start[0]);
		sollteVerstossGeben();
		
		ziel[0]  = start[0].getNtesFeld(3);
		start[1] = ziel[0].getNtesFeld(3);
		ziel[1]  = start[1].getNtesFeld(1);
		lager(0, 1).versetzeFigurAuf(start[1]);
		sollteVerstossGeben();
	}
	
	/**
	 * Prüft, ob erkannt wird, wenn bei einem Siebnerzug zu viele
	 * Schritte gemacht wurden.
	 */
	public void testSiebnerZuVielSchritte() {
		start[0] = bank(0);
		ziel[0]  = start[0].getNtesFeld(10);
		sollteVerstossGeben();
		
		start[1] = ziel[0].getNaechstes();
		ziel[1]  = start[1].getNtesFeld(3);
		lager(0, 1).versetzeFigurAuf(start[1]);
		sollteVerstossGeben();
	}
	
	/**
	 * Prüft, ob bei einem Verstoss gegen die Siebnerregel auch 
	 * berücksichtigt wird, wenn mehrere Teilzüge ungültig sind.
	 * 
	 * @throws RegelVerstoss
	 */
	public void testSiebnerMehrfach() throws RegelVerstoss {
		start[0] = bank(0).getNaechstes();
		ziel[0]  = start[0].getNtesFeld(3);
		start[1] = ziel[0].getNaechstes();
		ziel[1]  = start[1].getNtesFeld(4);
		sollteVerstossGeben();
		
		lager(0).versetzeFigurAuf(start[0]);
		sollteVerstossGeben();
		
		lager(0, 1).versetzeFigurAuf(start[1]);
		sollteValidieren();
		
		assertTrue(start[0].istFrei());
		assertTrue(ziel[0].istBesetztVon(spieler(0)));
		assertTrue(start[1].istFrei());
		assertTrue(ziel[1].istBesetztVon(spieler(0)));
	}
	
	/**
	 * Prüft, ob ein Mehrfachzug auch wirklich einen Verstoss gibt, der
	 * im ersten Teilzug die Figur frisst, welche im zweiten Teilzug 
	 * gezogen wird.
	 */
	public void testSiebnerMehrfachStartFigurGefressen() {
		start[0] = bank(0);
		ziel[0]  = bank(0).getNtesFeld(6);
		lager(0).versetzeFigurAuf(start[0]);
		start[1] = bank(0).getNaechstes();
		ziel[1]  = start[1].getNaechstes();
		lager(0, 1).versetzeFigurAuf(start[1]);
		sollteVerstossGeben();
	}
	
	/**
	 * Prüft, ob mit einer Siebnerregel auch nicht über ein geschütztes 
	 * Bankfeld gefahren werden kann.
	 * 
	 * @throws RegelVerstoss
	 */
	public void testSiebnerUeberGeschuetztes() throws RegelVerstoss {
		start[0] = lager(0);
		ziel[0]  = bank(0);
		sollteValidieren(new StartRegel());
		
		assertTrue(start[0].istFrei());
		assertTrue(ziel[0].istBesetztVon(spieler(0)));
		
		start[0] = bank(0).getVorheriges();
		ziel[0]  = start[0].getNtesFeld(7);
		lager(0, 1).versetzeFigurAuf(start[0]);
		sollteVerstossGeben();
	}
	
	/**
	 * Prüft, ob mit der Siebnerregel über ein ungeschütztes Bankfeld
	 * gezogen werden kann.
	 * 
	 * @throws RegelVerstoss
	 */
	public void testSiebnerUeberBankFeld() throws RegelVerstoss {
		start[0] = bank(0).getVorheriges();
		ziel[0]  = bank(0);
		lager(0).versetzeFigurAuf(start[0]);
		start[1] = bank(0).getVorheriges().getVorheriges();
		ziel[1]  = start[1].getNtesFeld(6);
		lager(0, 1).versetzeFigurAuf(start[1]);
		sollteValidieren();
		
		assertTrue(start[0].istFrei());
		assertTrue(ziel[0].istFrei());
		assertTrue(lager(0).istBesetztVon(spieler(0)));
		assertTrue(start[1].istFrei());
		assertTrue(ziel[1].istBesetztVon(spieler(0)));
	}
	
	/**
	 * Prüft, ob die Siebnerregel auf Figuren angewendet werden kann, welche
	 * vormals auf ihr Bankfeld gekommen waren und somit geschützt waren.
	 * 
	 * @throws RegelVerstoss
	 */
	public void testSiebnerMitGeschuetztem() throws RegelVerstoss {
		start[0] = bank(0);
		ziel[0]  = bank(0).getNtesFeld(5);
		lager(0).versetzeFigurAuf(start[0]);
		start[0].setGeschuetzt(true);
		start[1] = bank(0).getVorheriges();
		ziel[1]  = bank(0).getNaechstes();
		lager(0, 1).versetzeFigurAuf(start[1]);
		sollteValidieren();
		
		assertTrue(start[0].istFrei());
		assertTrue(ziel[0].istBesetztVon(spieler(0)));
		assertTrue(start[1].istFrei());
		assertTrue(ziel[1].istBesetztVon(spieler(0)));
	}
	
	/**
	 * Prüft eine schwierige Situation mit Figuren, die geschützt sind, es
	 * nach dem Siebnerzug jedoch nicht mehr sein werden.
	 * 
	 * @throws RegelVerstoss
	 */
	public void testSiebnerMitGeschuetztemVerzwickt() throws RegelVerstoss {
		start[0] = bank(0);
		ziel[0]  = bank(0).getNtesFeld(3);
		lager(0).versetzeFigurAuf(start[0]);
		start[0].setGeschuetzt(true);
		start[1] = bank(0).getVorheriges();
		ziel[1]  = bank(0);
		lager(0, 1).versetzeFigurAuf(start[1]);
		start[2] = bank(0).getVorheriges().getVorheriges();
		ziel[2]  = bank(0).getNaechstes();
		lager(0, 2).versetzeFigurAuf(start[2]);
		sollteValidieren();
	}
	
	/**
	 * Züge mit fremden Figuren sollten nicht möglich sein.
	 */
	public void testSiebnerMitFremdem() {
		start[0] = bank(0);
		ziel[0]  = bank(0).getNtesFeld(7);
		lager(1).versetzeFigurAuf(start[0]);
		sollteVerstossGeben();
	}
	
	/**
	 * Bewegungen, die keine Veränderungen mit sich bringen sollten die 
	 * Siebnerregel nicht tangieren.
	 * 
	 * @throws RegelVerstoss
	 */
	public void testSiebnerMitLeerenBewegungen() throws RegelVerstoss {
		start[0] = bank(0);
		ziel[0]  = bank(0).getNtesFeld(7);
		lager(0).versetzeFigurAuf(start[0]);
		start[1] = bank(0).getVorheriges();
		ziel[1]  = bank(0).getVorheriges();
		lager(0, 1).versetzeFigurAuf(start[1]);

		/*
		 * Nicht sollteValidieren verwenden, da dieser Zug nicht von
		 * getMoeglicheZuege zurückgegeben wird.
		 */
		ZugEingabe ze = new ZugEingabe(spieler, null, getBewegungen());
		regel.validiere(ze);
	}
	
	/**
	 * Prüft, ob in einer Grenzsituation die Siebnerregel die korrekte
	 * Antowrt auf die Frage liefert, ob ein Zug noch möglich ist.
	 */
	public void testSiebnerKannZiehen() {
		assertFalse(regel.istZugMoeglich(spieler(0)));
		
		lager(0, 0).versetzeFigurAuf(himmel(0, 0));
		lager(0, 1).versetzeFigurAuf(himmel(0, 1));
		lager(0, 2).versetzeFigurAuf(himmel(0, 2));
		lager(0, 3).versetzeFigurAuf(himmel(0, 3));
		assertFalse(regel.istZugMoeglich(spieler(0)));
		
		himmel(0, 0).versetzeFigurAuf(bank(0));
		assertTrue(regel.istZugMoeglich(spieler(0)));
	}
	
	// TODO: Robin Mehr und kompliziertere Tests schreiben, z. B. mit Himmel. --Robin
	
	/**
	 * @return Liefert eine Liste mit allen Bewegungen
	 */
	protected List<Bewegung> getBewegungen() {
		Vector<Bewegung> bewegungen = new Vector<Bewegung>();
		for (int i = 0; i < start.length; ++i) {
			if (start[i] == null) break;
			bewegungen.add(new Bewegung(start[i], ziel[i]));
		}
		return bewegungen;
	}
}
