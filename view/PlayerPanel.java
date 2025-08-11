package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerPanel extends JPanel {
    private String playerName;
    private final CardLayout layout;
    private PlaceShipsPanel placePanel;
    private TurnPanel turnPanel;
    private boolean finishedPlacement;
    ActionListener listener;
    private final int id;
    public PlayerPanel(int id){
        this.id = id;
        finishedPlacement = false;
        playerName = "Player " + id;
        layout = new CardLayout();
        setLayout(layout);
        EnterNamePanel namePanel = new EnterNamePanel(id, playerName, layout, this);
        add(namePanel, "NP");
    }
    public void waitTurn(){
        JPanel waitingTurnPanel = new JPanel();
        waitingTurnPanel.setBackground(Color.black);
        JLabel waitingTurnLabel = new JLabel(playerName + " is waiting for their turn to place ships");
        waitingTurnLabel.setForeground(Color.WHITE);
        waitingTurnPanel.add(waitingTurnLabel);
        add(waitingTurnPanel, "WTP");
        layout.show(this, "WTP");
    }
    public void startTurn(){
        layout.show(this, "NP");
    }
    public void hidePanel(){
        JPanel hidePlayerPanel = new JPanel();
        hidePlayerPanel.setBackground(Color.black);
        JLabel finishedPlacementLabel = new JLabel(playerName + " finished placing their ships");
        finishedPlacementLabel.setForeground(Color.WHITE);
        hidePlayerPanel.add(finishedPlacementLabel);
        add(hidePlayerPanel, "BP");
        layout.show(this, "BP");
    }


    public void createTurnPanel(int[] remainingShips){
        turnPanel = new TurnPanel(this.id, playerName, remainingShips);
        add(turnPanel, "TP");
    }
    public void showTurnPanel(){
        layout.show(this, "TP");
    }

    public void setPlayerName(String playerName){
        this.playerName = playerName;
        placePanel = new PlaceShipsPanel(playerName);
        add(placePanel, "PSP");
        fireCustomEvent("nameSet");
    }

    public String getPlayerName(){
        return this.playerName;
    }

    public boolean isFinishedPlacement() {
        return finishedPlacement;
    }
    public void setFinishedPlacement(boolean finishedPlacement){
        this.finishedPlacement = finishedPlacement;
        fireCustomEvent("finished");
    }

    public void addCustomEventListener(ActionListener listener) {
        this.listener = listener;
    }
    private void fireCustomEvent(String message) {
        ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, message);
        listener.actionPerformed(event);
    }

    public PlaceShipsPanel getPlaceShipsPanel() {
        return placePanel;
    }

    public TurnPanel getTurnPanel(){
        return turnPanel;
    }
}
