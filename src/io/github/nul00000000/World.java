package io.github.nul00000000;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import io.github.nul00000000.agent.Agent;
import io.github.nul00000000.agent.PlayerAgent;
import io.github.nul00000000.chess.Chess;

public class World {
	
	public Agent[] agents;
	public HashMap<Species, ArrayList<Agent>> species;
	private Random random;
	//private PlayerAgent player;
	
	private Chess chess;
	
	public World(long seed) {
		species = new HashMap<>();
		this.random = new Random(seed);
		//this.player = new PlayerAgent();
		this.chess = new Chess(0, 0, Main.WIDTH, Main.HEIGHT);
	}
	
	public void sortAgents() {
		species.clear();
		for(Agent a : agents) {
			if(!species.containsKey(a.getSpecies())) {
				species.put(a.getSpecies(), new ArrayList<>());
			}
			species.get(a.getSpecies()).add(a); 
		}
	}
	
	public void nextGeneration() {
		for(ArrayList<Agent> s : species.values()) {
			int r = s.size() / 2;
			if(r == 0) {
				s.remove(0);
			} else {
				for(int i = 0; i < r; i++) {
					s.remove(0);
				}
			}
		}
	}
	
	public void update() {
		//player.update();
		chess.update();
		//TODO scoring shit and stuff
		
		//if(generationfinishedtesting) sortAgents();
	}
	
	public void draw(Graphics2D g) {
		chess.draw(g);
		//player.draw(g);
	}

}
