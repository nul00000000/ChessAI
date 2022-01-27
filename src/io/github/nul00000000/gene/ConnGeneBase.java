package io.github.nul00000000.gene;

import java.util.ArrayList;
import java.util.List;

public class ConnGeneBase {
	
	public static ArrayList<ConnGeneBase> innovatedConns = new ArrayList<>();
	
	public final NodeGene in;
	public final NodeGene out;
	
	//number assigned to each new innovated connection not seen before (used in crossover)
	public final int innovationID;
	
	private ConnGeneBase(NodeGene in, NodeGene out) {
		this.in = in;
		this.out = out;
		this.innovationID = innovatedConns.size();
		innovatedConns.add(this);
	}
	
	public static ConnGeneBase getConnGeneBase(NodeGene in, NodeGene out) {
		for(ConnGeneBase s : innovatedConns) {
			if(in == s.in && out == s.out) {
				return s;
			}
		}
		return new ConnGeneBase(in, out);
	}
	
	public static ConnGeneBase getConnGeneBase(int innovationID) {
		return innovatedConns.get(innovationID);
	}
	
	public static int indexOf(List<ConnGene> c, int innovationID) {
		for(int i = 0; i < c.size(); i++) {
			if(c.get(i).base.innovationID == innovationID) {
				return i;
			}
		}
		return -1;
	}

}
