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


package dienste.netzwerk.server;

import dienste.eventqueue.EventQueue;
import dienste.netzwerk.Brief;
import dienste.netzwerk.BriefKastenInterface;


/**
 * Implementation des Interfaces {@link BriefKastenInterface} welcher den Einwruf
 * von Briefen in eine {@link EventQueue} ermöglicht.
 *
 * @see EventQueue
 *
 */
public class BriefkastenAdapter implements BriefKastenInterface {
	private EventQueue queue;

	/**
	 * Erstellt den BriefkastenAdapter
	 * @param queue zu adaptierende EventQueue
	 */
	public BriefkastenAdapter(EventQueue queue) {
		this.queue = queue;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see dienste.netzwerk.BriefKastenInterface#einwerfen(dienste.netzwerk.Brief)
	 */
	public void einwerfen(Brief brief) {
		/* Verpacke den Brief in einem NetzwerkEvent vor dem Einwurf */
		NetzwerkEvent event = new NetzwerkEvent(brief);
		queue.enqueue(event);
	}
}
