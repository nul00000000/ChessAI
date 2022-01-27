package io.github.nul00000000.gene;

public class Gene {
	
	private int data;
	
	public Gene(int in, int out, boolean inH, boolean outH, float weight) {
		this.data = (in << 25) | (inH ? 0x01000000 : 0) | (out << 17) | (outH ? 0x00010000 : 0) | Short.toUnsignedInt((short)(weight * Short.MAX_VALUE));
	}
	
	public Gene(int in, int out, boolean inH, boolean outH, short weight) {
		this.data = (in << 25) | (inH ? 0x01000000 : 0) | (out << 17) | (outH ? 0x00010000 : 0) | Short.toUnsignedInt(weight);
	}
	
	public Gene(int data) {
		this.data = data;
	}
	
	public int getInputID() {
		return (int) (Integer.toUnsignedLong(data) >> 25);
	}
	
	public boolean isInputHidden() {
		return (data & 0x01000000) != 0;
	}
	
	public int getOutputID() {
		return (data >> 17) & 0x7f;
	}
	
	public boolean isOutputHidden() {
		return (data & 0x00010000) != 0;
	}
	
	public short getWeightAsShort() {
		return (short) (data & 0xffff);
	}
	
	public float getWeight() {
		return ((short) (data & 0xffff)) / (float)Short.MAX_VALUE;
	}
	
	public String toBinaryString() {
		return Integer.toBinaryString(data);
	}
	
	public String toDNAString() {
		String r = "";
		for(int i = 0; i < 16; i++) {
			int a = (data & (0x3 << (i * 2))) >> (i * 2);
			r += a == 0 ? 'T' : a == 1 ? 'A' : a == 2 ? 'C' : 'G';
		}
		return r;
	}
	
	public int getData() {
		return data;
	}
	
	public String toString() {
		return Integer.toHexString(data).toUpperCase();
	}

}
