package io.github.nul00000000.agent;

import java.util.Random;

import io.github.nul00000000.Genome;
import io.github.nul00000000.Main;
import io.github.nul00000000.Species;

public class Agent extends AgentBase implements Comparable<Agent>{
	
	private Genome genome;
	private Species species;
	
	private double score;
	
	public Agent(int in, int out, int hidden, Random random) {
		super(random.nextDouble() * Main.WIDTH, random.nextDouble() * Main.HEIGHT, random.nextDouble() * 2 * Math.PI);
		this.genome = new Genome(in, out, random);
		this.species = Species.getSpecies(genome);
	}
	
	public Agent(Agent parent1, Agent parent2, Random random) {
		super(random.nextDouble() * Main.WIDTH, random.nextDouble() * Main.HEIGHT, random.nextDouble() * 2 * Math.PI);
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
	
	public void addScore(double score) {
		this.score += score;
	}
	
	public void resetScore() {
		this.score = 0;
	}

	//intended to order lowest score to highest
	@Override
	public int compareTo(Agent o) {
		return (int) Math.signum(this.score - o.score);
	}

	@Override
	public void act() {
		// TODO Auto-generated method stub
		
	}

}
