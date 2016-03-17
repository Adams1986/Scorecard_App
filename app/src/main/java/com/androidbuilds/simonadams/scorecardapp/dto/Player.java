package com.androidbuilds.simonadams.scorecardapp.dto;

import java.io.Serializable;

/**
 * Created by simonadams on 22/06/15.
 */
public class Player implements Serializable {

    private int [] score;
    private String name;
    private double handicap;
    private double courseHandicap;
    private int scoreTotal;
    private int scoreTotalBackNine;
    private int pointsTotal;


    public Player(String name, double handicap, int[] score, Course course) {
        super();
        this.name = name;
        this.handicap = handicap;
        this.score = score;
        this.courseHandicap = course.getCourseHandicap(handicap);
        this.scoreTotal = getScoreTotalFrontNine();
        this.scoreTotalBackNine = getScoreTotalBackNine();
        this.pointsTotal = getPoints(course);
    }

    public String getName(){

        return name;

    }

    public void setName(String name){

        this.name = name;

    }


    public double getHandicap() {
        return handicap;
    }


    public void setHandicap(double handicap) {
        this.handicap = handicap;
    }


    public int[] getScore() {
        return score;
    }

    public int getScoreTotalFrontNine(){

        int temp = 0;

        for(int i = 0; i < score.length/2; i++){

            temp +=score[i];
            scoreTotal = temp;
        }
        return scoreTotal;
    }

    public int getScoreTotalBackNine(){

        int temp = 0;

        for(int i = 9; i < score.length; i++){

            temp +=score[i];
            scoreTotalBackNine = temp;
        }
        return scoreTotalBackNine;
    }


    public void setScore(int[] score) {
        this.score = score;
    }

    public void setHoleScore(int hole, int score2){

        score[hole] = score2;
    }


    public double getCourseHandicap() {
        return courseHandicap;
    }


    public void setCourseHandicap(int courseHandicap) {
        this.courseHandicap = courseHandicap;
    }


    public int getPoints(Course course){

        int points = 0;

        for(int holeNumber = 0; holeNumber < score.length; holeNumber++) {
            int strokesOnHole = (int) coursePar(course, holeNumber);

            int netScore = 0;

            if (score[holeNumber] != 0) {
                netScore = (course.getHoles().get(holeNumber).getPar() + strokesOnHole) - score[holeNumber];

                if (netScore == -1) {
                    points += 1;
                } else if (netScore == 0) {
                    points += 2;
                } else if (netScore == 1) {
                    points += 3;
                } else if (netScore == 2) {
                    points += 4;
                } else if (netScore == 3) {
                    points += 5;
                } else if (netScore == 4) {
                    points += 6;
                } else if (netScore == 5) {
                    points += 7;
                } else
                    points += 0;
            }
        }
        pointsTotal  = points;

        return pointsTotal;
    }

    public int getPointsTotal(){



        return 0;
    }


    public double coursePar(Course course, int holeNumber) {



            double strokesOnHoleOne =
                    Math.round(getCourseHandicap() - course.getParTotal() - course.getHoleHCP(holeNumber));

            if (strokesOnHoleOne >= 0) {

                if (strokesOnHoleOne < 18)

                    strokesOnHoleOne = 1;

                else {

                    if (strokesOnHoleOne < 36)

                        strokesOnHoleOne = 2;

                    else if (strokesOnHoleOne < 54)

                        strokesOnHoleOne = 3;
                    else
                        strokesOnHoleOne = 4;
                }
            }
            else {

                if(strokesOnHoleOne < -18)

                    strokesOnHoleOne = -1;
                else
                    strokesOnHoleOne = 0;
            }


            return strokesOnHoleOne;
    }


}
