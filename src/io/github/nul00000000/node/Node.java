package io.github.nul00000000.node;

public class Node {
	
	protected float data;
	public final int id;
	public final int x;
	public final int y;
	
	public Node(int id, int x, int y) {
		this.id = id;
		this.x = x;
		this.y = y;
	}
	
	public void addInput(float data) {
		this.data += data;
	}
	
	public float getOutput() {
		return (float) Math.tanh(data);
	}
	
	public void reset() {
		this.data = 0;
	}

}
