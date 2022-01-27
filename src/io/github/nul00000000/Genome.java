package io.github.nul00000000;

import java.util.ArrayList;
import java.util.Random;

import io.github.nul00000000.gene.ConnGene;
import io.github.nul00000000.gene.ConnGeneBase;
import io.github.nul00000000.gene.NodeGene;
import io.github.nul00000000.gene.NodeGene.NodeType;

public class Genome {
	
	public static final int MUTATION_RATE = 2;
	
	public ArrayList<NodeGene> nodeGenes;
	public ArrayList<ConnGene> connGenes;
	public int inputs;
	public int outputs;
	
	public Genome(int inputs, int outputs, Random random) {
		//int maxNodeID = inputs + outputs + maxHiddens;
		nodeGenes = new ArrayList<>(inputs + outputs + 1);
		connGenes = new ArrayList<>();
		this.inputs = inputs;
		this.outputs = outputs;
		for(int i = 0; i < inputs; i++) {
			nodeGenes.add(NodeGene.getOrCreate(i, NodeType.INPUT));
		}
		for(int i = 0; i < outputs; i++) {
			nodeGenes.add(NodeGene.getOrCreate(i + inputs, NodeType.OUTPUT));
		}
		nodeGenes.add(NodeGene.getNextNode(NodeType.HIDDEN));
	}
	
	/**
	 * crossover + mutation
	 * @param g1 the more fit parent
	 * @param g2 the less fit parent
	 * @param random
	 */
	public Genome(Genome g1, Genome g2, Random random) {
		nodeGenes = new ArrayList<>();
		connGenes = new ArrayList<>();
		this.inputs = g1.inputs;
		this.outputs = g1.outputs;
		for(int i = 0; i < inputs; i++) {
			nodeGenes.add(NodeGene.getNodeGene(i));
		}
		for(int i = 0; i < outputs; i++) {
			nodeGenes.add(NodeGene.getNodeGene(i + inputs));
		}
		
		//probably the least efficient way i possibly could have done crossover
		int maxInnovationID1 = 0;
		for(ConnGene g : g1.connGenes) {
			if(g.base.innovationID > maxInnovationID1) {
				maxInnovationID1 = g.base.innovationID;
			}
		}
		for(int i = 0; i <= maxInnovationID1; i++) {
			int i1 = ConnGeneBase.indexOf(g1.connGenes, i);
			int i2 = ConnGeneBase.indexOf(g2.connGenes, i);
			if(i1 > 0 && i2 > 0) {
				connGenes.add(random.nextBoolean() ? g1.connGenes.get(i1) : g2.connGenes.get(i2));
			} else if(i1 > 0) {
				connGenes.add(g1.connGenes.get(i1));
			} else if(i2 > 0) {
				connGenes.add(g2.connGenes.get(i2));
			}
		}
		
		mutate(random);
		
		for(ConnGene g : connGenes) {
			if(g.base.in.type == NodeType.HIDDEN) {
				if(!nodeGenes.contains(g.base.in)) nodeGenes.add(g.base.in);
			}
			if(g.base.out.type == NodeType.HIDDEN) {
				if(!nodeGenes.contains(g.base.out)) nodeGenes.add(g.base.out);
			}
		}
	}
	
	public void mutate(Random random) {
		for(int i = 0; i < MUTATION_RATE; i++) {
			switch(random.nextInt(5)) {
			case 0:
				mutateLink(random);
				break;
			case 1:
				mutateNode(random);
				break;
			case 2:
				mututeEnableDisable(random);
				break;
			case 3:
				mutateWeightShift(random);
				break;
			case 4:
				mutateWeightRandom(random);
				break;
			}
		}
	}
	
	public void mutateLink(Random random) {
		NodeGene in = nodeGenes.get(random.nextInt(nodeGenes.size()));
		while(in.type == NodeType.OUTPUT) {
			in = nodeGenes.get(random.nextInt(nodeGenes.size()));
		}
		NodeGene out = nodeGenes.get(random.nextInt(nodeGenes.size()));
		while(out.type == NodeType.INPUT && !in.equals(out)) {
			out = nodeGenes.get(random.nextInt(nodeGenes.size()));
		}
		connGenes.add(new ConnGene(ConnGeneBase.getConnGeneBase(in, out), random.nextDouble() * 4.0 - 2.0, true));
	}
	
	public void mutateNode(Random random) {
		ConnGene oConn = connGenes.remove(random.nextInt(connGenes.size()));
		NodeGene newNode = NodeGene.getNextNode(NodeType.HIDDEN);
		connGenes.add(new ConnGene(ConnGeneBase.getConnGeneBase(oConn.base.in, newNode), 1.0, true));
		connGenes.add(new ConnGene(ConnGeneBase.getConnGeneBase(newNode, oConn.base.out), oConn.weight, oConn.enabled));// the oConn.enabled might be wrong honesytly not sure
	}
	
	public void mututeEnableDisable(Random random) {
		int i = random.nextInt(connGenes.size());
		ConnGene c = connGenes.get(i);
		ConnGene n = new ConnGene(c.base.innovationID, c.weight, !c.enabled);
		connGenes.set(i, n);
	}
	
	public void mutateWeightShift(Random random) {
		int i = random.nextInt(connGenes.size());
		ConnGene c = connGenes.get(i);
		ConnGene n = new ConnGene(c.base.innovationID, c.weight * random.nextDouble() * 2.0, c.enabled);
		connGenes.set(i, n);
	}
	
	public void mutateWeightRandom(Random random) {
		int i = random.nextInt(connGenes.size());
		ConnGene c = connGenes.get(i);
		ConnGene n = new ConnGene(c.base.innovationID, random.nextDouble() * 4.0 - 2.0, c.enabled);
		connGenes.set(i, n);
	}
	
	public double distance(Genome genome) {
		return 0;//TODO change to value
	}
	
//	public final int length;
//	public final Gene[] genes;
//	
//	public Genome(int length, Random random) {
//		this.length = length;
//		this.genes = new Gene[length];
//		for(int i = 0; i < length; i++) {
//			genes[i] = new Gene(random.nextInt());
//		}
//	}
//	
//	public Genome(int length, int in, int out, boolean hiddens, Random random) {
//		this.length = length;
//		this.genes = new Gene[length];
//		for(int i = 0; i < length; i++) {
//			genes[i] = new Gene(random.nextInt(in), random.nextInt(out), hiddens && random.nextBoolean(), hiddens && random.nextBoolean(), random.nextFloat() * 2 - 1);
//		}
//	}
//	
//	public long getSeed() {
//		return ((long) genes[0].getData()) | (((long) genes[1].getData()) << 32);
//	}

}
