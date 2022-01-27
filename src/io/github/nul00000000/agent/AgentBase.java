package io.github.nul00000000.agent;

import java.awt.Color;
import java.awt.Graphics2D;

public abstract class AgentBase {
	
	private double score;
	protected double x, y;
	protected double dx, dy;
	protected double angle;
	
	public AgentBase(double x, double y, double angle) {
		this.score = 0;
		this.x = x;
		this.y = y;
		this.dx = 0;
		this.dy = 0;
	}
	
	public double getScore() {
		return score;
	}
	
	public void addScore(double s) {
		score += s;
	}
	
	public void resetScore() {
		score = 0;
	}
	
	public abstract void act();
	
	public void update() {
		act();
		x += dx;
		y += dy;
		dx *= 0.9;
		dy *= 0.9;
	}
	
	public void draw(Graphics2D g) {
		g.setColor(Color.RED);
		g.fillOval((int) x - 10, (int) y - 10, 20, 20);
	}

}
