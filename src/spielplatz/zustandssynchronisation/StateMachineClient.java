package spielplatz.zustandssynchronisation;

import spielplatz.zustandssynchronisation.states.Lobby;
import spielplatz.zustandssynchronisation.states.ProgrammStart;
import dienste.netzwerk.Briefkasten;


public class StateMachineClient extends StateMachine {
	public StateMachineClient(Briefkasten briefkasten) {
	    register( new ProgrammStart() );
	    register( new Lobby() );
	    
	    setEvenSource( new NachrichtenLeser(briefkasten));
	    /*IFXMEW evensource */ /*FIXME the fixme */
	    this.briefkasten = briefkasten;
	    
	    
	    setStartState( ProgrammStart.class );
    }
}
