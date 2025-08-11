package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOverPanel extends JPanel {
    private final JLabel resultLabel;
    private final JLabel placementLabel;
    private final JButton backToMenuButton;
    private final JButton leaderboardButton;
    private final GridBagConstraints gbc;
    ActionListener listener;
    public GameOverPanel(String winner, int placement){
        placementLabel = new JLabel("You are " + placement + "th in the leaderboard");
        placementLabel.setForeground(Color.BLACK);
        placementLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        resultLabel = new JLabel("The winner is " + winner);
        resultLabel.setForeground(Color.BLACK);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 30));
        backToMenuButton = new JButton("GO BACK TO MENU");
        backToMenuButton.addActionListener(e -> fireCustomEvent("menu"));
        leaderboardButton = new JButton("CHECK OUT THE LEADERBOARD");
        leaderboardButton.addActionListener(e -> fireCustomEvent("scores"));
        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        setBackground(new Color(245, 206, 34));
        gbc.gridy = 0;
        add(resultLabel, gbc);
        gbc.gridy = 1;
        add(placementLabel, gbc);
        gbc.gridy = 2;
        add(leaderboardButton, gbc);
        gbc.gridy = 3;
        add(backToMenuButton, gbc);
    }

    public void addCustomEventListener(ActionListener listener) {
        this.listener = listener;
    }
    private void fireCustomEvent(String message) {
        ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, message);
        listener.actionPerformed(event);
    }
}
