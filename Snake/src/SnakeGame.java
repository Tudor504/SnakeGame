import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    private class Tile {
        int x;
        int y;

        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    int boardWidth;
    int boardHeight;
    int tileSize = 20;
    //snake
    Tile snakeHead;
    ArrayList<Tile> snakeBody;

    //food
    Tile food;
    Random random;

    //game logic
    Timer gameLoop;
    int velocityX;
    int velocityY;
    boolean gameOver = false;

    SnakeGame(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        Color veryDarkGreen = new Color(0, 30, 0);
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(veryDarkGreen);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(5, 5);
        snakeBody = new ArrayList<Tile>();

        food = new Tile(12, 12);
        random = new Random();
        placeFood();

        velocityX = 0;
        velocityY = 0;
        gameLoop = new Timer(120, this);
        gameLoop.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.setColor(Color.lightGray);
        g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize);

        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize);
        }

        g.setColor(Color.red);
        g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize);

        g.setFont(new Font("Serif", Font.BOLD, 16));
        if (gameOver) {
            g.setColor(Color.red);
            g.drawString("GAME OVER: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        } else {
            g.setColor(Color.yellow);
            g.drawString("SCORE: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
    }

    public void placeFood() {
        food.x = random.nextInt(boardWidth / tileSize);
        food.y = random.nextInt(boardHeight / tileSize);
    }

    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public Tile getLastSegment() {
        if (snakeBody.isEmpty()) {
            return snakeHead;
        }
        return snakeBody.get(snakeBody.size());
    }

    public void move() {
        if (collision(snakeHead, food)) {
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }
        for (int i = snakeBody.size() - 1; i >= 0; i--) {
            Tile snakePart = snakeBody.get(i);
            if (i == 0) {
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            } else {
                Tile prevSnakePart = snakeBody.get(i - 1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }
        
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        // Check wall collision and add segment
        if (snakeHead.x < 0)
        {
            Tile lastSegment = getLastSegment();
            snakeBody.add(new Tile(lastSegment.x + 1, lastSegment.y ));
            gameOver = true;
        }
        else if (snakeHead.x >= boardWidth / tileSize)
        {
            Tile lastSegment = getLastSegment();
            snakeBody.add(new Tile(lastSegment.x - 1, lastSegment.y ));
            gameOver = true;
        }
        else if ( snakeHead.y < 0 )
        {
            Tile lastSegment = getLastSegment();
            snakeBody.add(new Tile(lastSegment.x , lastSegment.y + 1));
            gameOver = true;
        }
        else if (snakeHead.y >= boardHeight / tileSize) 
        {
            Tile lastSegment = getLastSegment();
            snakeBody.add(new Tile(lastSegment.x, lastSegment.y - 1));
            gameOver = true;
        }

        // Check self collision
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            if (collision(snakeHead, snakePart)) {
                gameOver = true;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver)
            gameLoop.stop();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if ((e.getKeyCode() == KeyEvent.VK_W && velocityY != 1) || (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1)) {
            velocityX = 0;
            velocityY = -1;
        } else if ((e.getKeyCode() == KeyEvent.VK_A && velocityX != 1) || (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1)) {
            velocityX = -1;
            velocityY = 0;
        } else if ((e.getKeyCode() == KeyEvent.VK_S && velocityY != -1) || (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1)) {
            velocityX = 0;
            velocityY = 1;
        } else if ((e.getKeyCode() == KeyEvent.VK_D && velocityX != -1) || (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1)) {
            velocityX = 1;
            velocityY = 0;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
