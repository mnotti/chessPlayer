
public class PieceFactory {
	public Piece createPiece(String piece, int[] position){
		switch(piece){
			case "P": return new Pawn(Color.White, position);
			case "p": return new Pawn(Color.Black, position);
			case "R": return new Rook(Color.White, position);
			case "r": return new Rook(Color.Black, position);
			case "N": return new Knight(Color.White, position);
			case "n": return new Knight(Color.Black, position);
			case "B": return new Bishop(Color.White, position);
			case "b": return new Bishop(Color.Black, position);
			case "K": return new King(Color.White, position);
			case "k": return new King(Color.Black, position);
			case "Q": return new Queen(Color.White, position);
			case "q": return new Queen(Color.Black, position);
			default: return null;
		}
	}
}
