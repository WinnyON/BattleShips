package control;

import model.LeaderBoard;
import model.Player;
import view.*;

import javax.swing.*;

public class GameManager {
    private MainFrame mainFrame;
    private GamePanel gamePanel;
    private PlayerPanel player1;
    private PlayerPanel player2;
    private Player playerModel1;
    private Player playerModel2;
    private PlaceShipsPanel placeShipsPanel1;
    private PlaceShipsPanel placeShipsPanel2;
    private BoardPanel boardPanel1;
    private BoardPanel boardPanel2;
    private TurnPanel turnPanel1;
    private TurnPanel turnPanel2;
    private MenuPanel menuPanel;
    private BattleBoardPanel battleBoardPanel1;
    private BattleBoardPanel battleBoardPanel2;
    private SettingsPanel settingsPanel;
    private final int[] shipCount = {2, 1, 3, 2, 1};
    private Timer timer1;
    private Timer timer2;
    private LeaderBoard leaderBoard;
    private BackgroundMusic backgroundMusic;
    private SoundEffect explosionSoundEffect;
    private SoundEffect splashSoundEffect;
    String musicFilePath = "assets/dreams.wav";
    String explosionSoundEffectPath = "assets/hit.wav";
    String splashSoundEffectPath = "assets/splash.wav";
    private float soundEffectVolume = -30.0f;
    private float backgroundMusicVolume = -20.0f;
    public GameManager(){
        leaderBoard = new LeaderBoard();
        mainFrame = new MainFrame();
        menuPanel = mainFrame.getMenuPanel();
        menuPanel.addCustomEventListener(e -> {
            String command = e.getActionCommand();
            if(command.equals("1")){
                mainFrame.getScoresPanel().setScoresData(leaderBoard.toString());
            }
            else if(command.equals("2")){
                if(timer1 != null){
                    timer1.stop();
                    timer1 = null;
                }
                if(timer2 != null){
                    timer2.stop();
                    timer2 = null;
                }
                mainFrame.resetGamePanel();
                setGameListeners();
            }
        });
        backgroundMusic = new BackgroundMusic();
        backgroundMusic.playMusic(musicFilePath);
        backgroundMusic.setVolume(backgroundMusicVolume);
        explosionSoundEffect = new SoundEffect();
        splashSoundEffect = new SoundEffect();

        settingsPanel = mainFrame.getSettingsPanel();
        settingsPanel.addCustomEventListener(e -> {
            String command = e.getActionCommand();
            if(command.equals("music")){
                backgroundMusicVolume = settingsPanel.getMusicVolume();
                backgroundMusic.setVolume(backgroundMusicVolume);
            }
            else if(command.equals("effect")){
                soundEffectVolume = settingsPanel.getEffectVolume();
            }
        });
    }

    private void setGameListeners() {
        gamePanel = mainFrame.getGamePanel();
        player1 = gamePanel.getPlayer1();
        player2 = gamePanel.getPlayer2();
        player2.waitTurn();
        player1.addCustomEventListener(e -> {
            String command = e.getActionCommand();
            if(command.equals("nameSet")){ // name is set for player 1
                playerModel1 = new Player(player1.getPlayerName(), shipCount);
                placeShipsPanel1 = player1.getPlaceShipsPanel();
                placeShipsPanel1.setShipCount(playerModel1.getShipCount());
                boardPanel1 = placeShipsPanel1.getBoardPanel();
                placeShipsPanel1.addCustomEventListener(e1 -> {
                    String command1 = e1.getActionCommand();
                    if(command1.equals("Finished")){
                        playerModel1.setBoard(boardPanel1.getBoard());
                        player1.hidePanel();
                        player1.setFinishedPlacement(true);
                    }
                });
                boardPanel1.addCustomEventListener(e2 -> {
                    String command2 = e2.getActionCommand();
                    if(command2.equals("dec")){
                        placeShipsPanel1.decreaseShipCount();
                    }
                    else if(command2.equals("inc")){
                        placeShipsPanel1.increaseShipCount();
                    }
                });
            }
            else if(command.equals("finished") && player1.isFinishedPlacement()){
                player2.startTurn();
            }
        });
        player2.addCustomEventListener(e -> {
            String command = e.getActionCommand();
            if(command.equals("nameSet")){ // name is set for player 2
                playerModel2 = new Player(player2.getPlayerName(), shipCount);
                placeShipsPanel2 = player2.getPlaceShipsPanel();
                placeShipsPanel2.setShipCount(playerModel2.getShipCount());
                boardPanel2 = placeShipsPanel2.getBoardPanel();
                placeShipsPanel2.addCustomEventListener(e1 -> {
                    String command1 = e1.getActionCommand();
                    if(command1.equals("Finished")){
                        playerModel2.setBoard(boardPanel2.getBoard());
                        playerModel1.setEnemyBoard(boardPanel2.getBoard());
                        playerModel2.setEnemyBoard(boardPanel1.getBoard());
                        player2.hidePanel();
                        player2.setFinishedPlacement(true);
                    }
                });
                boardPanel2.addCustomEventListener(e2 -> {
                    String command2 = e2.getActionCommand();
                    if(command2.equals("dec")){
                        placeShipsPanel2.decreaseShipCount();
                    }
                    else if(command2.equals("inc")){
                        placeShipsPanel2.increaseShipCount();
                    }
                });
            }
            else if(command.equals("finished") && player2.isFinishedPlacement()){
                player1.createTurnPanel(playerModel1.getRemainingShips());
                player2.createTurnPanel(playerModel2.getRemainingShips());
                turnPanel1 = player1.getTurnPanel();
                battleBoardPanel1 = turnPanel1.getBoardPanel();
                turnPanel2 = player2.getTurnPanel();
                battleBoardPanel2 = turnPanel2.getBoardPanel();
                battleBoardPanel1.setEnemyGrid(playerModel1.getEnemyBoard());
                battleBoardPanel2.setEnemyGrid(playerModel2.getEnemyBoard());

                player1.showTurnPanel();
                player2.showTurnPanel();

                final int[] timeLeft = {10};
                timer1 = new Timer(1000, e12 -> {
                    if(timeLeft[0] > 0){
                        timeLeft[0]--;
                        turnPanel1.setTimeLeft(timeLeft[0]);
                    }
                    else{
                        turnPanel1.setTimeLeft(0);
                        battleBoardPanel1.generateRandomClick();
                    }
                });
                timer2 = new Timer(1000, e13 -> {
                    if(timeLeft[0] > 0){
                        timeLeft[0]--;
                        turnPanel2.setTimeLeft(timeLeft[0]);
                    }
                    else{
                        turnPanel2.setTimeLeft(0);
                        battleBoardPanel2.generateRandomClick();
                    }
                });
                // player 1 starts
                battleBoardPanel1.toggleTurn();
                timer1.start();
                turnPanel1.toggleHide();

                battleBoardPanel1.addCustomEventListener(e3 -> {
                    String command1 = e3.getActionCommand();
                    switch (command1) {
                        case "exploded" -> {
                            playerModel1.setRemainingShips(battleBoardPanel1.getExplodedLength() - 1);
                            turnPanel1.updateRemainingShips();
                            if (playerModel1.getShipsLeftInBattle() == 0) {
                                leaderBoard.addScore(playerModel1.getName());
                                mainFrame.showGameOverPanel(playerModel1.getName(), leaderBoard.getPlacement(playerModel1.getName()));
                                mainFrame.getGameOverPanel().addCustomEventListener(e7 -> {
                                    String command2 = e7.getActionCommand();
                                    if (command2.equals("menu")) {
                                        mainFrame.showMenuPanel();
                                    } else if (command2.equals("scores")) {
                                        mainFrame.getScoresPanel().setScoresData(leaderBoard.toString());
                                        mainFrame.showScoresPanel();
                                    }
                                });
                            }
                        }
                        case "miss" -> {
                            splashSoundEffect.playMusic(splashSoundEffectPath);
                            splashSoundEffect.setVolume(soundEffectVolume);
                            battleBoardPanel1.toggleTurn();
                            timer1.stop();
                            turnPanel1.setTimeLeft(0);
                            turnPanel1.toggleHide();
                            timeLeft[0] = 10;
                            turnPanel2.setTimeLeft(timeLeft[0]);
                            battleBoardPanel2.toggleTurn();
                            timer2.start();
                            turnPanel2.toggleHide();
                        }
                        case "hit" -> {
                            timeLeft[0] = 10;
                            explosionSoundEffect.playMusic(explosionSoundEffectPath);
                            explosionSoundEffect.setVolume(soundEffectVolume);
                        }
                    }
                });
                battleBoardPanel2.addCustomEventListener(e4 -> {
                    String command1 = e4.getActionCommand();
                    switch (command1) {
                        case "exploded" -> {
                            playerModel2.setRemainingShips(battleBoardPanel2.getExplodedLength() - 1);
                            turnPanel2.updateRemainingShips();
                            if (playerModel2.getShipsLeftInBattle() == 0) {
                                leaderBoard.addScore(playerModel2.getName());
                                mainFrame.showGameOverPanel(playerModel2.getName(), leaderBoard.getPlacement(playerModel2.getName()));
                                mainFrame.getGameOverPanel().addCustomEventListener(e7 -> {
                                    String command2 = e7.getActionCommand();
                                    if (command2.equals("menu")) {
                                        mainFrame.showMenuPanel();
                                    } else if (command2.equals("scores")) {
                                        mainFrame.getScoresPanel().setScoresData(leaderBoard.toString());
                                        mainFrame.showScoresPanel();
                                    }
                                });
                            }
                        }
                        case "miss" -> {
                            splashSoundEffect.playMusic(splashSoundEffectPath);
                            splashSoundEffect.setVolume(soundEffectVolume);
                            battleBoardPanel2.toggleTurn();
                            timer2.stop();
                            turnPanel2.setTimeLeft(0);
                            turnPanel2.toggleHide();
                            timeLeft[0] = 10;
                            turnPanel1.setTimeLeft(timeLeft[0]);
                            battleBoardPanel1.toggleTurn();
                            timer1.start();
                            turnPanel1.toggleHide();
                        }
                        case "hit" -> {
                            timeLeft[0] = 10;
                            explosionSoundEffect.playMusic(explosionSoundEffectPath);
                            explosionSoundEffect.setVolume(soundEffectVolume);
                        }
                    }
                });
            }
        });
    }


    public static void main(String[] args) {
        new GameManager();
    }
}
