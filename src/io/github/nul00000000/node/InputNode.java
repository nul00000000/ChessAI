package io.github.nul00000000.node;

public class InputNode extends Node {
	
	public InputNode(int id, int x, int y) {
		super(id, x, y);
	}

	public void addInput(float data) {
		this.data = data;
	}
	
	public float getOutput() {
		return this.data;
	}

}
