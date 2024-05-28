import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class StartWindow extends JFrame implements ActionListener {
    private JButton startButton;

    public StartWindow() {
        setTitle("Snake Game Start");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        startButton = new JButton("Start Game");
        startButton.addActionListener(this);
        
        //StartGame button
        Color veryDarkGreen = new Color(0, 30, 0);
        startButton.setPreferredSize(new Dimension(200, 100));
        startButton.setFont(new Font("Serif", Font.BOLD, 30));
        startButton.setBackground(veryDarkGreen);
        startButton.setForeground(Color.red);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(startButton, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            this.dispose(); // Close the start window
            SwingUtilities.invokeLater(() -> {
                try {
                    App.startGame();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StartWindow startWindow = new StartWindow();
            startWindow.setVisible(true);
        });
    }
}
