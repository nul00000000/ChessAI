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
	private Piece selected = null;
	
	private boolean[][] map;
	
	private boolean whiteCheck;
	private boolean blackCheck;
	
	private King whiteKing;
	private King blackKing;
	
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
		pieces[8] = new Rook(0, 0, false, this);
		pieces[9] = new Knight(1, 0, false, this);
		pieces[10] = new Bishop(2, 0, false, this);
		blackKing = (King) (pieces[11] = new King(3, 0, false, this));
		pieces[12] = new Queen(4, 0, false, this);
		pieces[13] = new Bishop(5, 0, false, this);
		pieces[14] = new Knight(6, 0, false, this);
		pieces[15] = new Rook(7, 0, false, this);
		
		pieces[24] = new Rook(0, 7, true, this);
		pieces[25] = new Knight(1, 7, true, this);
		pieces[26] = new Bishop(2, 7, true, this);
		whiteKing = (King) (pieces[27] = new King(3, 7, true, this));
		pieces[28] = new Queen(4, 7, true, this);
		pieces[29] = new Bishop(5, 7, true, this);
		pieces[30] = new Knight(6, 7, true, this);
		pieces[31] = new Rook(7, 7, true, this);
		this.map = new boolean[8][8];
	}
	
	public void calculateMap() {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				map[i][j] = false;
			}
		}
		for(Piece p : pieces) {
			if(!p.taken) {
				map[p.x][p.y] = true;
			}
		}
	}
	
	public Piece getPieceAt(int x, int y) {
		for(Piece p : pieces) {
			if(!p.taken && p.x == x && p.y == y) {
				return p;
			}
		}
		return null;
	}
	
	public boolean raycast(int x, int y, int tx, int ty) {
		if(tx < 0 || tx >= 8 || ty < 0 || ty >= 8) {
			return false;
		}
		int dx = (int) Math.signum(tx - x);
		int dy = (int) Math.signum(ty - y);
		int sr = Math.max(Math.abs(tx - x), Math.abs(ty - y));
		for(int i = 1; i < sr; i++) {
			if(map[x + dx * i][y + dy * i]) {
				return false;
			}
		}
		return true;
	}
	
	//chex mix :)
	public void checkCheck() {
		whiteCheck = false;
		blackCheck = false;
		for(Piece p : pieces) {
			if(p.isValidMove(whiteKing.x, whiteKing.y)) {
				whiteCheck = true;
			}
			if(p.isValidMove(blackKing.x, blackKing.y)) {
				blackCheck = true;
			}
		}
		System.out.println(whiteCheck + " " + blackCheck);
	}
	
	
	//checks chex mix
	public boolean checkCheckCheck(Piece p, int x, int y, boolean doIfAllowed) {
		int px = p.x;
		int py = p.y;
		Piece o;
		if(p.isValidMove(x, y)) {
			o = this.getPieceAt(x, y);
			if(o != null) {
				o.taken = true;
			}
			p.x = x;
			p.y = y;
		} else {
			return false;
		}
		checkCheck();
		if(!doIfAllowed) {
			p.x = px;
			p.y = py;
			if(o != null) {
				o.taken = false;
			}
		}
		return !((p.white && whiteCheck) || (!p.white && blackCheck));
	}
	
	public void update() {
		checkCheck();
		calculateMap();
		bx = (Input.mouseX - x) * 8 / width;
		by = (Input.mouseY - y) * 8 / width;
		if(Input.jButtons.contains(Input.LEFT_CLICK)) {
			for(Piece p : pieces) {
				if(p.x == bx && p.y == by && !p.taken) {
					selected = p;
				}
			}
		}
		if(Input.jButtons.contains(Input.RIGHT_CLICK) && selected != null) {
			checkCheckCheck(selected, bx, by, true);
		}
	}
	
	public void draw(Graphics2D g) {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				g.setColor((i + j) % 2 == 0 ? LIGHT : DARK);
				g.fillRect(i * tileWidth, j * tileHeight, tileWidth, tileHeight);
				if(selected != null && checkCheckCheck(selected, i, j, false)) {
					g.setColor(Color.GREEN.darker());
					g.drawRect(i * tileWidth + 2, j * tileHeight + 2, tileWidth - 4, tileHeight - 4);
				}
			}
		}
		g.setColor(Color.BLUE.darker());
		g.drawRect(bx * tileWidth, by * tileHeight, tileWidth, tileHeight);
		for(Piece p : pieces) {
			p.draw(g);
		}
	}

}
