package io.github.nul00000000.agent;

import java.util.Random;

import io.github.nul00000000.Genome;
import io.github.nul00000000.Species;
import io.github.nul00000000.chess.Chess;

public class Agent extends AgentBase implements Comparable<Agent>{
	
	private Genome genome;
	private Species species;
		
	public Agent(int in, int out, int hidden, Chess chess, Random random) {
		super(chess);
		this.genome = new Genome(in, out, random);
		this.species = Species.getSpecies(genome);
	}
	
	public Agent(Agent parent1, Agent parent2, Chess chess, Random random) {
		super(chess);
		this.genome = new Genome(parent1.genome, parent2.genome, random);
		this.species = Species.getSpecies(this.genome);
	} 
	
	public Species getSpecies() {
		return species;
	}
	
	public int getNumInputs() {
		return genome.inputs;
	}
	
	public int getNumOutputs() {
		return genome.outputs;
	}

	//intended to order lowest score to highest
	@Override
	public int compareTo(Agent o) {
		return (int) Math.signum(this.getScore() - o.getScore());
	}

	@Override
	public void act() {
		// TODO Auto-generated method stub
		
	}

}
