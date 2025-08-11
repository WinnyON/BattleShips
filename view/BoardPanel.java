package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class BoardPanel extends JPanel {
    private static final int GRID_SIZE = 10;
    private static final int CELL_SIZE = 30;

    private final int[][] grid = new int[GRID_SIZE][GRID_SIZE];

    private int currentLength;
    private int removedLength;
    private boolean direction;
    private List<int[]> previous;
    private int mode;
    // 0 - place
    // 1 - remove
    ActionListener listener;
    public BoardPanel() {
        mode = 0;
        setPreferredSize(new Dimension(GRID_SIZE * CELL_SIZE, GRID_SIZE * CELL_SIZE));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                resetHover();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                int col = e.getX() / CELL_SIZE;
                int row = e.getY() / CELL_SIZE;

                handleClick(row, col);
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if(mode == 1){ // no need for hover effect in remove mode
                    return;
                }
                int col = e.getX() / CELL_SIZE;
                int row = e.getY() / CELL_SIZE;
                handleHover(row, col);
            }
        });
    }

    private void handleHover(int row, int col) {
        resetPrevious();
        if (row < GRID_SIZE && col < GRID_SIZE) {
            if(direction && col + currentLength <= GRID_SIZE) // horizontal
            {
                previous = new ArrayList<>();
                for(int i = col; i < col + currentLength; i++){
                    if(grid[row][i] != 0 || isConflicting(row, i)){
                        resetPrevious();
                        previous = null;
                        break;
                    }
                    grid[row][i] = 3;
                    previous.add(new int[]{row, i});
                }
            }
            else if(!direction && row + currentLength <= GRID_SIZE) // vertical
            {
                previous = new ArrayList<>();
                for(int i = row; i < row + currentLength; i++){
                    if(grid[i][col] != 0 || isConflicting(i, col)){
                        resetPrevious();
                        previous = null;
                        break;
                    }
                    grid[i][col] = 3;
                    previous.add(new int[]{i, col});
                }
            }
            repaint();
        }
    }

    private void handleClick(int row, int col) {
        if (row < GRID_SIZE && col < GRID_SIZE) {
            if(mode == 1){ // remove mode
                if(grid[row][col] == 4){ // there is a ship on the coordinate
                    // count number of cells reset
                    // modify how many ships are left
                    removedLength = removeShip(row, col);
                    fireCustomEvent("inc"); // increase ship count
                }
            }
            if(previous != null){
                fireCustomEvent("dec"); // decrease ship count
                for(int[] arr : previous){
                    grid[arr[0]][arr[1]] = 4;
                }
                previous = null;
            }
            repaint();
        }
    }

    public void resetHover(){
        for(int i = 0; i < GRID_SIZE; i++){
            for(int j = 0; j < GRID_SIZE; j++){
                if(grid[i][j] == 3){
                    grid[i][j] = 0;
                }
            }
        }
        previous = null;
        repaint();
    }
    public int removeShip(int row, int col){
        int cellsModified = 1;
        grid[row][col] = 0;
        int i = row-1;
        int j = col;
        while(i >= 0 && grid[i][j] == 4){
            cellsModified++;
            grid[i][j] = 0;
            i--;
        }
        i = row+1;
        while(i < GRID_SIZE && grid[i][j] == 4){
            cellsModified++;
            grid[i][j] = 0;
            i++;
        }
        i = row;
        j = col - 1;
        while(j >= 0 && grid[i][j] == 4){
            cellsModified++;
            grid[i][j] = 0;
            j--;
        }
        j = col + 1;
        while(j < GRID_SIZE && grid[i][j] == 4){
            cellsModified++;
            grid[i][j] = 0;
            j++;
        }
        return cellsModified;
    }
    public boolean isConflicting(int i, int j){
        if(i > 0){

            if(grid[i-1][j] == 4){
                return true;
            }
            if(j > 0) {
                if (grid[i - 1][j - 1] == 4 || grid[i][j - 1] == 4) {
                    return true;
                }
            }
            if (j < GRID_SIZE-1){
                if(grid[i-1][j+1] == 4 || grid[i][j+1] == 4){
                    return true;
                }
            }
        }
        if(i < GRID_SIZE - 1){
            if(grid[i+1][j] == 4){
                return true;
            }
            if(j > 0) {
                if (grid[i + 1][j - 1] == 4 || grid[i][j - 1] == 4) {
                    return true;
                }
            }
            if (j < GRID_SIZE-1){
                if(grid[i + 1][j + 1] == 4 || grid[i][j + 1] == 4){
                    return true;
                }
            }
        }
        return false;
    }
    public void resetPrevious(){
        if(previous != null){
            for(int[] arr : previous){
                grid[arr[0]][arr[1]] = 0;
            }
        }
    }

    public void setCurrentLength(int currentLength){
        this.currentLength = currentLength;
    }

    public int getRemovedLength() {
        return removedLength;
    }

    public void setDirection(boolean direction){
        this.direction = direction;
    }

    public void setMode(int mode){
        this.mode = mode;
    }
    @Override
    protected void paintComponent (Graphics g){
        super.paintComponent(g);
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                // Draw cells
                if (grid[i][j] == 0) {
                    g.setColor(Color.BLUE);
                }
                else if(grid[i][j] == 3) {
                    g.setColor(new Color(97, 224, 250));
                }
                else if(grid[i][j] == 4){
                    g.setColor(Color.GREEN);
                }
                g.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }

        }
    }
//else if (e.getKeyCode() == KeyEvent.VK_ENTER)
    public void addCustomEventListener(ActionListener listener) {
        this.listener = listener;
    }
    private void fireCustomEvent(String message) {
        ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, message);
        listener.actionPerformed(event);
    }

    public int[][] getBoard() {
        return grid;
    }
}