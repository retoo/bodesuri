package spielplatz;

import java.util.List;
import java.util.Vector;

import spielplatz.zustandssynchronisation.states.EndState;
import spielplatz.zustandssynchronisation.states.State;


public class StateMachine {
	protected List<State> states;
	private State start;
	
	public StateMachine() {
		states = new Vector<State>();
	}
	
	
	protected void register(State state) {
        states.add(state);
    }


	protected void setStartState(State state) {
		start = state;
	}
	
	protected void run() {
		State currentState = start;
		
		while (true) {
			State newState = currentState.execute();
			
			if (newState == null) 
				throw new UnknownNextStateException("State " + currentState + " didn't return the next state");

			if (newState instanceof EndState) {
				/* FIXME */
			}
		}
	}


	protected void setEvenSource(EventSource source) {
        // TODO Auto-generated method stub
        
    }
}
