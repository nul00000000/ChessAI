package io.github.nul00000000.chess;

public class Rook extends Piece {

	public Rook(int x, int y, boolean white, Chess board) {
		super(x, y, white, "R", board);
	}

	@Override
	public boolean isValidMove(int x, int y) {
		return super.isValidMove(x, y) && ((x - this.x) == 0) != ((y - this.y) == 0) && board.raycast(this.x, this.y, x, y);
	}

}
