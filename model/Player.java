package model;

import java.util.Arrays;

public class Player {
    private int[][] board;
    private int[][] enemyBoard;
    private int[] shipCount;
    private int[] remainingShips;
    private int shipsLeftInBattle;
    private String name;
    private final int GRID_SIZE = 10;

    public Player(String name, int[] shipCount){
        this.name = name;
        this.shipCount = new int[5];
        System.arraycopy(shipCount, 0, this.shipCount, 0, 5);
        board = new int[GRID_SIZE][GRID_SIZE];
        shipsLeftInBattle = Arrays.stream(shipCount).sum();
        remainingShips = new int[5];
        System.arraycopy(shipCount, 0, remainingShips, 0, 5);
    }

    public void setBoard(int[][] boardData){
        board = new int[GRID_SIZE][GRID_SIZE];
        for(int i = 0; i < GRID_SIZE; i++){
            System.arraycopy(boardData[i], 0, board[i], 0, GRID_SIZE);
        }
    }
    public void setEnemyBoard(int[][] enemy){
        enemyBoard = new int[GRID_SIZE][GRID_SIZE];
        for(int i = 0; i < GRID_SIZE; i++){
            System.arraycopy(enemy[i], 0, enemyBoard[i], 0, GRID_SIZE);
        }
    }

    public String getName() {
        return name;
    }

    public int[][] getEnemyBoard(){
        return enemyBoard;
    }

    public int[] getRemainingShips() {
        return remainingShips;
    }

    public int[] getShipCount(){
        return shipCount;
    }

    public int getShipsLeftInBattle() {
        return shipsLeftInBattle;
    }

    public void setRemainingShips(int index) {
        remainingShips[index]--;
        shipsLeftInBattle--;
    }
}
