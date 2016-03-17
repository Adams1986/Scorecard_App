package com.androidbuilds.simonadams.scorecardapp.courses;

import com.androidbuilds.simonadams.scorecardapp.dto.Course;
import com.androidbuilds.simonadams.scorecardapp.dto.Hole;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by simonadams on 22/06/15.
 */
public class VaerebroCourse extends Course implements Serializable {


    public VaerebroCourse(){
        super(126, 71.4);

        addHoles();
        System.out.println(getCourseLength());

    }


    public void printParForCourse(){

        System.out.println(getParTotal());

    }

    @Override
    public void addHoles(){

        addHole(new Hole(1, 13, 4, 300));
        addHole(new Hole(2, 15, 3, 152));
        addHole(new Hole(3, 5, 4, 326));
        addHole(new Hole(4, 9, 3, 161));
        addHole(new Hole(5, 7, 5, 496));
        addHole(new Hole(6, 3, 5, 522));
        addHole(new Hole(7, 17, 3, 136));
        addHole(new Hole(8, 1, 4, 376));
        addHole(new Hole(9, 11, 5, 437));
        addHole(new Hole(10, 8, 4, 361));
        addHole(new Hole(11, 4, 4, 330));
        addHole(new Hole(12, 18, 3, 124));
        addHole(new Hole(13, 14, 4, 341));
        addHole(new Hole(14, 6, 4, 313));
        addHole(new Hole(15, 16, 4, 256));
        addHole(new Hole(16, 10, 4, 346));
        addHole(new Hole(17, 12, 4, 335));
        addHole(new Hole(18, 2, 5, 451));
    }

    @Override
    public float[] getBearingOfMap() {
        return  new float[]{
                -75f, 115f, -80f, -100f, 70f, 185f, -155f, 15f, 0f,
                180f, -160f, -110f, 165f, -20f, 175f, -10f, 5f, 0f};
    }

    @Override
    public float[] getZoomOnMap() {
        return  new float[]{
                17.1f, 18.1f, 16.9f, 18f, 16.5f, 16.3f, 18.2f, 16.8f, 16.5f,
                16.8f, 16.9f, 18.4f, 16.9f, 17.1f, 17.3f, 16.8f, 16.9f, 16.5f};
    }

    @Override
    public LatLng[] getStartLatitudeAndLongitude() {

        return new LatLng[]{
                new LatLng(55.759769, 12.157664), new LatLng(55.759692, 12.153207), new LatLng(55.758782, 12.155791),
                new LatLng(55.758276, 12.151899), new LatLng(55.757683, 12.150437), new LatLng(55.758473, 12.157324),
                new LatLng(55.753911, 12.158648), new LatLng(55.752278, 12.157823), new LatLng(55.754929, 12.159886),
                new LatLng(55.759110, 12.160605), new LatLng(55.755266, 12.160467), new LatLng(55.752059, 12.159216),
                new LatLng(55.751135, 12.157326), new LatLng(55.748504, 12.159778), new LatLng(55.752046, 12.159352),
                new LatLng(55.749024, 12.160447), new LatLng(55.751987, 12.160649), new LatLng(55.755388, 12.161121),
        };
    }

    @Override
    public LatLng[] getEndLatitudeAndLongitude() {

        return new LatLng[]{
                new LatLng(55.760475, 12.153079), new LatLng(55.759114, 12.155357), new LatLng(55.759215, 12.150653),
                new LatLng(55.757992, 12.149358), new LatLng(55.759136, 12.157275), new LatLng(55.753952, 12.157661),
                new LatLng(55.752841, 12.157535), new LatLng(55.755450, 12.158997), new LatLng(55.758799, 12.159774),
                new LatLng(55.755830, 12.160310), new LatLng(55.752379, 12.159137), new LatLng(55.751618, 12.157372),
                new LatLng(55.748297, 12.159040), new LatLng(55.751072, 12.158117), new LatLng(55.749748, 12.159655),
                new LatLng(55.752114, 12.159867), new LatLng(55.754964, 12.160935), new LatLng(55.759445, 12.161319),
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

