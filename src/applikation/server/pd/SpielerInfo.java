package applikation.server.pd;

import java.io.Serializable;

public class SpielerInfo implements Serializable {
	public final String name;

	public SpielerInfo(String name) {
		this.name = name;
	}
}
