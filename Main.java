//package snakegame;
import java.awt.Color;
import javax.swing.*;

public class Main{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("SNAKE GAME");
		frame.setBounds(10,10,905,700);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GamePanel panel = new GamePanel();
        panel.setBackground(Color.DARK_GRAY);
        frame.add(panel);
		
		frame.setVisible(true);

		
	}

}
