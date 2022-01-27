package io.github.nul00000000;

import java.util.ArrayList;

public class Species {
	
	public static final double SPECIATION_THRESHOLD = 100.0;
	private static ArrayList<Species> knownSpecies = new ArrayList<>();
	
	public Genome definition;
	public int speciesID;
	
	private Species(Genome definition) {
		this.definition = definition;
		this.speciesID = knownSpecies.size();
		knownSpecies.add(this);
	}
	
	public static Species getSpecies(Genome genome) {
		Species closest = null;
		double min = 10000.0;
		for(Species s : knownSpecies) {
			double dist = s.definition.distance(genome);
			if(dist < min) {
				min = dist;
				closest = s;
			}
		}
		if(min < SPECIATION_THRESHOLD) {
			return closest;
		} else {
			return new Species(genome);
		}
	}
	
	public static Species getSpecies(int speciesID) {
		return knownSpecies.get(speciesID);
	}

}
