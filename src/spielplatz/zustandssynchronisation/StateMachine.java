package spielplatz.zustandssynchronisation;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import dienste.netzwerk.Briefkasten;
import dienste.netzwerk.EndPunkt;

import spielplatz.zustandssynchronisation.states.State;


public class StateMachine {
	protected List<State> states;
	private State start;
	private EventSource eventSource;
	private Map<Class<? extends State>, State> stateMap;
	public EndPunkt endpunkt;
	public Briefkasten briefkasten;
	
	
	public StateMachine() {
		states = new Vector<State>();
		stateMap = new IdentityHashMap<Class<? extends State>, State>();
	}
	
	protected void register(State state) {
        states.add(state);
        state.setMachine(this);
        stateMap.put(state.getClass(), state);
    }

	protected void setStartState(Class<? extends State> klasse) {
		start = getState(klasse);
	}
	
	protected void setEvenSource(EventSource source) {
		eventSource = source;
    }
	
	public State getState(Class<? extends State> klasse) {    
	    return stateMap.get(klasse);
    }
	
	public void run() {
		State currentState = start;
		
		while (true) {			
			Event event = eventSource.getEevent();
			System.out.println("Handling of event " + event);
			State newState = currentState.execute(event);		
			
			currentState = newState;
		}
	}
}
