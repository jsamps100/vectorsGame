package game;

import java.util.Scanner;

public class Vectors {
    private Scanner input;
    private Board board;
    private AI ai;
    private Scoring scoring;
    private Highscores highscores;
    private Player player;
    private Player computer;
    private int totalRounds;

    public Vectors() {
        input = new Scanner(System.in);
        board = new Board();
        ai = new AI();
        scoring = new Scoring();
        highscores = new Highscores();
        totalRounds = 20;
    }
    
    public void run() {
        System.out.println("**************************************");
        System.out.println("- V E C T O R S                      *");
        System.out.println("**************************************");
        System.out.print("Enter your name: ");
        String name = input.nextLine().trim();

        if (name.isEmpty()) {
            name = "Player";
        }

        player = new Player(name, 'X');
        computer = new Player("Computer", 'C');

        // Ask for the number of rounds.
        System.out.print("Enter number of rounds (Default 20): ");
        String choice = input.nextLine().trim();
        if (!choice.isEmpty()) {
            try {
                int rounds = Integer.parseInt(choice);
                if (rounds >= 1 && rounds <= 32) {
                    totalRounds = rounds;
                } else {
                    System.out.println("Invalid number of rounds. Using 20.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Using 20 rounds.");
            }
        }
        // Main game loop.
       
        for (int round = 1; round <= totalRounds; round++) {
        	int playerPoints;
            int computerPoints;
        	
        	displayStatus(round);
            board.displayBoard();
            playerTurn();
            computerTurn();
            // Calculate points after both moves.
            playerPoints = scoring.calculatePoints(board, player.getToken());
            computerPoints = scoring.calculatePoints(board, computer.getToken());
            // Update player scores.
            player.addPoints(playerPoints);
            computer.addPoints(computerPoints);
            System.out.println();
            System.out.println("Round " + round + " Results:");
            System.out.println("Player earned: " + playerPoints);
            System.out.println("Computer earned: " + computerPoints);
            System.out.println();
        }
        displayFinalResults();
    }
    
    
    public void playerTurn() {
        int row;
        int col;
        
        while (true) {
        	String move;
            String[] coords;
        	
        	System.out.print("Player(X) Turn (Row Col): ");
            move = input.nextLine().trim();
            coords = move.split("\\s+");
            if (coords.length != 2) {
                System.out.println("Invalid input. Enter row and column.");
                continue;
            }
            try {
                row = Integer.parseInt(coords[1]);
                col = Integer.parseInt(coords[0]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter numbers.");
                continue;
            }

            // Board uses x = column and y = row.
            if (board.isValidMove(col, row)) {
                board.placeMove(col, row, Board.playerId.human);
                break;
            } else {
                System.out.println("Invalid move. Try again.");
            }
        }
    }
   
    
    public void computerTurn() {
        int[] move = ai.chooseMove(board);
        // AI returns {row, col}.
        // Board expects {column, row}.
        int[] coords = {move[1], move[0]};
        board.aiPlaceMove(coords, Board.playerId.comp);
        System.out.println("Computer(C) Turn (Row Col): " + move[0] + " " + move[1]);
    }
   
    
    public void displayStatus(int round) {
        System.out.println("**************************************");
        System.out.println("- V E C T O R S                      *");
        System.out.println("**************************************");
        System.out.printf("- %-34.34s *%n","High Score: " + highscores.getName() + " - " + highscores.getHighScore());
        System.out.println("**************************************");
        System.out.printf("- %-34.34s *%n",String.format("Player Score: %5d Last Round %3d",player.getTotalScore(),player.getLastRoundScore()));
        System.out.printf("- %-34.34s *%n",String.format("Computer Score: %3d Last Round %3d",computer.getTotalScore(), computer.getLastRoundScore()));
        System.out.println("**************************************");
        System.out.printf("- %-34.34s *%n", "Turn " + round + " of " + totalRounds);
        System.out.println("**************************************");
    }
    
    
    public void displayFinalResults() {
        int finalScore;
        
        finalScore= player.getTotalScore() - computer.getTotalScore();
        System.out.println();
        System.out.println("**************************************");
        System.out.println("              GAME OVER");
        System.out.println("**************************************");
        board.displayBoard();
        System.out.println("Player Total Score: "+ player.getTotalScore());
        System.out.println("Computer Total Score: "+ computer.getTotalScore());
        System.out.println("Final Score: " + finalScore);
        if (finalScore > 0) {
            System.out.println("You Win!");
        } else if (finalScore < 0) {
            System.out.println("Computer Wins!");
        } else {
            System.out.println("It's a Tie!");
        }
        // Check whether the final score is a new high score.
        highscores.checkHighScore(player.getName(), finalScore);
        System.out.println("**************************************");
        System.out.println("High Score: "+ highscores.getName() + " - "+ highscores.getHighScore());
        System.out.println("**************************************");
    }
}