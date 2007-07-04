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


package dienste.automat;
import dienste.automat.testzustaende.TestZustandAlpha;
import dienste.automat.testzustaende.TestZustandBeta;
import dienste.eventqueue.EventQueue;

/**
 * Dieser TestAutomat wird die Tests des Automaten gebraucht. Er
 * registriert bereits einige Zustände und setzt die Eventquellen.
 */
public class TestAutomat extends Automat {
	public EventQueue output;

	/**
	 * Erstellt einen neuen Testautomaten, registriert bei ihm
	 * einige Zustände, setzt den Startzustand die Eventquelle.
	 *
	 * @param input Eingabeevents
	 * @param output Ausgabeevents
	 */
	public TestAutomat(EventQueue input, EventQueue output) {
		super(false);

	    registriere(new TestZustandAlpha());
	    registriere(new TestZustandBeta());

	    setStart(TestZustandAlpha.class);

	    setEventQuelle(new EventQuelleAdapter(input));

	    this.output = output;
    }

	public String toString() {
	    return "Test-Automat";
	}
}