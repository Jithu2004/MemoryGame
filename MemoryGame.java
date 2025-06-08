import javax.swing.SwingUtilities;

public class MemoryGame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(null, "🧠 Welcome to Memory Match!\nMatch all the pairs to win!", "Welcome", JOptionPane.INFORMATION_MESSAGE);
            new GameBoard();
        });
    }
}
