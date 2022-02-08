package io.github.nul00000000.chess;

import java.awt.Color;
import java.awt.Graphics2D;

public abstract class Piece {
	
	public int x;
	public int y;
	public final boolean white;
	protected final Chess board;
	private String dName;
	public boolean taken;
	public boolean hasMoved = false;
	
	public Piece(int x, int y, boolean white, String dName, Chess board) {
		this.x = x;
		this.y = y;
		this.white = white;
		this.board = board;
		this.dName = dName;
		this.taken = false;
	}
	
	public boolean isValidMove(int x, int y) {
		Piece p = board.getPieceAt(x, y);
		return p == null || (p.white != this.white);
	}
	
	public void draw(Graphics2D g) {
		if(!taken) {
			g.setColor(white ? Color.WHITE : Color.BLACK);
			g.fillRect(board.x + x * board.tileWidth + board.tileWidth / 8, board.y + y * board.tileHeight + board.tileHeight / 8, board.tileWidth * 3 / 4, board.tileHeight * 3 / 4);
			g.setColor(white ? Color.BLACK : Color.WHITE);
			if(white) {
				g.drawRect(board.x + x * board.tileWidth + board.tileWidth / 8, board.y + y * board.tileHeight + board.tileHeight / 8, board.tileWidth * 3 / 4, board.tileHeight * 3 / 4);
			}
			g.drawString(dName, board.x + x * board.tileWidth + board.tileWidth * 5 / 16, board.y + y * board.tileHeight + board.tileHeight * 11 / 16);
		}
	}

}
