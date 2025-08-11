package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BattleBoardPanel extends JPanel {
    private static final int GRID_SIZE = 10;
    private static final int CELL_SIZE = 30;

    private final int[][] grid = new int[GRID_SIZE][GRID_SIZE];
    private int[][] enemyGrid = new int[GRID_SIZE][GRID_SIZE];
    private int previousX;
    private int previousY;
    private int explodedLength;
    private boolean isMyTurn;
    ActionListener listener;
    public BattleBoardPanel(){
        isMyTurn = false;
        previousX = -1;
        previousY = -1;
        setPreferredSize(new Dimension(GRID_SIZE * CELL_SIZE, GRID_SIZE * CELL_SIZE));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                resetHover();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if(!isMyTurn){
                    return;
                }
                int col = e.getX() / CELL_SIZE;
                int row = e.getY() / CELL_SIZE;
                handleClick(row, col);
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if(!isMyTurn){
                    return;
                }
                int col = e.getX() / CELL_SIZE;
                int row = e.getY() / CELL_SIZE;
                System.out.println("Col " + col + " Row " + row );
                resetPrevious();
                if (row < GRID_SIZE && col < GRID_SIZE && grid[row][col] == 0) {
                    previousX = row;
                    previousY = col;
                    grid[row][col] = 3;
                    repaint();
                }
            }
        });
    }

    public void generateRandomClick(){
        Random r = new Random();
        int row = r.nextInt(GRID_SIZE);
        int col = r.nextInt(GRID_SIZE);
        while(grid[row][col] != 0){
            row = r.nextInt(GRID_SIZE);
            col = r.nextInt(GRID_SIZE);
        }
        handleClick(row, col);
    }

    private void handleClick(int row, int col){
        if (row < GRID_SIZE && col < GRID_SIZE) {
            if(grid[row][col] == 3 || grid[row][col] == 0){
                if(attackCell(row, col)){
                    grid[row][col] = 1; // hit
                    checkExploded(row, col);
                    fireCustomEvent("hit");
                }
                else{
                    grid[row][col] = 2; // miss
                    fireCustomEvent("miss");
                }
                previousX = -1;
                previousY = -1;
            }
            repaint();
        }
    }

    public void toggleTurn(){
        isMyTurn = !isMyTurn;
    }
    public void resetPrevious(){
        if(previousX != -1) {
            grid[previousX][previousY] = 0;
        }
    }

    public void setEnemyGrid(int[][] enemy){
        enemyGrid = new int[GRID_SIZE][GRID_SIZE];
        for(int i = 0; i < GRID_SIZE; i++){
            System.arraycopy(enemy[i], 0, enemyGrid[i], 0, GRID_SIZE);
        }
    }
    private boolean attackCell(int row, int col){
        return enemyGrid[row][col] == 4;
    }

    private void checkExploded(int row, int col){
        List<int[]> hits = new ArrayList<>();
        hits.add(new int[]{row, col});
        int i = row;
        int j = col+1;
        while(j < GRID_SIZE && grid[i][j] == 1){
            hits.add(new int[]{i, j});
            j++;
        }
        if(j < GRID_SIZE && enemyGrid[i][j] == 4 && grid[i][j] == 0){
            return;
        }
        j = col-1;
        while(j >= 0 && grid[i][j] == 1){
            hits.add(new int[]{i, j});
            j--;
        }
        if(j >= 0 && enemyGrid[i][j] == 4 && grid[i][j] == 0){
            return;
        }
        j = col;
        i = row+1;
        while(i < GRID_SIZE && grid[i][j] == 1){
            hits.add(new int[]{i, j});
            i++;
        }
        if(i < GRID_SIZE && enemyGrid[i][j] == 4 && grid[i][j] == 0){
            return;
        }
        i = row-1;
        while(i >= 0 && grid[i][j] == 1){
            hits.add(new int[]{i, j});
            i--;
        }
        if(i >= 0 && enemyGrid[i][j] == 4 && grid[i][j] == 0){
            return;
        }
        setExploded(hits);
    }
    private void setExploded(List<int[]> hits){
        for(int[] arr : hits){
            grid[arr[0]][arr[1]] = 5;
        }
        explodedLength = hits.size();
        repaint();
        fireCustomEvent("exploded");
    }

    public int getExplodedLength() {
        return explodedLength;
    }

    public void resetHover(){
        for(int i = 0; i < GRID_SIZE; i++){
            for(int j = 0; j < GRID_SIZE; j++){
                if(grid[i][j] == 3){
                    grid[i][j] = 0;
                }
            }
        }
        repaint();
    }

    @Override
    protected void paintComponent (Graphics g){
        super.paintComponent(g);
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (grid[i][j] == 0) { // empty
                    g.setColor(Color.BLUE);
                }
                else if (grid[i][j] == 1) {
                   g.setColor(Color.ORANGE); // Hit
                }
                else if (grid[i][j] == 2) {
                    g.setColor(Color.WHITE); // Miss
                }
                else if(grid[i][j] == 3) { // hover
                    g.setColor(new Color(97, 224, 250));
                }
                else if(grid[i][j] == 5){ // whole ship hit
                    g.setColor(Color.RED);
                }
                g.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                if(grid[i][j] == 1){
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setColor(Color.RED);
                    g2d.setStroke(new BasicStroke(2.5f));
                    g2d.draw(new Line2D.Double(j * CELL_SIZE+3, i * CELL_SIZE+3, j * CELL_SIZE + CELL_SIZE-3, i * CELL_SIZE + CELL_SIZE-3));
                    g2d.draw(new Line2D.Double(j * CELL_SIZE + CELL_SIZE-3, i * CELL_SIZE+3, j * CELL_SIZE+3, i * CELL_SIZE + CELL_SIZE-3));
                    g2d.setStroke(new BasicStroke(1.0f));
                }
            }

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
