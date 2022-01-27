package io.github.nul00000000.chess;

import java.awt.Color;
import java.awt.Graphics2D;

import io.github.nul00000000.Input;

public class Chess {
	
	public final static Color LIGHT = new Color(0xddddbb);
	public final static Color DARK = new Color(0x664422);
	public final static Color LIGHT_MOVEABLE = new Color(0xbbff99);
	public final static Color DARK_MOVEABLE = new Color(0x556611);
	
	public int x;
	public int y;
	public int width;
	public int height;
	public int tileWidth;
	public int tileHeight;
	
	private int bx;
	private int by;
	
	private Piece[] pieces;
	
	public Chess(int x, int y, int width, int height) {
		this.tileWidth = width / 8;
		this.tileHeight = height / 8;
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.pieces = new Piece[32];
		for(int i = 0; i < 8; i++) {
			pieces[i] = new Pawn(i, 1, false, this);
			pieces[16 + i] = new Pawn(i, 6, true, this);
		}
		//pieces[] = 
	}
	
//	public void raycast(int x, int y, int dx, int dy) {
//		
//	}
	
	public void update() {
		bx = (Input.mouseX - x) * 8 / width;
		by = (Input.mouseY - y) * 8 / width;
//		if(Input.jButtons.contains(Input.LEFT_CLICK)) {
//			if(test.isValidMove(bx, by)) {
//				test.x = bx;
//				test.y = by;
//			}
//		}
	}
	
	public void draw(Graphics2D g) {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				g.setColor((i + j) % 2 == 0 ? LIGHT : DARK);
				g.fillRect(i * tileWidth, j * tileHeight, tileWidth, tileHeight);
//				if(test.isValidMove(i, j)) {
//					g.setColor(Color.GREEN.darker());
//					g.drawRect(i * tileWidth + 2, j * tileHeight + 2, tileWidth - 4, tileHeight - 4);
//				}
			}
		}
		g.setColor(Color.BLUE.darker());
		g.drawRect(bx * tileWidth, by * tileHeight, tileWidth, tileHeight);
		for(Piece p : pieces) {
			if(p != null) {
				p.draw(g);
			}
		}
	}

}
