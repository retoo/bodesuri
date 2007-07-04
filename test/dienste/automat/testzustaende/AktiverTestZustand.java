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


package dienste.automat.testzustaende;

import dienste.automat.events.TestEventA;
import dienste.automat.events.TestEventB;
import dienste.automat.events.TestExitEvent;
import dienste.automat.zustaende.EndZustand;
import dienste.automat.zustaende.Zustand;
import dienste.eventqueue.Event;

public class AktiverTestZustand extends Zustand {
	public Class<? extends Zustand> handle(Event event) {

		if (event instanceof TestEventA)
			return a();
		else if (event instanceof TestEventB)
			return b();
		else if (event instanceof TestExitEvent) {
			return exitEvent((TestExitEvent) event);
		}

		return super.handle(event);
	}

	Class<? extends Zustand> exitEvent(TestExitEvent event) {
	    return EndZustand.class;
    }

	Class<? extends Zustand> a() {
		return keinUebergang();
	}

	Class<? extends Zustand> b() {
		return keinUebergang();
	}
}
