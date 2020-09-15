package pieces;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import controller.Game;
import controller.Move;
import utilities.Pair;

public class King extends Piece {
	public King(Boolean isWhite, Pair<Integer, Integer> position) {
		this.isWhite = isWhite;
		this.position= position;
	}

	public ArrayList<Move> generateMoves(Game game){
		return null;
	}
	public ImageIcon getImage() {
		if (isWhite) {
			return new ImageIcon("img/white_king.png");
		} else {
			return new ImageIcon("img/black_king.png");
		}
	}
}