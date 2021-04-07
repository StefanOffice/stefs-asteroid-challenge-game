package stef.asteroidchallenge;

import java.util.Date;

public class Player implements Comparable<Player>{

    public String name;
    public int score;
    public Date date;


    public Player(String name, int score, Date date) {
        this.name = name;
        this.score = score;
        this.date = date;
    }

    @Override
    //sort the players based on the score,
    // inverted so the best player goes to the start of the list
    public int compareTo(Player o) {
        if(o == null)
            return -1;
        return o.score - score;
    }

}