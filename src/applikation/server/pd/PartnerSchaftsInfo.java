package applikation.server.pd;

import java.io.Serializable;
import java.util.List;

import applikation.geteiltes.SpielerInfo;

public class PartnerSchaftsInfo implements Serializable {
	public final List<SpielerInfo> spieler;

	public PartnerSchaftsInfo(List<SpielerInfo> spieler) {
		this.spieler = spieler;
    }
}
