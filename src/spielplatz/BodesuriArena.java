package spielplatz;

import java.util.Vector;

import applikation.server.ServerAutomat;

public class BodesuriArena {

	public static Vector<String> nicks;

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		nicks = new Vector<String>();

		nicks.add("Anna Navarre ");
		nicks.add("JC Denton");
		nicks.add("Joseph Manderley");
		nicks.add("Walton Simons");

		Thread server = new Thread(new Runnable() {
			public void run() {
				ServerAutomat server = new ServerAutomat(4);
				try {
					server.run();
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Server: Exception in run()");
					System.exit(99);
				}
            }
		});

		server.start();

		Vector<Thread> clients = new Vector<Thread>();

		for (int i = 0; i < 4; i++) {
			Runnable r = new Botsuri(nicks.get(i), "localhost", 7788, Stupidbot.class, true);

			Thread t = new Thread(r);
			t.start();
			clients.add(t);
		}

		System.out.println("Warte auf Ende ...");
		server.join();
		System.out.println("Server fertig");

		for (Thread t : clients) {
			t.join();
			System.out.println("Client fertig");
		}

		System.out.println("Alles fertig");
	}
}
