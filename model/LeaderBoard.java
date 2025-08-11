package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class LeaderBoard {
    static private class User {
        private final String name;
        private int score;
        public User(String name, int score){
            this.name = name;
            this.score = score;
        }
        public String getName() {
            return name;
        }
        public int getScore(){
            return score;
        }
        public void incrementScore(){
            score++;
        }

        @Override
        public String toString(){
            return name + " " + score;
        }
    }
    private List<User> scores;
    String filePath = "assets/leaderboard.txt";
    public LeaderBoard(){
        scores = loadScoresFromFile();
    }

    private List<User> loadScoresFromFile(){
        List<User> playerScores = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            playerScores = br.lines().map( line -> {
                String[] data = line.split(" ");
                return new User(data[1], Integer.parseInt(data[2]));
                })
                .sorted(Comparator.comparingInt(User::getScore).reversed())
                .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Leaderboard cannot be read from file");
        }
        return playerScores;
    }

    private void saveScoresToFile(){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))){
            bw.write(toString());
        }
        catch (IOException e){
            System.out.println("Leaderboard cannot be saved to file");
        }
    }

    public void addScore(String name){
        User user = scores.stream().filter(u -> name.equals(u.getName())).findFirst().orElse(null);
        if(user == null){
            user = new User(name, 1);
            scores.add(user);
        }
        else{
            user.incrementScore();
        }
        scores = scores.stream().sorted(Comparator.comparingInt(User::getScore).reversed()).collect(Collectors.toList());
        saveScoresToFile();
    }

    public int getPlacement(String name){
        int i = 1;
        for(User user : scores){
            if(name.equals(user.getName())){
                return i;
            }
            i++;
        }
        return -1;
    }

    @Override
    public String toString(){
        int i = 1;
        StringBuilder leaderboard = new StringBuilder();
        for(User user : scores){
            leaderboard.append(i).append(". ").append(user.toString()).append("\n");
            i++;
        }
        return leaderboard.toString();
    }
}
