package view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final CardLayout layout;
    private final JPanel cardPanel;
    private final MenuPanel menuPanel;
    private final SettingsPanel settingsPanel;
    private final ScoresPanel scoresPanel;
    private GamePanel gamePanel;
    private GameOverPanel gameOverPanel;

    public MainFrame(){
        setTitle("Battleships");
        setBounds(100, 100, 1080, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        layout = new CardLayout();
        cardPanel = new JPanel();
        cardPanel.setLayout(layout);
        add(cardPanel);

        menuPanel = new MenuPanel(layout, cardPanel);
        cardPanel.add(menuPanel, "MP");

        settingsPanel = new SettingsPanel(layout, cardPanel);
        cardPanel.add(settingsPanel, "SP");

        scoresPanel = new ScoresPanel(layout, cardPanel);
        cardPanel.add(scoresPanel, "SCP");

        gamePanel = new GamePanel();
        cardPanel.add(gamePanel, "GP");

        setVisible(true);
    }

    @Override
    public CardLayout getLayout() {
        return layout;
    }

    public GameOverPanel getGameOverPanel(){
        return gameOverPanel;
    }
    public void showGameOverPanel(String name, int placement){
        gameOverPanel = new GameOverPanel(name, placement);
        cardPanel.add(gameOverPanel, "GOP");
        layout.show(cardPanel, "GOP");
    }

    public MenuPanel getMenuPanel() {
        return menuPanel;
    }

    public void showMenuPanel(){
        layout.show(cardPanel, "MP");
    }

    public void showScoresPanel(){
        layout.show(cardPanel, "SCP");
    }

    public SettingsPanel getSettingsPanel() {
        return settingsPanel;
    }

    public ScoresPanel getScoresPanel() {
        return scoresPanel;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public void resetGamePanel(){
        cardPanel.remove(gamePanel);
        gamePanel = new GamePanel();
        cardPanel.add(gamePanel, "GP");
    }
}
