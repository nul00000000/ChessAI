package io.github.nul00000000.gene;

public class ConnGene {
	
	
	public final double weight;
	public final boolean enabled;
	
	public final ConnGeneBase base;
	
	public ConnGene(int innovationID, double weight, boolean enabled) {
		this.base = ConnGeneBase.getConnGeneBase(innovationID);
		this.weight = weight;
		this.enabled = enabled;
	}
	
	public ConnGene(ConnGeneBase inn, double weight, boolean enabled) {
		this.base = inn;
		this.weight = weight;
		this.enabled = enabled;
	}

}
