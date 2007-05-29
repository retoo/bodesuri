package applikation.client;

import applikation.server.BodesuriServer;

public class BodesuriEntwickler {

	public static void main(String[] args) {
		Thread server = new Thread(new Runnable() {
			public void run() {
				BodesuriServer server = new BodesuriServer();

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
			Thread client = new Thread(new Runnable() {
				public void run() {
					BodesuriClient client = new BodesuriClient(Thread.currentThread().getName());

					try {
						client.run();
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("Client: Exception in run()");
						System.exit(99);
					}
	            }
			}, "Spieler " + i); /* evil.. :P */

			client.start();

		}


	}

}
