package ui.spiel.brett;

import java.awt.Color;
import java.util.Vector;

public enum FigurenFarbe{
	BLAU(Color.BLUE, "blau"), 
	GRUEN(Color.GREEN, "gruen"), 
	GELB(Color.YELLOW, "gelb"), 	
	ROT(Color.RED, "rot");
	
	private  String sFarbe;
	private  Color farbe;
		
	public Vector<FigurenFarbe> farben = new Vector<FigurenFarbe>();

	private FigurenFarbe(Color farbe, String sFarbe){
		this.farbe = farbe;
		this.sFarbe = sFarbe;
	}

	public Color getColor(){
		return this.farbe;
	}
	
	public String getFarbe(){
		return this.sFarbe;
	}
}
