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
	
	private int winStatus = 0;
	
	private boolean whiteTurn = true;
	
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
	}
	
	/**
	 * 
	 * @param p
	 * @param x
	 * @param y
	 * @param doIfAllowed
	 * @return -1 if move is not allowed, 0 if it is allowed, 1 if a piece is taken
	 */
	public int checkCheckCheck(Piece p, int x, int y, boolean doIfAllowed) {
		boolean pieceTaken = false;
		int px = p.x;
		int py = p.y;
		Piece o = null;
		if(p.isValidMove(x, y)) {
			o = this.getPieceAt(x, y);
			if(o != null) {
				pieceTaken = true;
				o.taken = true;
			}
			p.x = x;
			p.y = y;
		} else {
			return -1;
		}
		calculateMap();
		checkCheck();
		boolean r = !((p.white && whiteCheck) || (!p.white && blackCheck));
		if(!doIfAllowed || !r) {
			p.x = px;
			p.y = py;
			if(o != null) {
				o.taken = false;
			}
			calculateMap();
			checkCheck();
		}
		return r ? (pieceTaken ? 1 : 0) : -1;
	}
	
	//returns true if either is in checkmate
	public boolean checkCheckmate() {
		for(Piece p : pieces) {
			if(p.white == whiteTurn) {
				for(int i = 0; i < 8; i++) {
					for(int j = 0; j < 8; j++) {
						if(checkCheckCheck(p, i, j, false) != -1) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	public int move(int x1, int y1, int x2, int y2) {
		Piece p = this.getPieceAt(x1, y1);
		if(p == null || p.white != whiteTurn) {
			return -2;
		}
		int a = checkCheckCheck(p, x2, y2, true);
		//there can only be one check at the end of a turn, so just check for either and reward that extra
		if(a != -1 && (whiteCheck || blackCheck)) {
			return 2;
		} else {
			return a;
		}
	}
		
	public void update() {
		bx = (Input.mouseX - x) * 8 / width;
		by = (Input.mouseY - y) * 8 / width;
		if(Input.jButtons.contains(Input.LEFT_CLICK)) {
			for(Piece p : pieces) {
				if(p.x == bx && p.y == by && !p.taken) {
					selected = p;
				} 
			}
		}
		
//		if(Input.jUpButtons.contains(Input.LEFT_CLICK) && selected != null && (selected.white == whiteTurn) && !(selected.x == bx && selected.y == by)) {
//			boolean a = checkCheckCheck(selected, bx, by, true);
//			if(a) {
//				whiteTurn = !whiteTurn;
//				selected.hasMoved = true;
//			}
//		}
	}
	
	public void draw(Graphics2D g) {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				g.setColor((i + j) % 2 == 0 ? LIGHT : DARK);
				g.fillRect(i * tileWidth, j * tileHeight, tileWidth, tileHeight);
				if(selected != null && (selected.white == whiteTurn) && (checkCheckCheck(selected, i, j, false) != -1)) {
					g.setColor(Color.GREEN.darker());
					g.drawRect(i * tileWidth + 2, j * tileHeight + 2, tileWidth - 4, tileHeight - 4);
				}
			}
		}
		g.setColor(Color.BLUE.darker());
		g.drawRect(bx * tileWidth, by * tileHeight, tileWidth, tileHeight);
		for(Piece p : pieces) {
			if(p != selected || !Input.buttons.contains(Input.LEFT_CLICK)) {
				p.draw(g);
			}
		}
		if(Input.buttons.contains(Input.LEFT_CLICK)) {
			selected.draw(g, Input.mouseX, Input.mouseY);
		}
	}

}
