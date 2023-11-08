
//package snakegame;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    private int[] snakexlength = new int[750];
    private int[] snakeylength = new int[750];
    private int lengthofsnake = 3;

    private int[] xpos = { 25, 50, 75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475,
            500, 525, 550, 575, 600, 625, 650, 675, 700, 725, 750, 775, 800, 825, 850 };
    private int[] ypos = { 75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475, 500, 525,
            550, 575, 600, 625 };

    private Random random = new Random();
    private int foodx, foody;

    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private int moves = 0;

    private int score = 0;
    public boolean gameOver = false;

    private ImageIcon snaketitle = new ImageIcon(getClass().getResource("snaketitle.jpg"));
    private ImageIcon leftmouth = new ImageIcon(getClass().getResource("leftmouth.png"));
    private ImageIcon rightmouth = new ImageIcon(getClass().getResource("rightmouth.png"));
    private ImageIcon upmouth = new ImageIcon(getClass().getResource("upmouth.png"));
    private ImageIcon downmouth = new ImageIcon(getClass().getResource("downmouth.png"));
    private ImageIcon snakeimage = new ImageIcon(getClass().getResource("snakeimage.png"));
    private ImageIcon food = new ImageIcon(getClass().getResource("food.png"));

    private Timer timer;
    private int delay = 100;

    GamePanel() {

        addKeyListener(this);
        setFocusable(true);

        Timer timer = new Timer(delay, this);
        timer.start();

        newfood();

    }

    public void paint(Graphics g) {

        g.setColor(Color.BLACK);
        g.drawRect(24, 10, 851, 55);
        g.drawRect(24, 74, 851, 576);

        snaketitle.paintIcon(this, g, 25, 11);
        g.fillRect(25, 75, 850, 575);

        if (moves == 0) {
            snakexlength[0] = 100;
            snakexlength[1] = 75;
            snakexlength[2] = 50;
            snakeylength[0] = 100;
            snakeylength[1] = 100;
            snakeylength[2] = 100;

        }
        if (left) {
            leftmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
        }
        if (right) {
            rightmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
        }
        if (up) {
            upmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
        }
        if (down) {
            downmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
        }
        for (int i = 0; i < lengthofsnake; i++) {
            snakeimage.paintIcon(this, g, snakexlength[i], snakeylength[i]);
        }

        food.paintIcon(this, g, foodx, foody);

        if (gameOver) {

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("GAME OVER", 300, 300);

            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("PRESS SPACE  TO RESTART", 320, 350);

            g.setFont(new Font("Arial", Font.PLAIN, 30));
            g.drawString("SCORE:=" + score, 340, 400);

        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("SCORE :" + score, 750, 30);

        g.dispose();

    }

    public void actionPerformed(ActionEvent e) {
        for (int i = lengthofsnake - 1; i > 0; i--) {
            snakexlength[i] = snakexlength[i - 1];
            snakeylength[i] = snakeylength[i - 1];

        }
        if (left) {
            snakexlength[0] = snakexlength[0] - 25;
        }
        if (right) {
            snakexlength[0] = snakexlength[0] + 25;
        }
        if (up) {
            snakeylength[0] = snakeylength[0] - 25;
        }
        if (down) {
            snakeylength[0] = snakeylength[0] + 25;
        }
        if (snakexlength[0] > 850) {
            snakexlength[0] = 25;
        }
        if (snakexlength[0] < 25) {
            snakexlength[0] = 850;
        }
        if (snakeylength[0] > 625) {
            snakeylength[0] = 75;
        }
        if (snakeylength[0] < 75) {
            snakeylength[0] = 625;
        }

        collideWithFood();
        collideWithBody();

        repaint();

    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            restart();
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT && (!right)) {
            left = true;
            right = false;
            up = false;
            down = false;
            moves++;

        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && (!left)) {
            left = false;
            right = true;
            up = false;
            down = false;
            moves++;

        }
        if (e.getKeyCode() == KeyEvent.VK_UP && (!down)) {
            left = false;
            right = false;
            up = true;
            down = false;
            moves++;

        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN && (!up)) {
            left = false;
            right = false;
            up = false;
            down = true;
            moves++;

        }
    }

    public void KeyReleased(KeyEvent e) {
    }

    public void KeyTyped(KeyEvent e) {
    }

    private void newfood() {
        foodx = xpos[random.nextInt(34)];
        foody = ypos[random.nextInt(23)];

        for (int i = lengthofsnake - 1; i >= 0; i--) {
            if (snakexlength[i] == foodx && snakeylength[i] == foody) {
                newfood();
            }
        }
    }

    private void collideWithFood() {
        if (snakexlength[0] == foodx && snakeylength[0] == foody) {
            newfood();
            lengthofsnake++;
            score++;
        }
    }

    private void collideWithBody() {
        for (int i = lengthofsnake - 1; i > 0; i--) {
            if (snakexlength[i] == snakexlength[0] && snakeylength[i] == snakeylength[0]) {
                gameOver = true;
                timer.stop();

            }
        }

    }

    private void restart() {
        gameOver = false;
        moves = 0;
        score = 0;
        left = false;
        right = true;
        up = false;
        down = false;
        timer.start();
        repaint();

    }
}
