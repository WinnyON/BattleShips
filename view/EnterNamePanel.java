package view;

import javax.swing.*;
import java.awt.*;

public class EnterNamePanel extends JPanel {
    private final JTextField nameField;
    public EnterNamePanel(int id, String playerName, CardLayout layout, PlayerPanel playerPanel){
        setLayout(new GridBagLayout());
        if(id == 1) {
            setBackground(new Color(166, 14, 14));
        }
        else{
            setBackground(new Color(28, 115, 173));
        }
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5); // Add padding around buttons
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel nameLabel = new JLabel("Enter your name");
        nameLabel.setForeground(Color.WHITE);
        add(nameLabel, gbc);
        gbc.gridy = 1;
        nameField = new JTextField(playerName, 15);
        add(nameField, gbc);
        gbc.gridy = 2;
        JButton submitButton = new JButton("Submit");
        add(submitButton, gbc);
        submitButton.addActionListener(e -> {
                                            playerPanel.setPlayerName(nameField.getText().replace(" ", "_"));
                                            layout.show(playerPanel, "PSP");
                                            });
    }
}
