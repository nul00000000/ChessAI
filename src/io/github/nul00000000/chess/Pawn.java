package io.github.nul00000000.chess;

public class Pawn extends Piece {

	public Pawn(int x, int y, boolean white, Chess board) {
		super(x, y, white, "P", board);
	}

	@Override
	public boolean isValidMove(int x, int y) {
//		int ty = white ? y - 1 : y + 1;
		return ((white && y == this.y - 1) || (!white && y == this.y + 1)) && Math.abs(this.x - x) <= 1;
	}

}
