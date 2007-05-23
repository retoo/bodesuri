package dienste.automat;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class Automat {
	protected List<Zustand> states;
	private Zustand start;
	private EventSource eventSource;
	private Map<Class<? extends Zustand>, Zustand> stateMap;

	public Automat() {
		states = new Vector<Zustand>();
		stateMap = new IdentityHashMap<Class<? extends Zustand>, Zustand>();
	}

	protected void register(Zustand state) {
		states.add(state);
		state.setMachine(this);
		stateMap.put(state.getClass(), state);
	}

	protected void setStartState(Class<? extends Zustand> klasse) {
		start = getState(klasse);
	}

	protected void setEventSource(EventSource source) {
		eventSource = source;
	}

	public Zustand getState(Class<? extends Zustand> klasse) {
		Zustand state = stateMap.get(klasse);
		
		if (state == null) 
			throw new RuntimeException("Nichtregistierer Zustand " + klasse);
		
		return state;
	}

	public void run() {
		Zustand currentState = start;

		while (true) {
			Zustand newState;
			
			System.out.println("Execute State: " + currentState);
			
			currentState.init();
		
			if (currentState instanceof AktiverZustand) {
				AktiverZustand state = (AktiverZustand) currentState;
				
				Event event = eventSource.getEevent();
				
				newState = state.handle(event);
			} else {
				PassiverZustand state = (PassiverZustand) currentState;
				newState = state.getNextState();
			}
			currentState = newState;
		}			
	}
}
