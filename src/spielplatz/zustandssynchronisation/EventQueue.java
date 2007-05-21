package spielplatz.zustandssynchronisation;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import spielplatz.zustandssynchronisation.events.Event;

public class EventQueue implements EventSource {
	BlockingQueue<Event> queue;
	
	public EventQueue() {
		queue = new LinkedBlockingQueue<Event>();
	}
	
	public void enqueue(Event event) {
		try {
			queue.put(event);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public Event dequeue() {
		try {
	        return queue.take();
        } catch (InterruptedException e) {
    		throw new RuntimeException(e);
        }
	}

	public Event getEevent() {
	    return dequeue();
    }
}
