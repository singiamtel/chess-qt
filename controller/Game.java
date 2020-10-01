package controller;

import java.util.ArrayList;

import pieces.*;
import utilities.Pair;
import utilities.Raycast;

public class Game {
	private Piece [][] board = new Piece[8][8];
	private boolean whitePlays;
	// TODO: implement enPassant
	private Pawn enPassant;
	private Pawn lastEnPassant;


	// Test game
	public Game(boolean empty){
		board[3][3] = new Knight(true,new Pair(3,3));
		board[0][0] = new King(true,new Pair(0,0));
		board[1][2] = new King(false,new Pair(1,2));
		board[4][2] = new Pawn(false,new Pair(4,2));
	}

	private Piece[][] cloneBoard(){
		Piece [][] newBoard = new Piece[8][8];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if(board[i][j] != null) {
					newBoard[i][j] = board[i][j].clonePiece();
				}
				else newBoard[i][j] = board[i][j];
			}
		}
		return newBoard;
	}
	public Game(Game game){

		this.board = game.cloneBoard();

		this.whitePlays = game.whitePlays;
		if(game.enPassant != null){
			this.enPassant = (Pawn)game.enPassant.clonePiece();
		}
		if(game.lastEnPassant != null){
			this.lastEnPassant = (Pawn)game.lastEnPassant.clonePiece();
		}
	}
	public Game(){
		// Empty constructor starts a new game 
		// White pieces
		board[0][0] = new Rook(true, new Pair(0,0));
		board[0][1] = new Knight(true,new Pair(0,1));
		board[0][2] = new Bishop(true,new Pair(0,2));
		board[0][3] = new Queen(true,new Pair(0,3));
		board[0][4] = new King(true,new Pair(0,4));
		board[0][5] = new Bishop(true,new Pair(0,5));
		board[0][6] = new Knight(true,new Pair(0,6));
		board[0][7] = new Rook(true,new Pair(0,7));

		for(int i=0; i<8; ++i){
			board[1][i] = new Pawn(true,new Pair(1,i));
		}
		// Black pieces
		for(int i=0; i<8; ++i){
			board[6][i] = new Pawn(false, new Pair(6, i));
		}
		board[7][0] = new Rook(false,new Pair(7,0));
		board[7][1] = new Knight(false,new Pair(7,1));
		board[7][2] = new Bishop(false,new Pair(7,2));
		board[7][3] = new Queen(false,new Pair(7,3));
		board[7][4] = new King(false,new Pair(7,4));
		board[7][5] = new Bishop(false,new Pair(7,5));
		board[7][6] = new Knight(false,new Pair(7,6));
		board[7][7] = new Rook(false,new Pair(7,7));

		// Empty spaces
		for(int i=2; i<6; ++i){
			for(int j=0; j<8; ++j){
				board[i][j] = null;
			}
		}
		this.whitePlays = true;
	}

	public boolean whitePlays() {
		return this.whitePlays;
	}

	public void setWhitePlays(boolean whitePlays) {
		this.whitePlays = whitePlays;
	}

	public Pawn getEnPassant() {
		return this.enPassant;
	}

	public void setEnPassant(Pawn enPassant) {
		this.enPassant = enPassant;
	}

	public Piece getPieceAtSquare(int square){
		int x = square/8;
		int y = square%8;
		return board[x][y];
	}

	public Piece getPieceAtSquare(Pair square){
		return board[square.getFirst()][square.getSecond()];
	}

	// This function is only for debug purposes and should never execute
	public void printBoard(){
			for(int j=7; j>=0; j--){
				for(int k=0; k<8; ++k){
					if(this.board[j][k] != null) System.out.print(this.board[j][k].getClass().getName() + " ");
					else System.out.print("null ");
				}
				System.out.println("");
			}
	
	}

	public boolean isKingOnCheck(boolean isWhite){
		// ArrayList<Move> enemyMoves = new ArrayList<Move>(this.generateAllColourMoves(!isWhite));
		// Piece king = findKing(isWhite);
		// System.out.println("king: " + king.getPosition().getFirst() + " " + king.getPosition().getSecond());
		// for (int i = 0; i < enemyMoves.size(); i++) {
		// 	System.out.println("checking move " + enemyMoves.get(i));
		// 	if(enemyMoves.get(i).getTo().equals(king.getPosition())){
		// 		return true;
		// 	}
		// }
		// return false;
		Piece king = findKing(isWhite);
		if(Raycast.kingRaycast(this, king)){
			return true;
		}
		return false;
	}

	private Piece findKing(boolean isWhite){
		for(int i=0; i<8; ++i){
			for (int j = 0; j < 8; j++) {
				Piece tmp = this.getPieceAtSquare(new Pair(i, j));
				if(tmp != null){
					if(tmp instanceof King && tmp.isWhite() == isWhite){
						return tmp;
					}
				}
			}
		}
		// TODO: no king exception
		return null;
	}
	// public ArrayList

	public ArrayList<Move> generateAllColourMoves(boolean isWhite){
		ArrayList<Move> list = new ArrayList<Move>();
		for(int i = 0; i<8; ++i){
			for(int j = 0; j<8; ++j){
				Piece attempt = this.getPieceAtSquare(new Pair(i,j));
				if(attempt != null && attempt.isWhite() == isWhite){
					ArrayList<Move> tmp = attempt.generateMoves(this);
					if(tmp != null) list.addAll(tmp);
				}
			}
		}
		return list;
	}

	public void makeMove(Move move) {
		this.whitePlays = !this.whitePlays;

		// if(GameController.getCurrent().getEnPassant() != null && GameController.getCurrent().getEnPassant() == move.getTo()) {
		// 	makeMoveEnPassant(move);
		// 	return;
		// }

		this.board[move.getTo().getFirst()][move.getTo().getSecond()] = this.board[move.getFrom().getFirst()][move
				.getFrom().getSecond()].clonePiece();
		this.board[move.getFrom().getFirst()][move.getFrom().getSecond()] = null;

		this.board[move.getTo().getFirst()][move.getTo().getSecond()]
				.setPosition(new Pair(move.getTo().getFirst(), move.getTo().getSecond()));
		// TODO update material
	}

	public void makeMoveEnPassant(Move move) {
		this.board[move.getTo().getFirst()][move.getTo().getSecond()] = this.board[move.getFrom().getFirst()][move.getFrom().getSecond()];
		this.board[move.getFrom().getFirst()][move.getFrom().getSecond()] = null;
		this.board[move.getTo().getFirst()][move.getTo().getSecond()].setPosition(new Pair(move.getTo().getFirst(), move.getTo().getSecond()));
		
	}
		
	// private void undoMove(Move move) {
	// 	this.whitePlays = !this.whitePlays;
	// 	this.board[move.getFrom().getFirst()][move.getFrom().getSecond()] = this.board[move.getTo().getFirst()][move
	// 			.getTo().getSecond()];
	// 	this.board[move.getTo().getFirst()][move.getTo().getSecond()] = null;

	// 	this.board[move.getFrom().getFirst()][move.getFrom().getSecond()]
	// 			.setPosition(new Pair(move.getFrom().getFirst(), move.getFrom().getSecond()));
	// }

	public boolean validateMove(Move move) {
		// this.makeMove(move);

		Game check = new Game(this);
		// System.out.println("CHECK BOARD BEFORE CHANGING");
		// check.printBoard();
		check.makeMove(move);
		// System.out.println("CHECK BOARD AFTER CHANGING");
		// check.printBoard();
		// System.out.println("THIS BOARD");
		// this.printBoard();
		if (check.isKingOnCheck(whitePlays)) {
			System.out.println("move " + move + " left king on check");
			return false;
		}
		System.out.println("move " + move + " didnt leave king on check");
		return true;
	}

	public ArrayList<Move> purgeMoves(ArrayList<Move> moves){
		for (int i = 0; i < moves.size(); i++) {
			System.out.println("trying to purge move " + moves.get(i));
			if(!validateMove(moves.get(i))){
				System.out.println("purging " + moves.get(i).getFrom() + " " + moves.get(i).getTo());
				moves.remove(i);
				i--;
			}
		}
		return moves;
	}

	public boolean isStaleMate(){
		if(isKingOnCheck(this.whitePlays)) return false;
		ArrayList<Move> legalMoves = new ArrayList<Move>(this.generateAllColourMoves(this.whitePlays));
		for (int i = 0; i < legalMoves.size(); i++) {
			if(!validateMove(legalMoves.get(i))){
				legalMoves.remove(i);
			}
		}
		if(legalMoves.isEmpty()) return true;
		return false;
	}

	public boolean isMate(){
		if(! isKingOnCheck(this.whitePlays)) return false;
		ArrayList<Move> legalMoves = new ArrayList<Move>(this.generateAllColourMoves(this.whitePlays));
		for (int i = 0; i < legalMoves.size(); i++) {
			if(!validateMove(legalMoves.get(i))){
				legalMoves.remove(i);
			}
		}
		if(legalMoves.isEmpty()) return true;
		return false;
	}
	private void updateEnPassant(){
		if(enPassant != null && lastEnPassant == enPassant){
			// If current en passant is the same as the last turn's one,
			// we assume no new pawn is on en passant, and reset it.
			enPassant = null;
			lastEnPassant = null;
		}
		else{
			// Otherwise, just update variable
			lastEnPassant = enPassant;
		}
	}

	public void update(){
		this.updateEnPassant();
		if(isMate()){
			System.out.println("GAME OVER");
		}
		// checks if game is over
		// this function should run every ply
	}
}