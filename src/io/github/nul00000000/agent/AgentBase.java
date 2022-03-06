package io.github.nul00000000.agent;

import java.awt.Color;
import java.awt.Graphics2D;

import io.github.nul00000000.chess.Chess;

public abstract class AgentBase {
	
	private double score;
	private Chess chess;
	private int x1, y1, x2, y2;
	
	public AgentBase(Chess chess) {
		this.score = 0;
		this.chess = chess;
	}
	
	public void setChess(Chess chess) {
		this.chess = chess;
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
	
	protected void move(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		chess.move(x1, y1, x2, y2);
	}
	
	public abstract void act();
	
	public void draw(Graphics2D g) {
		g.setColor(Color.RED);
		g.drawLine(x1 * chess.tileWidth + chess.tileWidth / 2, y1 * chess.tileHeight + chess.tileHeight / 2, x2 * chess.tileWidth + chess.tileWidth / 2, y2 * chess.tileHeight + chess.tileHeight / 2);
	}

}
