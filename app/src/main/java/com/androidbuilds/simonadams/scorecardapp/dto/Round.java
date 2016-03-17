package com.androidbuilds.simonadams.scorecardapp.dto;

import java.io.Serializable;

/**
 * Created by simonadams on 22/06/15.
 */
public class Round implements Serializable {

    private Course course;
    private Player players[];
    private String date;
    private int identifier;


    public Round(Course course, Player[] players) {
        super();

        this.players = new Player[4];
        this.course = course;
        this.players = players;
    }

    public String getDate(){

        return date;
    }

    public void setIdentifier(int id){

        identifier = id;
    }

    public int getIdentifier(){

        return identifier;
    }

    public void setDate(String date){

        this.date = date;
    }


    public Player[] getPlayers(){

        return players;
    }

    public Course getCourse(){

        return course;
    }

    public void getScores(int[] score){

        for (int i = 0; i < score.length; i++) {

            if(i>8)
                System.out.print("Hole " + (i+1) +": ");
            else
                System.out.print("Hole " + (i+1) +" : ");
            if(score[i] == 0)
                System.out.println("-");
            else
                System.out.println(score[i]);
        }
    }

    /*public void setScoresForPlayers(int playerNumber, int hole){

        players[playerNumber].setHoleScore(hole);
    }*/

    public void addPlayer(int playerNumber, Player player){

        if(playerNumber < 4){

            this.players[playerNumber] = player;
        }

    }

    public void getScoreRelativeToPar(){

        //course
    }

}

