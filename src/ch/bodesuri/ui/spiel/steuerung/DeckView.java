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


package ch.bodesuri.ui.spiel.steuerung;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.JPanel;

import ch.bodesuri.applikation.client.controller.Steuerung;
import ch.bodesuri.applikation.client.pd.Karte;
import ch.bodesuri.applikation.client.pd.Karten;
import ch.bodesuri.dienste.observer.ListChangeEvent;
import ch.bodesuri.dienste.observer.ListChangeEvent.ListChangeType;


/**
 * JPanel, das DeckView wird zur Darstellung der Karten verwendet. Hier werden
 * die Karten der Spieler verwaltet und dargestellt.
 */
public class DeckView extends JPanel implements Observer {
	private Karten karten;
	private Vector<KarteView> karteViews;
	private KarteMouseAdapter karteMouseAdapter;

	public DeckView(Steuerung steuerung, Karten karten) {
		this.karten = karten;

		setLayout(null);
		setOpaque(false);

		Dimension groesse = new Dimension(190, 340);
		setPreferredSize(groesse);
		setMaximumSize(groesse);
		setMinimumSize(groesse);

		KartenAuswahl kartenAuswahl = new KartenAuswahl();
		kartenAuswahl.setVisible(false);
		add(kartenAuswahl);
		
		karteMouseAdapter =	new KarteMouseAdapter(steuerung);

		karteViews = new Vector<KarteView>();
		for (int i = 0; i < 6; ++i) {
			int x = i % 2;
			int y = i / 2;
			Point position = new Point(10 + x*90, 10 + y*110);
			KarteView kv = new KarteView(position, karteMouseAdapter, kartenAuswahl);
			karteViews.add(kv);
			add(kv);
		}

		karten.addObserver(this);
	}

	public void update(Observable o, Object arg) {
		if (arg instanceof ListChangeEvent) {
			ListChangeEvent change = (ListChangeEvent) arg;
			if (change.changeType == ListChangeType.ADDED) {
				for (KarteView kv : karteViews) {
					if (kv.getKarte() == null) {
						kv.setKarte((Karte) change.changedObject);
						break;
					}
				}
			} else if (change.changeType == ListChangeType.REMOVED) {
				for (KarteView kv : karteViews) {
					if (kv.getKarte() == change.changedObject) {
						kv.setKarte(null);
						break;
					}
				}
			} else if (change.changeType == ListChangeType.EVERYTHING) {
				for (KarteView karteView : karteViews) {
					karteView.setKarte(null);
				}
				for (int i = 0; i < karten.size(); ++i) {
					karteViews.get(i).setKarte(karten.get(i));
				}
			}
		}
    }
}
