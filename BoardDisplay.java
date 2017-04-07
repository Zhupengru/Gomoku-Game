package Gomoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BoardDisplay {
	static Chessboard boardArgu;
	static JFrame frame = new JFrame("Gomoku Game");
	static MyDrawPanelBoard drawBoardPanel;
	static WinSituation checkwin;
	static MouseListener mouseListener = new MouseListener(){
		public void mouseClicked(MouseEvent e){
			if (SwingUtilities.isLeftMouseButton(e)){
				Thread myThread = new Thread(){
					public void run(){
						drawBoardPeople(boardArgu.board);
					}
				};
				Thread myThread1 = new Thread(){
					public void run(){
						drawBoardPeople(boardArgu.board);
					}
				};
				int x = e.getX();
				int y = e.getY();
				boardArgu.newPiece(x, y);
				System.out.println("step: "+ boardArgu.step);
				//myThread1.start();
				//drawBoardPeople(boardArgu.board);
				checkwin = new WinSituation();
				String winner;
				winner = checkwin.judge(boardArgu.board);
				if (winner.equals("BLACK")) {
					System.out.println("BLACK WIN!!");
					drawBoardPeople(boardArgu.board);
					drawBoardPanel.removeMouseListener(mouseListener);
					//frame.setVisible(false); //you can't see me!
					//frame.dispose();
					frame = new JFrame("Black player Win!!!");
					frame.setSize(960, 975);
					ImageIcon icon = new ImageIcon("2009.jpg");
					JLabel label = new JLabel(icon);
					frame.add(label);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.pack();
					frame.setVisible(true);
					//System.exit(0);
				}
				else if (winner.equals("WHITE")) {
					System.out.println("WHITE WIN!!");
					drawBoardPeople(boardArgu.board);
					drawBoardPanel.removeMouseListener(mouseListener);
					//frame.setVisible(false); //you can't see me!
					//frame.dispose();
					frame = new JFrame("White player Win!!!");
					frame.setSize(960, 975);
					ImageIcon icon = new ImageIcon("2009.jpg");
					JLabel label = new JLabel(icon);
					frame.add(label);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.pack();
					frame.setVisible(true);
					//System.exit(0);
				}
				else {
					
					int[] next = new int[2];
					SmartAI2 AI = new SmartAI2();
					next = AI.nextStep(boardArgu.board, boardArgu.step);
					boardArgu.newPiece(next[0]*64, next[1]*64);
					
					myThread.start();
					//drawBoardPeople(boardArgu.board);
					
					//System.out.println("step: "+ boardArgu.step);
					winner = checkwin.judge(boardArgu.board);
					if (winner.equals("BLACK")) {
						System.out.println("BLACK WIN!!");
						//drawBoardPeople(boardArgu.board);
						drawBoardPanel.removeMouseListener(mouseListener);
						//frame.setVisible(false); //you can't see me!
						//frame.dispose();
						frame = new JFrame("Black player Win!!!");
						frame.setSize(960, 975);
						ImageIcon icon = new ImageIcon("2009.jpg");
						JLabel label = new JLabel(icon);
						frame.add(label);
						frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						frame.pack();
						frame.setVisible(true);
						//System.exit(0);
					}
					else if (winner.equals("WHITE")) {
						System.out.println("WHITE WIN!!");
						//myThread1.start();
						//myThread1.stop();
						//drawBoardPeople(boardArgu.board);
						drawBoardPanel.removeMouseListener(mouseListener);
						//frame.setVisible(false); //you can't see me!
						//frame.dispose();
						frame = new JFrame("White player Win!!!");
						frame.setSize(960, 975);
						ImageIcon icon = new ImageIcon("2009.jpg");
						JLabel label = new JLabel(icon);
						frame.add(label);
						frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						frame.pack();
						frame.setVisible(true);
						//System.exit(0);
					}
				}
			}
		}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
	};
	
	BoardDisplay(){}
	BoardDisplay(Chessboard myBoard){
		boardArgu = myBoard;
	}
	public static void drawBoardPeople(int[][] board) {
		drawBoardPanel = new MyDrawPanelBoard(board);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(drawBoardPanel);
		frame.setSize(960, 975);
		frame.setVisible(true);
		drawBoardPanel.repaint();
		drawBoardPanel.addMouseListener(mouseListener);
	}
}

class MyDrawPanelBoard extends JPanel {
	int[][] boardToDraw = new int[15][15];
	MyDrawPanelBoard(int[][] board){
		for (int i = 0; i < 15; i++){
			for (int j = 0; j < 15; j++){
				boardToDraw[i][j] = board[i][j];
				//System.out.print(board[i][j]+" ");
			}
			//System.out.println();
		}
	}
	public void paintComponent(Graphics g) {
		//base
		g.setColor(Color.gray);
		g.fillRect(0, 0, 1024, 1024);
		//line
		for (int i = 0; i < 14; i++) {
			for (int j = 0; j < 14; j++) {
				if (i == 13 && j < 13) {
					g.setColor(Color.black);
					g.fillRect(i * 64 + 30, j * 64 + 25, 64, 64);
					g.setColor(Color.white);
					g.fillRect(i * 64 + 1 + 30, j * 64 + 1 + 25, 62, 63);
				} else if (j == 13 && i < 13) {
					g.setColor(Color.black);
					g.fillRect(i * 64 + 30, j * 64 + 25, 64, 64);
					g.setColor(Color.white);
					g.fillRect(i * 64 + 1 + 30, j * 64 + 1 + 25, 63, 62);
				} else if (i == 13 && j == 13) {
					g.setColor(Color.black);
					g.fillRect(i * 64 + 30, j * 64 + 25, 64, 64);
					g.setColor(Color.white);
					g.fillRect(i * 64 + 1 + 30, j * 64 + 1 + 25, 62, 62);
				} else {
					g.setColor(Color.black);
					g.fillRect(i * 64 + 30, j * 64 + 25, 64, 64);
					g.setColor(Color.white);
					g.fillRect(i * 64 + 1 + 30, j * 64 + 1 + 25, 63, 63);
				}
			}
		}
		//base black circle
		g.setColor(Color.black);
		g.fillOval(64*3 + 23, 64*3 + 18, 14, 14);
		g.fillOval(64*11 + 23, 64*3 + 18, 14, 14);
		g.fillOval(64*3 + 23, 64*11 + 18, 14, 14);
		g.fillOval(64*11 + 23, 64*11 + 18, 14, 14);
		g.fillOval(64*7 + 23, 64*7 + 18, 14, 14);
		//chess piece
		int max = 0;
		for (int i = 0; i < 15; i++)
			for (int j = 0; j < 15; j++)
				max = Math.max(max, boardToDraw[i][j]); 
		
		for (int i = 0; i < 15; i++){
			for (int j = 0; j < 15; j++){
				if (boardToDraw[i][j] > 0){
					if (boardToDraw[i][j] % 2 == 1){
						g.setColor(Color.black);
						g.fillOval(i*64+3, j*64-2, 54,54);
					} else{
						g.setColor(Color.black);
						g.fillOval(i*64+3, j*64-2, 54,54);
						g.setColor(Color.white);
						g.fillOval(i*64+3+1, j*64-2+1, 52,52);
					}
					if (boardToDraw[i][j] == max){
						g.setColor(Color.red);
						g.fillRect(i*64+3+24, j*64-2+24, 6, 6);
					}
				}
			}
		}
	}
}


