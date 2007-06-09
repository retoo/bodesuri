package applikation.client.controller;

public class SpielerFarbe {
	float r, g, b;
	
	public SpielerFarbe(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public float getRed() {
    	return r;
    }
	
	public float getGreen() {
		return g;
	}
	
	public float getBlue() {
		return b;
	}
}
