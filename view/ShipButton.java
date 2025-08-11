package view;

import javax.swing.*;
import java.awt.*;

public class ShipButton extends JButton {
    private final int length;
    private int state;
    // 0 - active
    // 1 - selected
    // 2 - disabled
    public ShipButton(String name, int length){
        super(name);
        this.length = length;
        setState(0);
    }

    public int getLength() {
        return length;
    }

    public int getState(){
        return state;
    }
    public void setState(int state){
        this.state = state;
        setEnabled(true);
        if(state == 0){
            setBackground(Color.LIGHT_GRAY);
        }
        else if(state == 1){
            setBackground(Color.DARK_GRAY);
        }
        else {
            setBackground(Color.RED);
            setEnabled(false);
        }
    }


}
