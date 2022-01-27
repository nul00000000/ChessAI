package io.github.nul00000000.chess;

public class Knight extends Piece {

	public Knight(int x, int y, boolean white, Chess board) {
		super(x, y, white, "k", board);
	}

	@Override
	public boolean isValidMove(int x, int y) {
		int nx = Math.abs(x - this.x);
		int ny = Math.abs(y - this.y);
		return nx + ny == 3 && (nx != 0 && ny != 0);
	}

}
