package dienste.statemachine;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class Automat {
	protected List<State> states;
	private State start;
	private EventSource eventSource;
	private Map<Class<? extends State>, State> stateMap;

	public Automat() {
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

	protected void setEventSource(EventSource source) {
		eventSource = source;
	}

	public State getState(Class<? extends State> klasse) {
		State state = stateMap.get(klasse);
		
		if (state == null) 
			throw new RuntimeException("Nichtregistierer Zustand " + klasse);
		
		return state;
	}

	public void run() {
		State currentState = start;

		while (true) {
			State newState;
			
			System.out.println("Execute State: " + currentState);
			
			currentState.init();
		
			if (currentState instanceof ActiveState) {
				ActiveState state = (ActiveState) currentState;
				
				Event event = eventSource.getEevent();
				
				newState = state.handle(event);
			} else {
				PassiveState state = (PassiveState) currentState;
				newState = state.getNextState();
			}
			currentState = newState;
		}			
	}
}
