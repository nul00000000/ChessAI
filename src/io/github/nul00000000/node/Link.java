package io.github.nul00000000.node;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Link {
	
	private Node in;
	private Node out;
	private float weight;
	
	public Link(Node in, Node out, float weight) {
		this.in = in;
		this.out = out;
		this.weight = weight;
	}
	
	public int getInNode() {
		return in.id;
	}
	
	public int getOutNode() {
		return out.id;
	}
	
	public float getWeight() {
		return weight;
	}
	
	public void calculate() {
		out.addInput(in.getOutput() * weight);
	}
	
	public float getCalculation() {
		return in.getOutput() * weight;
	}
	
	public void draw(Graphics2D g) {
		float abs = Math.abs(weight);
		g.setColor(weight > 0 ? Color.RED : Color.BLUE);
		g.setStroke(new BasicStroke(abs + 1));
		this.drawArrowLine(g, in.x, in.y, out.x, out.y, (int)(3 * abs + 2), (int)(2 * abs + 2));
	}
	
	private void drawArrowLine(Graphics2D g, int x1, int y1, int x2, int y2, int d, int h) {
	    int dx = 2 * (x2 - x1) / 3, dy = 2 * (y2 - y1) / 3;
	    double D = 2 * Math.sqrt(dx*dx + dy*dy) / 3;
	    double xm = D - d, xn = xm, ym = h, yn = -h, x;
	    double sin = dy / D, cos = dx / D;

	    x = xm*cos - ym*sin + x1;
	    ym = xm*sin + ym*cos + y1;
	    xm = x;

	    x = xn*cos - yn*sin + x1;
	    yn = xn*sin + yn*cos + y1;
	    xn = x;

	    int[] xpoints = {x1 + dx, (int) xm, (int) xn};
	    int[] ypoints = {y1 + dy, (int) ym, (int) yn};

	    g.drawLine(x1, y1, x2, y2);
	    g.fillPolygon(xpoints, ypoints, 3);
	}

}
