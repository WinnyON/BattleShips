package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class PlaceShipsPanel extends JPanel {
    private final JLabel nameLabel;
    private final BoardPanel boardPanel;
    private final JButton submitButton;
    private JButton toggleAlignment;
    private JButton removeShip;
    private JPanel editPanel;
    private int selected = 0;
    private ShipButton[] buttonsForShips;
    private int[] shipCount;
    private int totalShips;
    ActionListener listener;
    public PlaceShipsPanel(String playerName){
        nameLabel = new JLabel(playerName + " place your ships!");
        submitButton = new JButton("Done");
        submitButton.setEnabled(false);
        submitButton.addActionListener(e -> {
            fireCustomEvent("Finished"); // save board data
        });
        boardPanel = new BoardPanel();
        shipButtons();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5); // Add padding around buttons
        gbc.gridy = 0;
        gbc.gridx = 0;
        add(nameLabel, gbc);
        gbc.gridy = 1;
        add(boardPanel, gbc);
        gbc.gridy = 2;
        // add ship buttons
        add(editPanel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(submitButton, gbc);
    }

    public void setShipCount(int[] shipCount) {
        this.shipCount = shipCount;
        totalShips = Arrays.stream(shipCount).sum();
    }

    private void shipButtons(){
        editPanel = new JPanel();
        editPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        buttonsForShips = new ShipButton[5];
        buttonsForShips[0] = new ShipButton("Destroyer", 1);
        buttonsForShips[1] = new ShipButton("Submarine", 2);
        buttonsForShips[2] = new ShipButton("Cruiser", 3);
        buttonsForShips[3] = new ShipButton("Battleship", 4);
        buttonsForShips[4] = new ShipButton("Carrier", 5);
        for(int i = 0; i < 5; i++){
            int index = buttonsForShips[i].getLength()-1;
            buttonsForShips[i].addActionListener(e -> {
                if(shipCount[index] > 0) {
                    setSelected(index+1);
                }
            });
        }
        toggleAlignment = new JButton("Horizontal");
        boardPanel.setDirection(true);
        setSelected(1);
        toggleAlignment.addActionListener(e -> {
            if(toggleAlignment.getText().equals("Horizontal")) {
                toggleAlignment.setText("Vertical");
                boardPanel.setDirection(false);
            }
            else{
                toggleAlignment.setText("Horizontal");
                boardPanel.setDirection(true);
            }
        });
        removeShip = new JButton("Remove");
        removeShip.addActionListener(e -> {
            boardPanel.setMode(1);
            setSelected(0);
        });

        gbc.gridy = 0;
        for(int i = 0; i <  5; i++){
            gbc.gridx = i;
            editPanel.add(buttonsForShips[i], gbc);
        }
        gbc.gridy = 1;
        gbc.gridx = 1;
        editPanel.add(toggleAlignment, gbc);
        gbc.gridy = 1;
        gbc.gridx = 3;
        editPanel.add(removeShip, gbc);
    }

    public void setSelected(int length){
        if(selected != -1 && buttonsForShips[selected].getState() < 2) {
            buttonsForShips[selected].setState(0);
        }
        if(length > 0 && buttonsForShips[length - 1].getState() < 2) {
            boardPanel.setMode(0);
            selected = length - 1;
            buttonsForShips[selected].setState(1);
            boardPanel.setCurrentLength(length);
        }
        else if(length <= 0){
            boardPanel.setCurrentLength(0);
        }
    }

    public void addCustomEventListener(ActionListener listener) {
        this.listener = listener;
    }
    private void fireCustomEvent(String message) {
        ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, message);
        listener.actionPerformed(event);
    }

    public BoardPanel getBoardPanel() {
        return boardPanel;
    }

    public void decreaseShipCount(){
        shipCount[selected]--;
        totalShips--;
        if(shipCount[selected] == 0){
            buttonsForShips[selected].setState(2); // disable button
            setSelected(0);
        }
        if(totalShips == 0){
            submitButton.setEnabled(true);
        }
    }

    public void increaseShipCount(){
        int length = boardPanel.getRemovedLength();
        if(shipCount[length-1] == 0){
            buttonsForShips[length-1].setState(0);
        }
        shipCount[length-1]++;
        if(totalShips == 0){
            submitButton.setEnabled(false);
        }
        totalShips++;
    }
}
