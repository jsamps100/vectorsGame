package game;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class Highscores {

    private String name;
    private int highScore;

    public Highscores() {
        name = "N/A";
        highScore = 0;
        loadHighScore();
    }
    
    public String getName() {
        return name;
    }

    public int getHighScore() {
        return highScore;
    }
    
    public void loadHighScore() {

        try {
            File file = new File("highscores.txt");
            Scanner reader = new Scanner(file);
            name = reader.nextLine();
            highScore = reader.nextInt();
            reader.close();
        }
        catch (Exception e) {
            name = "N/A";
            highScore = 0;
        }
    }
    public void saveHighScore() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("highscores.txt"));
            writer.println(name);
            writer.println(highScore);
            writer.close();
        }
        catch (Exception e) {
            System.out.println("Error saving high score.");
        }
    }
    public void checkHighScore(String playerName, int score) {

        if (score > highScore) {
            name = playerName;
            highScore = score;
            saveHighScore();
        }
    }
}