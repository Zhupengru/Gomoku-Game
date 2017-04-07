package Gomoku;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class ImageTest {

	public static void main(String[] args) {

		JFrame frame = new JFrame();
		ImageIcon icon = new ImageIcon("2009.jpg");
		JLabel label = new JLabel(icon);
		frame.add(label);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}
}
