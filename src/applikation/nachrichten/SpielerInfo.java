package applikation.nachrichten;

import java.io.Serializable;

public class SpielerInfo implements Serializable {
	public final String name;

	public SpielerInfo(String name) {
		this.name = name;
	}
}
