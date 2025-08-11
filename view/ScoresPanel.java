package view;

import javax.swing.*;
import java.awt.*;

public class ScoresPanel extends JPanel {
    private final JLabel leaderboardLabel;
    private final JLabel headerLabel;
    private final JTextArea dataTextArea;
    private final JScrollPane scrollPane;
    private final JButton goBackButton;
    private final GridBagConstraints gbc;
    public ScoresPanel(CardLayout layout, JPanel cardPanel){
        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0; // Column
        gbc.gridy = 0; // Row
        gbc.insets = new Insets(10, 10, 10, 10);
        leaderboardLabel = new JLabel("LEADERBOARD");
        add(leaderboardLabel, gbc);
        headerLabel = new JLabel("Nr.    Name    Score");
        dataTextArea = new JTextArea("Scores data is loading...");
        dataTextArea.setPreferredSize(new Dimension(250, 400));
        dataTextArea.setEditable(false);
        scrollPane = new JScrollPane(dataTextArea);
        gbc.gridy = 1;
        add(headerLabel, gbc);
        gbc.gridy = 2;
        add(scrollPane, gbc);


        goBackButton = new JButton("GO BACK TO MENU");
        goBackButton.addActionListener(e -> layout.show(cardPanel, "MP"));
        gbc.gridy = 3;
        add(goBackButton, gbc);
    }

    public void setScoresData(String scoresData){
        scoresData = scoresData.replace(" ", "  ");
        dataTextArea.setText(scoresData);
    }
}
