package game;

public class Player {
	    private String name;
	    private char token;
	    private int totalScore;
	    private int lastRoundScore;

	    public Player(String name, char token) {
	        this.name = name;
	        this.token = token;
	        totalScore = 0;
	        lastRoundScore = 0;
	    }

	    public String getName() {
	        return name;
	    }

	    public char getToken() {
	        return token;
	    }

	    public int getTotalScore() {
	        return totalScore;
	    }
	    
	    public int getLastRoundScore() {
	    	return lastRoundScore;
	    }

	    public void setLastRoundScore(int points) {
	    	lastRoundScore = points;
	    }
	    
	    public void addPoints(int points) {
	        totalScore = totalScore + points;
	        lastRoundScore = points;
	    }

	}