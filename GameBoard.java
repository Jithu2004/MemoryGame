import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GameBoard extends JFrame {
    private final int SIZE = 4;
    private CardButton first, second;
    private boolean canClick = true;
    private javax.swing.Timer flipBackTimer;
    private javax.swing.Timer gameTimer;
    private int matchedPairs = 0;
    private int moves = 0;
    private int timeElapsed = 0;

    private JLabel movesLabel, timerLabel;
    private JButton restartButton;

    public GameBoard() {
        setTitle("🎮 Memory Match Game");
        setSize(480, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Top panel (timer + moves + restart)
        JPanel topPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        movesLabel = new JLabel("Moves: 0", SwingConstants.CENTER);
        timerLabel = new JLabel("Time: 0s", SwingConstants.CENTER);

        restartButton = new JButton("Restart 🔁");
        restartButton.addActionListener(e -> restartGame());

        topPanel.add(movesLabel);
        topPanel.add(timerLabel);
        topPanel.add(restartButton);

        add(topPanel, BorderLayout.NORTH);

        // Create card grid
        JPanel gamePanel = new JPanel(new GridLayout(SIZE, SIZE, 5, 5));
        gamePanel.setBackground(Color.DARK_GRAY);

        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < (SIZE * SIZE) / 2; i++) {
            ids.add(i);
            ids.add(i);
        }
        Collections.shuffle(ids);

        for (int id : ids) {
            CardButton card = new CardButton(id);
            card.addActionListener(e -> onCardClick(card));
            gamePanel.add(card);
        }

        add(gamePanel, BorderLayout.CENTER);
        startGameTimer();
        setVisible(true);
    }

    private void onCardClick(CardButton card) {
        if (!canClick || card.isMatched() || card == first || card.getText().length() > 0) return;

        card.reveal();

        if (first == null) {
            first = card;
        } else {
            second = card;
            canClick = false;
            moves++;
            movesLabel.setText("Moves: " + moves);

            if (first.getId() == second.getId()) {
                first.setMatched(true);
                second.setMatched(true);
                matchedPairs++;
                reset();

                if (matchedPairs == (SIZE * SIZE) / 2) {
                    gameTimer.stop();
                    JOptionPane.showMessageDialog(this,
                            "🎉 You won!\nMoves: " + moves + "\nTime: " + timeElapsed + " seconds.",
                            "Victory", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                flipBackTimer = new javax.swing.Timer(1000, e -> {
                    first.hide();
                    second.hide();
                    reset();
                });
                flipBackTimer.setRepeats(false);
                flipBackTimer.start();
            }
        }
    }

    private void reset() {
        first = null;
        second = null;
        canClick = true;
    }

    private void startGameTimer() {
        gameTimer = new javax.swing.Timer(1000, e -> {
            timeElapsed++;
            timerLabel.setText("Time: " + timeElapsed + "s");
        });
        gameTimer.start();
    }

    private void restartGame() {
        dispose(); // close current window
        new GameBoard(); // open new one
    }
}
