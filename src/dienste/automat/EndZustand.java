package dienste.automat;

public class EndZustand extends Zustand {
	Automat automat;
	
	@Override
	public void setAutomat(Automat automat) {
		this.automat = automat;
	}
}
