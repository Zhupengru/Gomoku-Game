package Gomoku;

public class Chessboard {
	public static int[][] board= new int[15][15];
	public static int step = 1;
	public static void newPiece(int x, int y){
		//System.out.println(x+"  "+y);	
		if(board[x/64][y/64] == 0) board[x/64][y/64] = step++;
	}
}
