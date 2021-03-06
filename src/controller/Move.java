package controller;

import utilities.Pair;

public class Move {
	private Pair from;
	private Pair to;
	private Promotions promotion;
	private Pair enPassant;

	public static enum Promotions{
		BISHOP,
		KNIGHT,
		ROOK,
		QUEEN
	}
	public static enum Castle{
		WHITEKING,
		WHITEQUEEN,
		BLACKKING,
		BLACKQUEEN,
	}
	
	private boolean castle = false;
	private Castle which;
	private boolean isEating;
	private boolean check;

	public boolean isCheck() {
		return this.check;
	}

	public Move(Pair from, Pair to){
		this.from = from;
		this.to = to;
	}

	public Move(Castle castle){
		this.castle = true;
		which = castle;
		switch (castle) {
		case WHITEKING:
			this.from = new Pair(0,4);
			this.to = new Pair(0,6);
			break;
		case WHITEQUEEN:
			this.from = new Pair(0,4);
			this.to = new Pair(0,2);
			break;
		case BLACKKING:
			this.from = new Pair(7,4);
			this.to = new Pair(7,6);
			break;
		case BLACKQUEEN:
			this.from = new Pair(7,4);
			this.to = new Pair(7,2);
			break;
		default:
			break;
		}
	}
	
	public Move(Pair from, Pair to, Promotions promotion){
		this.from = from;
		this.to = to;
		this.promotion = promotion;
	}
	
	public Move(Pair from, Pair to, boolean isEating){
		this.from = from;
		this.to = to;
		this.isEating = isEating;
	}
	
	public Move(Pair from, Pair to, Pair enPassant, boolean isEating){
		this.from = from;
		this.to = to;
		this.enPassant = enPassant;
		this.isEating = isEating;
	}
	
	public Pair getFrom() {
		return this.from;
	}

	public Move(Pair from, Pair to, Promotions promotion, Boolean castle, boolean isEating) {
		this.from = from;
		this.to = to;
		this.promotion = promotion;
		this.castle = castle;
		this.isEating = isEating;
	}

	public static boolean isOutOfBounds(Pair pair){
		if (pair.getFirst() < 0 || pair.getFirst() >= 8){
		return true;
		}
		if (pair.getSecond() < 0 || pair.getSecond() >= 8){
		return true;
		}
		return false;
	}

	public boolean getCheck() {
		return this.check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}
	public void setFrom(Pair from) {
		this.from = from;
	}

	public Pair getTo() {
		return this.to;
	}

	public void setTo(Pair to) {
		this.to = to;
	}

	public Promotions getPromotion() {
		return this.promotion;
	}

	public void setPromotion(Promotions promotion) {
		this.promotion = promotion;
	}

	public Pair getEnPassant() {
		return enPassant;
	}

	public Boolean isCastle() {
		return this.castle;
	}

	public Boolean getCastle() {
		return this.castle;
	}
	public Castle getWhich() {
		return this.which;
	}
	public void setCastle(Boolean castle) {
		this.castle = castle;
	}

	public boolean isEating() {
		return this.isEating;
	}

	public void setIsEating(boolean isEating) {
		this.isEating = isEating;
	}
	@Override
	public String toString(){
		return "Move@" + this.hashCode() + ": " + from + " " + to;
	}
}