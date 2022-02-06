package io.github.nul00000000.chess;

public class Pawn extends Piece {

	public Pawn(int x, int y, boolean white, Chess board) {
		super(x, y, white, "P", board);
	}

	@Override
	public boolean isValidMove(int x, int y) {
		int t = white ? -1 : 1;
		if(y != this.y + t || Math.abs(this.x - x) > 1) {
			return false;
		}
		if(x == this.x) {
			return board.getPieceAt(x, y) == null;
		} else {
			return board.getPieceAt(x, y) != null;
		}
	}

}
