package dienste.threads;

/**
 * Eine Bodesuri-angepasste Impelementation der Thread Klasse.
 *
 * Der Thread kommt von Haus aus mit einem eigenen exceptionHandler welcher bei
 * Felern automatisch die Runtime-Umgebung beende. Dies ist notwendig da wir im
 * Fehlerfall nicht garantieren können dsa alle Threads beendet sind.
 * Unglücklicherweise beendet sich die Java Virtual Machine nur wenn alle
 * Threads beendet sind.
 */
public class BodesuriThread extends Thread {
	public BodesuriThread(String name) {
		super(name);
		setupExceptionHandler();
    }

	private void setupExceptionHandler() {
	    setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			public void uncaughtException(Thread t, Throwable e) {
				System.out.println("uncaughtException in thread " + t);
				e.printStackTrace();
				System.exit(99);
			}
		});
    }
}
