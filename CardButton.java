import java.awt.*;
import javax.swing.*;

public class CardButton extends JButton {
    private final int id;
    private boolean matched = false;

    public CardButton(int id) {
        this.id = id;
        setFont(new Font("Arial", Font.BOLD, 24));
        setText(""); // initially hidden
        setFocusPainted(false);
        setBackground(Color.LIGHT_GRAY);
    }

    public int getId() {
        return id;
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
        setEnabled(false);
        setBackground(new Color(102, 255, 153)); // green for matched
    }

    public void reveal() {
        setText(String.valueOf(id));
        setBackground(Color.YELLOW);
    }

    public void hide() {
        setText("");
        setBackground(Color.LIGHT_GRAY);
    }
}
