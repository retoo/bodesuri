package spielplatz;

import java.util.concurrent.LinkedBlockingQueue;

import spielplatz.hilfsklassen.Brief;

public class Briefkasten {
	LinkedBlockingQueue<Brief> briefablage = new LinkedBlockingQueue<Brief>();
	
	public void einwerfen(Brief nachricht) {
		try {
			briefablage.put(nachricht);
		} catch (InterruptedException e) {
			System.out.println("InterruptedException");
			e.printStackTrace();
			System.exit(123);
		}
	}
	
	public Brief getBrief() {
		return getBrief(true);
	}
	
	public Brief getBrief(boolean isBlocking) {
		Brief brief = null;
		try {
			brief = isBlocking 
				? briefablage.take()
				: briefablage.poll();
		} catch (InterruptedException e) {
			System.out.println("InterruptedException");
			e.printStackTrace();
			System.exit(123);
		}
		return brief;
	}
}
