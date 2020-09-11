package pieces;

import javax.swing.ImageIcon;

public class Knight extends Piece{
public ImageIcon getImage() {
	if(isWhite) {
		return new ImageIcon("img/white_knight.png");
	}else {
		return new ImageIcon("img/black_knight.png");
	}
	}
}