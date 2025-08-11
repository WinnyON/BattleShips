package view;

import javax.swing.*;
import java.awt.*;

public class TurnPanel extends JPanel {
    private final JLabel timerLabel;
    private final JLabel nameLabel;
    private final BattleBoardPanel boardPanel;
    private final RemainingPanel remainingPanel;
    private final GridBagConstraints gbc;
    private final Color color;
    public TurnPanel(int id, String playerName, int[] remainingShips){
        if(id == 1){
            color = new Color(166, 14, 14);
        }
        else{
            color = new Color(28, 115, 173);
        }
        setBackground(color);
        nameLabel = new JLabel(playerName);
        nameLabel.setForeground(Color.WHITE);
        timerLabel = new JLabel("Time left for your turn: 10s");
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setVisible(false);
        boardPanel = new BattleBoardPanel();
        remainingPanel = new RemainingPanel(color, remainingShips);
        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(nameLabel, gbc);
        gbc.gridy = 1;
        add(timerLabel, gbc);
        gbc.gridy = 2;
        add(boardPanel, gbc);
        gbc.gridy = 3;
        add(remainingPanel, gbc);
    }
    public BattleBoardPanel getBoardPanel(){
        return boardPanel;
    }
    public void setTimeLeft(int seconds){
        timerLabel.setText("Time left for your turn: " + seconds);
    }

    public void toggleHide(){
        timerLabel.setVisible(!timerLabel.isVisible());
    }
    public void updateRemainingShips(){
        remainingPanel.updateRemainingShipLabels();
    }
}
