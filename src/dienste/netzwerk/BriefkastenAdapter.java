package dienste.netzwerk;

import dienste.automat.EventQueue;

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
