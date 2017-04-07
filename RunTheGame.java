package Gomoku;

import java.applet.Applet;
import java.awt.event.*;

import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;

public class RunTheGame {
	static Chessboard myBoard = new Chessboard();
	static BoardDisplay myDisplay = new BoardDisplay(myBoard);
	public static void main(String[] args){
		
		//myBoard.board[7][7] = 1;
		
		Thread myThread = new Thread(){
			public void run(){
				myDisplay.drawBoardPeople(myDisplay.boardArgu.board);
			}
		};
		myThread.start();
		//myThread.stop();
		//myDisplay.drawBoardPeople(myDisplay.boardArgu.board);
		//MyDrawPanelBoard test = new MyDrawPanelBoard(myDisplay.boardArgu.board);
		
		//test.addMouseListener(myDisplay.mouseListener);
		/*myDisplay.boardArgu.board[7][7] = 1;
		myDisplay.frame.add(test);
		myDisplay.drawBoard(myBoard.board);*/
	}
}

