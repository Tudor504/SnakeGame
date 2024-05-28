import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        StartWindow.main(args); // Show the start window
    }

    public static void startGame() throws Exception {
        int boardWidth = 400;
        int boardHeight = 400;

        JFrame frame = new JFrame("Snake");
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        SnakeGame snakeGame = new SnakeGame(boardWidth, boardHeight);
        frame.add(snakeGame);
        frame.pack();
        snakeGame.requestFocus();
    }
}
