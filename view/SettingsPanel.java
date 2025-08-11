package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsPanel extends JPanel {
    private final JSlider musicSlider;
    private final JSlider effectSlider;
    ActionListener listener;
    public SettingsPanel(CardLayout layout, JPanel cardPanel){
        setLayout(new GridBagLayout());
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        JLabel settingsTitle = new JLabel("SETTINGS");
        add(settingsTitle, gbc);
        // add music slider
        gbc.gridy = 1;
        JLabel musicLabel = new JLabel("Background Music");
        optionsPanel.add(musicLabel, gbc);
        musicSlider = new JSlider(0, 100, 50);
        musicSlider.setMajorTickSpacing(100);
        musicSlider.setPaintLabels(true);
        musicSlider.addChangeListener(e -> fireCustomEvent("music"));
        gbc.gridx = 1;
        optionsPanel.add(musicSlider, gbc);
        // add sound effects slider
        gbc.gridy = 2;
        gbc.gridx = 0;
        JLabel effectLabel = new JLabel("Sound effects");
        optionsPanel.add(effectLabel, gbc);
        effectSlider = new JSlider(0, 100, 50);
        effectSlider.setMajorTickSpacing(100);
        effectSlider.setPaintLabels(true);
        effectSlider.addChangeListener(e -> fireCustomEvent("effect"));
        gbc.gridx = 1;
        optionsPanel.add(effectSlider, gbc);
        gbc.gridy = 1;
        gbc.gridx = 0;
        add(optionsPanel, gbc);
        JButton goBackButton = new JButton("GO BACK TO MENU");
        goBackButton.addActionListener(e -> layout.show(cardPanel, "MP"));
        gbc.gridy = 2;
        add(goBackButton, gbc);
    }

    public float getMusicVolume(){
        return (musicSlider.getValue() - 80) > 0 ? 0.0f : (float) (musicSlider.getValue() - 80);
    }
    public float getEffectVolume(){
        return (effectSlider.getValue() - 80) > 0 ? 0.0f : (float) (effectSlider.getValue() - 80);
    }
    public void addCustomEventListener(ActionListener listener) {
        this.listener = listener;
    }
    private void fireCustomEvent(String message) {
        ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, message);
        listener.actionPerformed(event);
    }
}
