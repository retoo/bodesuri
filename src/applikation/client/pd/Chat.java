package applikation.client.pd;

import dienste.observer.ObservableList;

public class Chat extends ObservableList<String> {
	public String getLetzteZeile() {
	    return this.get(size() - 1);
    }

	/* TODO: sauber den spielernamen übergeben (-reto) */
	public void neueNachricht(String absender, String nachricht) {
		add(absender + "> " + nachricht + "\n");
    }
}
