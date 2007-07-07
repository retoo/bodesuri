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


package ui.spiel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import pd.regelsystem.karten.Deck;
import pd.regelsystem.karten.KartenFarbe;

import ui.geteiltes.ClickMouseAdapter;
import ui.ressourcen.BrettXML;
import ui.ressourcen.Icons;
import ui.spiel.steuerung.KarteMouseAdapter;
import ui.spiel.steuerung.KarteView;
import ui.spiel.steuerung.KartenAuswahl;
import applikation.client.controller.Steuerung;
import applikation.client.pd.Joker;
import applikation.client.pd.Karte;

/**
 * JPanel, es dient zur Darstellung des JokerViews, welches alle Karten
 * darstellt.
 */
public class JokerView extends JPanel {
	private Vector<Karte> kartenDeck;

	private BrettXML brettXML;

	public JokerView(Steuerung steuerung) {
		setLayout(new BorderLayout());
		setBackground(new Color(0, 0, 0, 100));
		setOpaque(false);

		JPanel kartenPanel = new JPanel();
		kartenPanel.setLayout(null);
		kartenPanel.setMinimumSize(new Dimension(490, 430));
		kartenPanel.setPreferredSize(new Dimension(490, 430));
		kartenPanel.setMaximumSize(new Dimension(490, 430));
		kartenPanel.setOpaque(false);

		erstelleDeck();

		try {
			brettXML = new BrettXML("/ui/ressourcen/brett.xml");
		} catch (Exception e) {
			// Checked Exception in unchecked umwandeln
			throw new RuntimeException(e);
		}

		KarteMouseAdapter kma = new KarteMouseAdapter(steuerung);
		KartenAuswahl ka = new KartenAuswahl();

		Vector<KarteView> karteViews = new Vector<KarteView>();
		for (int i = 0; i < kartenDeck.size(); i++) {
			KarteView kv = new KarteView(brettXML.getJokerKarten().get(i), kma,
					ka);
			karteViews.add(kv);
			kv.setKarte(kartenDeck.get(i));
			kartenPanel.add(kv);
		}

		JLabel jokerSchliessen = new JLabel();
		jokerSchliessen.setIcon(Icons.JOKERSCHLIESSEN);
		Point pos = brettXML.getJokerKarten().get(13);
		jokerSchliessen.setBounds(pos.x, pos.y, Icons.JOKERSCHLIESSEN
				.getIconWidth(), Icons.JOKERSCHLIESSEN.getIconHeight());
		jokerSchliessen.addMouseListener(new ClickMouseAdapter() {
			public void clicked(MouseEvent e) {
				setVisible(false);
			}
		});
		kartenPanel.add(jokerSchliessen);

		JPanel hintergrund = new JPanel();
		hintergrund.setLayout(new BoxLayout(hintergrund, BoxLayout.PAGE_AXIS));
		hintergrund.setBackground(new Color(0, 0, 0, 178));
		hintergrund.setOpaque(true);
		kartenPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		kartenPanel.setAlignmentY(Component.CENTER_ALIGNMENT);

		hintergrund.add(Box.createVerticalGlue());
		hintergrund.add(kartenPanel);
		hintergrund.add(Box.createVerticalGlue());
		add(hintergrund, BorderLayout.CENTER);

		addMouseListener(new MouseAdapter() {
		});
	}

	/**
	 * Dient zur erstellung des Decks, welches in der JockerView benötigt wird.
	 */
	public void erstelleDeck() {
		kartenDeck = new Vector<applikation.client.pd.Karte>();
		List<pd.regelsystem.Karte> karten = Deck
				.getKartenFuerFarbe(KartenFarbe.Herz);
		for (pd.regelsystem.Karte karte : karten) {
			kartenDeck.add(new Joker(new Karte(karte)));
		}
	}
}
