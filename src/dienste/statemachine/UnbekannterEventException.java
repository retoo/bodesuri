package dienste.statemachine;

public class UnbekannterEventException extends RuntimeException {
    public UnbekannterEventException(String msg) {
	    super(msg);
    }

	private static final long serialVersionUID = 1L;
}
