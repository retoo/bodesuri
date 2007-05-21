package spielplatz.zustandssynchronisation;

public class UnknownNextStateException extends RuntimeException {
    public UnknownNextStateException(String msg) {
	    super(msg);
    }

	private static final long serialVersionUID = 1L;
}
