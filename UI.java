package all;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class UI extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Color BG_COLOR = new Color(0xbbada0);
	private static final String FONT_NAME = "Arial";
	private static final int TILE_SIZE = 50;
	private static final int TILES_MARGIN = 1;
	private static final int BOARD_MARGIN = 35;

	private static int[][] myBoard = new int[15][15];
	private static int step = 1;

	private ComputeBoard checkWinner = new ComputeBoard();

	public UI() {
		setPreferredSize(new Dimension(2 * BOARD_MARGIN + 14 * TILE_SIZE + 15 * TILES_MARGIN,
				2 * BOARD_MARGIN + 14 * TILE_SIZE + 15 * TILES_MARGIN + 20));
		setFocusable(true);
		addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					myBoard = new int[15][15];
				}
				repaint();
			}

		});

		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				int row = (int) Math.round((double) (x - BOARD_MARGIN) / (double) (TILE_SIZE + TILES_MARGIN));
				int col = (int) Math.round((double) (y - BOARD_MARGIN) / (double) (TILE_SIZE + TILES_MARGIN));
				myBoard[row][col] = step++;
				repaint();

			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

		});
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(BG_COLOR);
		g.fillRect(0, 0, this.getSize().width, this.getSize().height);
		for (int y = 0; y < 14; y++) {
			for (int x = 0; x < 14; x++) {
				drawTile(g, x, y);
			}
		}
		for (int y = 0; y < 15; y++) {
			for (int x = 0; x < 15; x++) {
				if (myBoard[x][y] > 0) {
					if (myBoard[x][y] % 2 == 0)
						drawWhiteChess(g, x, y);
					else
						drawBlackChess(g, x, y);
				}
				if (myBoard[x][y] > 1 && myBoard[x][y] == step - 1)
					drawMark(g, x, y);
			}
		}

		drawWinner(g);
	}

	private void drawTile(Graphics g2, int x, int y) {
		Graphics2D g = ((Graphics2D) g2);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
		int xOffset = offsetCoors(x);
		int yOffset = offsetCoors(y);
		g.setColor(new Color(0x776e65));
		g.fillRect(xOffset, yOffset, TILE_SIZE, TILE_SIZE);
		g.setColor(new Color(0xeee4da));
	}

	private void drawWhiteChess(Graphics g2, int x, int y) {
		Graphics2D g = ((Graphics2D) g2);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
		int xOffset = offsetCoors(x) - TILE_SIZE / 2 - TILES_MARGIN;
		int yOffset = offsetCoors(y) - TILE_SIZE / 2 - TILES_MARGIN;
		g.setColor(Color.BLACK);
		g.fillOval(xOffset, yOffset, TILE_SIZE - 1, TILE_SIZE - 1);
		g.setColor(Color.LIGHT_GRAY);
		g.fillOval(xOffset + 1, yOffset + 1, TILE_SIZE - 4, TILE_SIZE - 4);
		g.setColor(Color.WHITE);
		g.fillOval(xOffset + 15, yOffset + 5, 15, 4);
	}

	private void drawBlackChess(Graphics g2, int x, int y) {
		Graphics2D g = ((Graphics2D) g2);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
		int xOffset = offsetCoors(x) - TILE_SIZE / 2 - TILES_MARGIN;
		int yOffset = offsetCoors(y) - TILE_SIZE / 2 - TILES_MARGIN;
		g.setColor(Color.BLACK);
		g.fillOval(xOffset, yOffset, TILE_SIZE - 1, TILE_SIZE - 1);
		g.setColor(Color.WHITE);
		g.fillOval(xOffset + 15, yOffset + 5, 15, 4);
	}

	private void drawMark(Graphics g2, int x, int y) {
		Graphics2D g = ((Graphics2D) g2);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
		int xOffset = offsetCoors(x) - 3;
		int yOffset = offsetCoors(y) - 3;
		g.setColor(Color.RED);
		g.fillOval(xOffset, yOffset, 4, 4);
	}

	private void drawWinner(Graphics g2) {
		String winner;
		winner = checkWinner.judge(myBoard);
		Graphics2D g = ((Graphics2D) g2);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
		if (!winner.equals("")) {
			myBoard = new int[15][15];
			g.setColor(new Color(255, 255, 255, 85));
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(Color.ORANGE);
			g.setFont(new Font(FONT_NAME, Font.BOLD, 72));
			if (winner.equals("WHITE")) {
				g.drawString("White Player Won!", 80, 200);
			} else if (winner.equals("BLACK")) {
				g.drawString("Black Player Won!", 80, 200);
			}

			g.setFont(new Font(FONT_NAME, Font.PLAIN, 20));
			g.setColor(Color.WHITE);
			g.drawString("Press ESC to play again", 80, getHeight() - 40);
		}

	}

	private static int offsetCoors(int arg) {
		return arg * (TILES_MARGIN + TILE_SIZE) + TILES_MARGIN + BOARD_MARGIN;
	}

	public static void main(String[] args) {
		JFrame game = new JFrame();
		game.setTitle("Îå×ÓÆå ---V1.0");
		game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		game.setSize(2 * BOARD_MARGIN + 14 * TILE_SIZE + 15 * TILES_MARGIN,
				2 * BOARD_MARGIN + 14 * TILE_SIZE + 15 * TILES_MARGIN + 20);
		game.setResizable(false);

		game.add(new UI());

		game.setLocationRelativeTo(null);
		game.setVisible(true);
	}

}
