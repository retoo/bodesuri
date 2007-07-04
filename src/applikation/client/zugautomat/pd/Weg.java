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


package applikation.client.zugautomat.pd;

import applikation.client.pd.Brett;

/**
 * Decorator für die Klasse pd.zugsystem.Weg. Kümmert sicht um das Handling
 * der Weg-Markierung.
 */
public class Weg {
	private pd.zugsystem.Weg aktuellerWeg;
	private Brett brett;

    public Weg(Brett brett) {
    	this.brett = brett;
    }

	public pd.zugsystem.Weg getAktuellerWeg() {
    	return aktuellerWeg;
    }

    public void setAktuellerWeg(pd.zugsystem.Weg neuerWeg) {
    	if (aktuellerWeg != null)
    		unmarkiere(aktuellerWeg);

    	this.aktuellerWeg = neuerWeg;

    	if (aktuellerWeg != null)
    		markiere(aktuellerWeg);
    }

	private void markiere(pd.zugsystem.Weg aktuellerWeg) {
		for (pd.spiel.brett.Feld f : aktuellerWeg) {
			brett.getFeld(f).setWeg(true);
		}
    }

	private void unmarkiere(pd.zugsystem.Weg aktuellerWeg) {
		for (pd.spiel.brett.Feld f : aktuellerWeg) {
			brett.getFeld(f).setWeg(false);
		}
    }
}
