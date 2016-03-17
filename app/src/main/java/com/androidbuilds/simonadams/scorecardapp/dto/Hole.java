package com.androidbuilds.simonadams.scorecardapp.dto;

import java.io.Serializable;

/**
 * Created by simonadams on 22/06/15.
 */
public class Hole implements Serializable {

    private int par;
    private int hcp;
    private int length;
    private int number;




    public Hole(int number, int hcp, int par, int length) {

        this.par = par;
        this.hcp = hcp;
        this.length = length;
        this.number = number;
    }


    @Override
    public String toString(){

        String s = "Hole " + number + " - Par: " + par + " HCP: " + hcp + " Lenght: " + length;

        return s;
    }


    public int getPar() {
        return par;
    }


    public void setPar(int par) {
        this.par = par;
    }


    public int getHcp() {
        return hcp;
    }


    public void setHcp(int hcp) {
        this.hcp = hcp;
    }


    public int getLength() {
        return length;
    }


    public void setLength(int length) {
        this.length = length;
    }


    public int getNumber() {
        return number;
    }


    public void setNumber(int number) {
        this.number = number;
    }



}

