package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MenuPanel extends JPanel {

    private Image backgroundImage;
    ActionListener listener;
    public MenuPanel(CardLayout layout, JPanel cardPanel){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        //gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; // Column
        gbc.gridy = 0; // Row
        gbc.weightx = 1.0; // Allow the button to grow horizontally
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding around buttons
        setBackground(new Color(0, 0, 139));

        try {
            backgroundImage = ImageIO.read(new File("assets/menuBackground.png")); // Update the path to your image
        } catch (IOException e) {
            e.printStackTrace();
        }

        JButton startButton = new JButton("START GAME");
        startButton.setSize(100, 40);
        startButton.addActionListener(e -> {
            fireCustomEvent("2");
            layout.show(cardPanel, "GP");
        });
        JButton settingsButton = new JButton("SETTINGS");
        settingsButton.setSize(100, 40);
        settingsButton.addActionListener(e -> layout.show(cardPanel, "SP"));
        JButton scoresButton = new JButton("SCORES");
        scoresButton.setSize(100, 40);
        scoresButton.addActionListener(e -> {
            fireCustomEvent("1");
            layout.show(cardPanel, "SCP");
        });

        add(startButton, gbc);
        gbc.gridy = 1;
        add(settingsButton, gbc);
        gbc.gridy = 2;
        add(scoresButton, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public void addCustomEventListener(ActionListener listener) {
        this.listener = listener;
    }
    private void fireCustomEvent(String message) {
        ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, message);
        listener.actionPerformed(event);
    }
}
