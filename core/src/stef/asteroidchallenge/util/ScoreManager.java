package stef.asteroidchallenge.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import stef.asteroidchallenge.Player;

public class ScoreManager {

    private static final Player[] bestPlayers = new Player[10];
    @SuppressWarnings("SimpleDateFormat")
    private static final DateFormat formatter = new SimpleDateFormat("dd/MM/yy");

    /**
     * Loads the scores, if there is an error or specified file is corrupted,
     *  fills the list with stub data
     * @param filePath - specify the path for the file that contains the scores
     */
    public static void loadHighScores(String filePath){

        //in case the scores file is deleted, create dummy data and create a new scores file with that data
        if(!new File(filePath).exists()) {
            ScoreManager.createStubData();
            ScoreManager.saveHighScores(filePath);
        }

        //read the best scores from the scores file
        try{
            //creates a reader, wrapped with a buffer for efficiency
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String input;
            for(int i = 0; i < bestPlayers.length; i++){
                //take the data from the line
                input = reader.readLine();
                if (input == null) {
                    bestPlayers[i] = (new Player("Unknown", 0, new Date()));
                }  else {
                    //use the data to re-create the player instance and add it to the bestPlayers
                    String[] playerData = input.split(";");
                    String name = playerData[0];
                    int score = Integer.parseInt(playerData[1]);
                    Date date = formatter.parse(playerData[2]);
                    Player player = new Player(name, score, date);
                    bestPlayers[i] = player;
                }
            }
            reader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * Helper method, called if the highscores file is deleted.
     */
    public static void createStubData(){
        for (int i = 0; i < 10; i++) {
            bestPlayers[i]=(new Player("Unknown",0, new Date()));
        }
    }


    /**
     * Saves the highscore data
     * Called when the application is preparing to quit
     * @param filePath - path to the file where scores should be saved
     */
    public static void saveHighScores(String filePath){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            for (Player current : bestPlayers) {
                StringBuilder sb = new StringBuilder(current.name);
                sb.append(";").append(current.score).append(";").append(formatter.format(current.date)).append("\n");
                writer.write(sb.toString());
            }
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * Checks if the current player should be added to the leaderboard
     * @param evaluatedScore - current player score
     * @return - true if evaluatedScore belongs to the leaderboard
     */
    public static boolean isHighScore(int evaluatedScore){
        Arrays.sort(bestPlayers);
        //compare it with the last player on the list and if the evaluated score is greater return true;
        int lastScoreOnLeaderBoard = bestPlayers[bestPlayers.length - 1] != null ? bestPlayers[bestPlayers.length-1].score : 0;
        return (evaluatedScore > lastScoreOnLeaderBoard);
    }

    /**
     * Adds the specified player to the leaderboard if its score is a new highscore
     * @param player
     */
    public static void addPlayerToLeaderBoard(Player player){
        if(isHighScore(player.score)){
            //replace the last player on the leaderboard
            bestPlayers[bestPlayers.length-1] = player;
            //then sort the array so that newly added player is in the correct position
            Arrays.sort(bestPlayers);
        }
    }

    public static Player[] getBestPlayers() {
        return Arrays.copyOf(bestPlayers, bestPlayers.length);
    }
}
