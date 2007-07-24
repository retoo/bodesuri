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


package ui.geteiltes;

import java.awt.Color;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ui.ressourcen.Icons;
import applikation.client.pd.Spieler;

/**
 * JPanel mit den Namen der Spieler.
 */

public class SpielerView extends JPanel implements Observer {
	private JLabel name;
	private Spieler spieler;

	public SpielerView(Spieler spieler) {
		this.spieler = spieler;
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setOpaque(false);

		this.name = new JLabel(spieler.getSpieler().getName());
		name.setIcon(Icons.getSpielerIcon(spieler.getSpieler().getFarbe()));
		name.setFont(name.getFont().deriveFont(Font.BOLD));
		add(name);

		spieler.addObserver(this);
	}

	public void update(Observable o, Object arg) {
		name.setText(spieler.getName());

		if (spieler.amZug()) {
			this.name.setForeground(Color.WHITE);
		} else {
			this.name.setForeground(Color.BLACK);
		}
	}

	/**
	 * Sorge dafür, dass der Observer ausgetragen wird.
	 */
	public void removeNotify() {
		spieler.deleteObserver(this);
		super.removeNotify();
	}
}
