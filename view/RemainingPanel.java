package view;

import javax.swing.*;
import java.awt.*;

public class RemainingPanel extends JPanel {
    private final JLabel remainingLabel;
    private final JLabel[] ships;
    private final int[] remainingShips;
    GridBagConstraints gbc;
    public RemainingPanel(Color color, int[] remainingShips){
        setBackground(color);
        remainingLabel = new JLabel("Remaining enemy ships:");
        remainingLabel.setForeground(Color.WHITE);
        ships = new JLabel[5];
        this.remainingShips = remainingShips;
        ships[0] = new JLabel("Destroyer(1): " + remainingShips[0]);
        ships[1] = new JLabel("Submarine(2): " + remainingShips[1]);
        ships[2] = new JLabel("Cruiser(3): " + remainingShips[2]);
        ships[3] = new JLabel("Battleship(4): " + remainingShips[3]);
        ships[4] = new JLabel("Carrier(5): " + remainingShips[4]);
        gbc = new GridBagConstraints();
        setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(remainingLabel, gbc);
        for(int i = 0; i < 5; i++){
            gbc.gridy = i + 1;
            ships[i].setForeground(Color.WHITE);
            add(ships[i], gbc);
        }
    }

    public void updateRemainingShipLabels(){
        ships[0].setText("Destroyer(1): " + remainingShips[0]);
        ships[1].setText("Submarine(2): " + remainingShips[1]);
        ships[2].setText("Cruiser(3): " + remainingShips[2]);
        ships[3].setText("Battleship(4): " + remainingShips[3]);
        ships[4].setText("Carrier(5): " + remainingShips[4]);
    }
}
