package com.androidbuilds.simonadams.scorecardapp.dto;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by simonadams on 22/06/15.
 */
public abstract class Course implements Serializable{

    private ArrayList<Hole> holes;
    private double courseSlope;
    private int parTotal;
    private double cRating;
    private int length;
    private String courseName;

    public Course(double courseSlope, double cRating){

        holes = new ArrayList<Hole>();
        this.courseSlope = courseSlope;
        this.cRating = cRating;
        this.parTotal = getParTotal();
        this.length = getCourseLength();


    }


    public int getScoreRelative(int[] score){

        int relativeScore = 0;

        for(int i = 0; i < score.length; i++) {

            if (score[i] != 0) {
                relativeScore += score[i] - holes.get(i).getPar();
            }
        }

        return relativeScore;
    }

    public ArrayList<Hole>getHoles(){

        return holes;
    }


    public int getCourseLength(){

        int length = 0;

        for(Hole h : holes){
            length += h.getLength();
        }
        return length;

    }

    public int getCourseLengthFrontNine(){

        int length = 0;

        for(int i = 0; i < (holes.size()/2); i++){

            length += holes.get(i).getLength();

        }
        return length;

    }

    public int getCourseLengthBackNine(){

        int length = 0;

        for(int i = 9; i < (holes.size()); i++){

            length += holes.get(i).getLength();

        }
        return length;

    }

    public double getCourseHandicap(double handicap){

        double playerCourseHCP
                = Math.round(handicap*courseSlope/113+cRating-parTotal);

        return playerCourseHCP;
    }

    public int getParTotal(){

        int parTotal=0;

        for(Hole h : holes){

            parTotal += h.getPar();


        }
        return parTotal;

    }

    public int getParFrontNine(){

        int parTotal = 0;

        for(int i = 0; i < (holes.size()/2); i++){

            parTotal += holes.get(i).getPar();


        }
        return parTotal;

    }

    public int getParBackNine(){

        int parTotal = 0;

        for(int i = 9; i < (holes.size()); i++){

            parTotal += holes.get(i).getPar();


        }
        return parTotal;

    }


    public void addHole(Hole h){

        holes.add(h);

    }

    public int getHoleHCP(int holeNumber){


        int holeHCP = holes.get(holeNumber).getHcp();

        return holeHCP;
    }

    public double getCourseSlope() {
        return courseSlope;
    }

    public void setCourseSlope(double courseSlope) {
        this.courseSlope = courseSlope;
    }

    public double getCRating() {
        return cRating;
    }

    public abstract void addHoles();

    public int [] holeImages(){

        return null;
    }

    public abstract LatLng [] getStartLatitudeAndLongitude();

    public abstract LatLng [] getEndLatitudeAndLongitude();

    public abstract LatLng [] getPerspectiveLatitudeAndLongitude();

    public abstract float[] getBearingOfMap();

    public abstract float[] getZoomOnMap();
}
