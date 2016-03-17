package com.androidbuilds.simonadams.scorecardapp.courses;

import com.androidbuilds.simonadams.scorecardapp.dto.Course;
import com.androidbuilds.simonadams.scorecardapp.dto.Hole;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by simonadams on 22/06/15.
 */
public class BrondbyCourse extends Course implements Serializable {


//	Course harekaer = new Course(132, 70.6);


    public BrondbyCourse(){
        super(119, 67.2);

        addHoles();
        System.out.println(getCourseLength());

    }


    public void printParForCourse(){

        System.out.println(getParTotal());

    }

    @Override
    public void addHoles(){

        addHole(new Hole(1, 10, 4, 338));
        addHole(new Hole(2, 2, 5, 442));
        addHole(new Hole(3, 14, 4, 248));
        addHole(new Hole(4, 18, 3, 120));
        addHole(new Hole(5, 6, 4, 331));
        addHole(new Hole(6, 4, 4, 373));
        addHole(new Hole(7, 16, 4, 307));
        addHole(new Hole(8, 12, 4, 252));
        addHole(new Hole(9, 8, 4, 241));
        addHole(new Hole(10, 1, 5, 478));
        addHole(new Hole(11, 11, 3, 130));
        addHole(new Hole(12, 17, 3, 107));
        addHole(new Hole(13, 9, 4, 337));
        addHole(new Hole(14, 3, 5, 457));
        addHole(new Hole(15, 13, 3, 161));
        addHole(new Hole(16, 7, 4, 316));
        addHole(new Hole(17, 5, 4, 285));
        addHole(new Hole(18, 15, 3, 175));
    }

    @Override
    public float[] getBearingOfMap() {
        return  new float[]{
                40f, 95f, -90f, -70f, 90f, 140f, -30f, 160f, 250f,
                270f, -130f, 170f, 80f, 270f, 280f, 90f, 270f, 60f};
    }

    @Override
    public float[] getZoomOnMap() {
        return  new float[]{
                16.9f, 16.6f, 17.3f, 18.4f, 17f, 16.8f, 17.1f, 17.4f, 17.4f,
                16.6f, 18.2f, 18.5f, 16.9f, 16.5f, 18f, 17.2f, 17.2f, 17.8f};
    }

    @Override
    public LatLng[] getStartLatitudeAndLongitude() {

        return new LatLng[]{
                new LatLng(55.632120, 12.397570), new LatLng(55.634618, 12.403145), new LatLng(55.634959, 12.411561),
                new LatLng(55.634550, 12.407221), new LatLng(55.635009, 12.405995), new LatLng(55.635278, 12.417789),
                new LatLng(55.632259, 12.420737), new LatLng(55.634176, 12.417909), new LatLng(55.632415, 12.416492),
                new LatLng(55.633896, 12.409721), new LatLng(55.634074, 12.401478), new LatLng(55.631920, 12.399058),
                new LatLng(55.630293, 12.399818), new LatLng(55.630673, 12.405367), new LatLng(55.629873, 12.397435),
                new LatLng(55.630465, 12.395181), new LatLng(55.630286, 12.400498), new LatLng(55.630785, 12.395573),
        };
    }

    @Override
    public LatLng[] getEndLatitudeAndLongitude() {

        return new LatLng[]{
                new LatLng(55.634427, 12.401064), new LatLng(55.634109, 12.409986), new LatLng(55.634471, 12.407727),
                new LatLng(55.634745, 12.405306), new LatLng(55.635449, 12.411215), new LatLng(55.632548, 12.421253),
                new LatLng(55.634503, 12.417999), new LatLng(55.632137, 12.419199), new LatLng(55.631887, 12.412829),
                new LatLng(55.634341, 12.402873), new LatLng(55.633126, 12.400258), new LatLng(55.630988, 12.399488),
                new LatLng(55.630923, 12.405050), new LatLng(55.629802, 12.398221), new LatLng(55.630070, 12.394934),
                new LatLng(55.630527, 12.399668), new LatLng(55.630365, 12.396033), new LatLng(55.631836, 12.397725),
        };
    }

    @Override
    public LatLng[] getPerspectiveLatitudeAndLongitude() {

        LatLng[] latLngs = new LatLng[18];

        for (int i = 0; i < latLngs.length; i++) {

            latLngs[i] = new LatLng(
                    (getStartLatitudeAndLongitude()[i].latitude + getEndLatitudeAndLongitude()[i].latitude) / 2,
                    (getStartLatitudeAndLongitude()[i].longitude + getEndLatitudeAndLongitude()[i].longitude) / 2);
        }
        return latLngs;
    }
}

