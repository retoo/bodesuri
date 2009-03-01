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


package ch.bodesuri.applikation.bot;

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import ch.bodesuri.applikation.client.events.ZugErfasstEvent;
import ch.bodesuri.applikation.client.pd.Spiel;
import ch.bodesuri.pd.karten.Bube;
import ch.bodesuri.pd.karten.Sieben;
import ch.bodesuri.pd.karten.Vier;
import ch.bodesuri.pd.regelsystem.Karte;
import ch.bodesuri.pd.regelsystem.ZugEingabe;
import ch.bodesuri.pd.spiel.brett.BankFeld;
import ch.bodesuri.pd.spiel.brett.Feld;
import ch.bodesuri.pd.spiel.spieler.Spieler;
import ch.bodesuri.pd.zugsystem.Bewegung;
import ch.bodesuri.pd.zugsystem.Weg;


public class IntelliBot implements Bot {

	/*  
	 * TODO: Pürfen ob amn hier anstatt die Zugeingaben irgendwie Karte und bewebungen übergeben könnte, 
	 * der lookup mit dem register ist ja ned wirklich cool
	 * 
	 * 
	 * TODO: Bot ignroert Partnerschaften in verschiedenen Situationen. 
	 * 
	 */
	public ZugErfasstEvent macheZug(Spiel spiel, List<ZugEingabe> moeglicheZuege,
			Map<Karte, ch.bodesuri.applikation.client.pd.Karte> kartenRegister) {
		if (moeglicheZuege.isEmpty()) {
			return null;
		} else {
			
			PriorityQueue<Variante> varianten = new PriorityQueue<Variante>();
			for (ZugEingabe ze : moeglicheZuege) {
				int score = 0;
				List<Bewegung> bewegungen = ze.getBewegungen();
				Karte karte = ze.getKarte();
				Spieler spieler = ze.getSpieler();
				Spieler partner = spieler.getPartner();

				
				for (Bewegung bewegung : bewegungen) {
					Feld ziel = bewegung.ziel;
					Feld start = bewegung.start;
					Weg weg = bewegung.getWeg();
					
					/* Himmel erreicht */
					if (ziel.istHimmel()) {
						score += 50;
					}
					
					/* Macht der Spieler eine extra runde? */ 
					if (start.istNormal() && ziel.istNormal()) {
						for (Feld feld : weg) {
							/* Ignore Ziel und Start */
							if (feld == start || feld == ziel)
								continue;

							if (feld.istBank()) {
								BankFeld bank = (BankFeld) feld;
								if (bank.getSpieler() == spieler) {
									score -= 50;
								}
							}
						}
					}
					
					/* Wir können einen Spielstein fressen, der nicht uns selber gehört oder unserem Partner */
					if (ziel.istBesetzt() && 
							!((ziel.istBesetztVon(spieler) || ziel.istBesetztVon(partner))) && 
							!(karte instanceof Bube)) {
						score += 50;
					}
				
					/* Fahren wir sinnlos rückwerts? */ 
					if (karte instanceof Vier && weg.istRueckwaerts()) {
						
						/* Grosse ausnahme: wir fahren in den ersten paar Zügen rückwerts */
						if (start.istBank() &&
							((BankFeld) start).getSpieler() == spieler) {
								score += 12;
						} else { 
							score -= 8;
						}
					}
				
					/* Siebner Zug mit viel Fressen ist wichtig */
					if (karte instanceof Sieben) {
						for (Feld feld : weg) {
							/* Ignore Ziel und Start */
							if (feld == start || feld == ziel)
								continue;
							
							if (feld.istBesetzt()) {
								
								/* Wer wird gefressen? wir selber oder jemand anders ? */
								if (feld.istBesetztVon(spieler) || feld.istBesetztVon(partner)) {
									score -= 5;
								} else {
									score += 5;
								}
									
							}	
						}
					}
					
					
					Variante v = new Variante(ze, score);
					
					varianten.add(v);					
				}
				
				
					

			}
//			int rand = (int) Math.floor(Math.random() * moeglicheZuege.size());
//			ZugEingabe ze = moeglicheZuege.get(rand);
//			List<Bewegung> bewegungen = ze.getBewegungen();
//			applikation.client.pd.Karte karte = kartenRegister.get(ze.getKarte());

			Variante best = varianten.peek();
			System.out.println(" - " + best.score + ": " + best.zugEingabe);

			ZugEingabe ze = best.zugEingabe; 
			
			
			List<Bewegung> bewegungen = ze.getBewegungen();
			ch.bodesuri.applikation.client.pd.Karte karte = kartenRegister.get(ze.getKarte());

			
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
