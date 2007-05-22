package dienste.statemachine;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;




public class StateMachine {
	protected List<State> states;
	private State start;
	private EventSource eventSource;
	private Map<Class<? extends State>, State> stateMap;
	
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
	
	protected void setEventSource(EventSource source) {
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
