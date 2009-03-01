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


package ch.bodesuri.test.dienste.automat.testzustaende;

import ch.bodesuri.dienste.automat.zustaende.Zustand;

/**
 * Repräsentiert einen TestZustand(-Alpha) im TestAutomaten, der zusätzlich
 * Übergänge zu anderen TestZuständen definiert hat.
 */
public class TestZustandAlpha extends TestZustand {
	/**
	 * Übergang nach TestZustandAlpha, also sich selbst.
	 */
	protected Class<? extends Zustand> a() {
		return TestZustandAlpha.class;
    }

	/**
	 * Übergang nach TestZustandBeta.
	 */
	protected Class<? extends Zustand> b() {
		return TestZustandBeta.class;
    }
}
