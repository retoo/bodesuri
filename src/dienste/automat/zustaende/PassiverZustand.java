package dienste.automat.zustaende;


public interface PassiverZustand extends ZustandsInterface {
	Class<? extends Zustand> handle();
}
