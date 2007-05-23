package dienste.automat;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;



public class EventQueue implements EventQuelle {
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

	public Event getEvent() {
	    return dequeue();
    }
}
