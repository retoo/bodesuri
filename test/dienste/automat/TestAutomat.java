package dienste.automat;
import dienste.automat.testzustaende.AktiverTestZustandAlpha;
import dienste.automat.testzustaende.AktiverTestZustandBeta;


public class TestAutomat extends Automat {
	public EventQueue output;

	public TestAutomat(EventQueue input, EventQueue output) {
	    registriere(new AktiverTestZustandAlpha());
	    registriere(new AktiverTestZustandBeta());
	    
	    setStart(AktiverTestZustandAlpha.class);
	    
	    setEventQuelle(input);
	    
	    this.output = output;
    }

	public TestAutomat() {
    }
}