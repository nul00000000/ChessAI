package io.github.nul00000000.chess;

public class King extends Piece {

	public King(int x, int y, boolean white, Chess board) {
		super(x, y, white, "K", board);
	}

	@Override
	public boolean isValidMove(int x, int y) {
		return super.isValidMove(x, y) && (x != this.x || y != this.y) && Math.abs(x - this.x) <= 1 && Math.abs(y - this.y) <= 1;
	}

}
