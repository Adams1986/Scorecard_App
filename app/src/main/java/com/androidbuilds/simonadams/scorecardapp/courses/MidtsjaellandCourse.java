package com.androidbuilds.simonadams.scorecardapp.courses;

import com.androidbuilds.simonadams.scorecardapp.dto.Course;
import com.androidbuilds.simonadams.scorecardapp.dto.Hole;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by simonadams on 22/06/15.
 */
public class MidtsjaellandCourse extends Course implements Serializable {


//	Course harekaer = new Course(132, 70.6);


    public MidtsjaellandCourse(){
        super(125, 68.7);

        addHoles();
        System.out.println(getCourseLength());

    }


    public void printParForCourse(){

        System.out.println(getParTotal());

    }

    @Override
    public void addHoles(){

        addHole(new Hole(1, 9, 4, 320));
        addHole(new Hole(2, 15, 3, 140));
        addHole(new Hole(3, 11, 4, 291));
        addHole(new Hole(4, 3, 5, 456));
        addHole(new Hole(5, 17, 3, 118));
        addHole(new Hole(6, 5, 4, 366));
        addHole(new Hole(7, 7, 5, 435));
        addHole(new Hole(8, 13, 3, 172));
        addHole(new Hole(9, 1, 5, 499));
        addHole(new Hole(10, 18, 3, 132));
        addHole(new Hole(11, 2, 5, 509));
        addHole(new Hole(12, 16, 3, 117));
        addHole(new Hole(13, 8, 4, 320));
        addHole(new Hole(14, 4, 4, 331));
        addHole(new Hole(15, 12, 4, 240));
        addHole(new Hole(16, 10, 4, 308));
        addHole(new Hole(17, 14, 3, 121));
        addHole(new Hole(18, 6, 5, 446));
    }

    @Override
    public float[] getBearingOfMap() {
        return  new float[]{
                -90f, -85f, 110f, -85f, -160f, 90f, -95f, 35f, 115f,
                -50f, -80f, -45f, -75f, 135f, -50f, 100f, -95f, 110f};
    }

    @Override
    public float[] getZoomOnMap() {
        return  new float[]{
                17f, 18.2f, 17.2f, 16.5f, 18.4f, 16.9f, 16.7f, 17.9f, 16.4f,
                18.3f, 16.4f, 18.4f, 17f, 17f, 17.6f, 17.1f, 18.4f, 16.6f};
    }

    @Override
    public LatLng[] getStartLatitudeAndLongitude() {

        return new LatLng[]{
                new LatLng(55.649659, 11.665459), new LatLng(55.649821, 11.659657), new LatLng(55.649881, 11.656955),
                new LatLng(55.648571, 11.662277), new LatLng(55.649016, 11.654833), new LatLng(55.647858, 11.653381),
                new LatLng(55.647766, 11.658523), new LatLng(55.648021, 11.652772), new LatLng(55.650171, 11.654381),
                new LatLng(55.649754, 11.665861), new LatLng(55.649790, 11.665230), new LatLng(55.650381, 11.658206),
                new LatLng(55.651301, 11.657696), new LatLng(55.651848, 11.652449), new LatLng(55.650554, 11.655309),
                new LatLng(55.652506, 11.653003), new LatLng(55.651900, 11.658540), new LatLng(55.651598, 11.658241),
        };
    }

    @Override
    public LatLng[] getEndLatitudeAndLongitude() {

        return new LatLng[]{
                new LatLng(55.649884, 11.660362), new LatLng(55.650124, 11.657430), new LatLng(55.649364, 11.661453),
                new LatLng(55.649290, 11.655243), new LatLng(55.648081, 11.653831), new LatLng(55.647973, 11.658845),
                new LatLng(55.647738, 11.652208), new LatLng(55.649301, 11.654457), new LatLng(55.649069, 11.661773),
                new LatLng(55.650417, 11.664172), new LatLng(55.651012, 11.657881), new LatLng(55.651096, 11.656761),
                new LatLng(55.652127, 11.652801), new LatLng(55.650254, 11.656658), new LatLng(55.651530, 11.652436),
                new LatLng(55.652472, 11.657898), new LatLng(55.651932, 11.656553), new LatLng(55.650719, 11.665091),
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

