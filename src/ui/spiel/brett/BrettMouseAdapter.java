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


package ui.spiel.brett;

import java.awt.event.MouseEvent;
import ui.geteiltes.ClickMouseAdapter;
import applikation.client.controller.Steuerung;

/**
 * Erweiterung des ClickMouseAdapter. Beim klicken auf das Spielbrett wird das
 * jeweilige Feld abgewählt, das man davor ausgewählt hatte.
 */
public class BrettMouseAdapter extends ClickMouseAdapter {
	private Steuerung steuerung;

	public BrettMouseAdapter(Steuerung steuerung) {
		this.steuerung = steuerung;
	}

	public void clicked(MouseEvent e) {
		steuerung.feldAbwaehlen();
	}
}
