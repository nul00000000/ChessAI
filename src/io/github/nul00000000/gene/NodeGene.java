package io.github.nul00000000.gene;

import java.util.ArrayList;

public class NodeGene {
	
	private static ArrayList<NodeGene> knownNodes = new ArrayList<>();
		
	public final int nodeID;
	public final NodeType type;
	
	private NodeGene(NodeType type) {
		this.nodeID = knownNodes.size();
		this.type = type;
		knownNodes.add(this);
	}
	
	public static int getAmountOfNodes() {
		return knownNodes.size();
	}
	
	public static NodeGene getNodeGene(int id) {
		return knownNodes.get(id);
	}
	
	public static NodeGene getOrCreate(int id, NodeType type) {
		NodeGene r = knownNodes.get(id);
		if(r != null) {
			return r;
		} else {
			return getNextNode(type);
		}
	}
	
	public static NodeGene getNextNode(NodeType type) {
		return new NodeGene(type);
	}
	
	@Override
	public boolean equals(Object o) {
		return ((NodeGene) o).nodeID == this.nodeID;
	}
	
	public enum NodeType {
		INPUT, HIDDEN, OUTPUT;
	}

}
