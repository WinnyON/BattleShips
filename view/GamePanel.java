package view;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private PlayerPanel player1;
    private PlayerPanel player2;
    public GamePanel(){
        player1 = new PlayerPanel(1);
        player2 = new PlayerPanel(2);
        setLayout(new GridLayout(1, 2));
        add(player1);
        add(player2);
    }

    public PlayerPanel getPlayer1() {
        return player1;
    }

    public PlayerPanel getPlayer2() {
        return player2;
    }
}
