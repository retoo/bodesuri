package spielplatz.zustandssynchronisation;

import spielplatz.zustandssynchronisation.states.SpielEndeState;
import spielplatz.zustandssynchronisation.states.SpielStartState;


public class Sync extends StateMachine {
	public Sync() {
	    register( new SpielStartState() );
	    register( new SpielEndeState() );
	    
	    setEvenSource( new NachrichtenLeser());
	    setStartState( states.get(0) );
    }

	public static void main(String[] args) {
		StateMachine sync = new Sync();
		
		sync.run();
	}
}
