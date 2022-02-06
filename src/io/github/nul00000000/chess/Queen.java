package io.github.nul00000000.chess;

public class Queen extends Piece {

	public Queen(int x, int y, boolean white, Chess board) {
		super(x, y, white, "Q", board);
	}

	@Override
	public boolean isValidMove(int x, int y) {
		return super.isValidMove(x, y) && ((Math.abs(x - this.x) == Math.abs(y - this.y) && x != this.x) || (((x - this.x) == 0) != ((y - this.y) == 0))) && board.raycast(this.x, this.y, x, y);
	}

}
