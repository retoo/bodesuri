package spielplatz;

import java.util.Vector;

import applikation.server.ServerAutomat;

public class BodesuriArena {

	public static Vector<String> nicks;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
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

		for (int i = 0; i < 4; i++) {

			Runnable r = new TestClient(nicks.get(i));

			Thread t = new Thread(r);
			t.start();
		}
	}
}
