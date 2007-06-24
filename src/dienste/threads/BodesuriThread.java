package dienste.threads;

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
