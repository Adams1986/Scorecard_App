package com.androidbuilds.simonadams.scorecardapp.courses;

import com.androidbuilds.simonadams.scorecardapp.dto.Course;
import com.androidbuilds.simonadams.scorecardapp.dto.Hole;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by simonadams on 22/06/15.
 */
public class ReeCourse extends Course implements Serializable {


    public ReeCourse(){
        super(133, 70.7);

        addHoles();
        System.out.println(getCourseLength());

    }


    public void printParForCourse(){

        System.out.println(getParTotal());

    }

    @Override
    public void addHoles(){

        addHole(new Hole(1, 3, 5, 435));
        addHole(new Hole(2, 5, 4, 323));
        addHole(new Hole(3, 15, 3, 155));
        addHole(new Hole(4, 13, 5, 435));
        addHole(new Hole(5, 17, 4, 298));
        addHole(new Hole(6, 11, 3, 153));
        addHole(new Hole(7, 9, 4, 303));
        addHole(new Hole(8, 7, 4, 335));
        addHole(new Hole(9, 1, 4, 358));
        addHole(new Hole(10, 16, 3, 170));
        addHole(new Hole(11, 10, 5, 440));
        addHole(new Hole(12, 4, 4, 340));
        addHole(new Hole(13, 12, 4, 314));
        addHole(new Hole(14, 14, 4, 278));
        addHole(new Hole(15, 6, 4, 266));
        addHole(new Hole(16, 2, 5, 479));
        addHole(new Hole(17, 8, 4, 317));
        addHole(new Hole(18, 18, 3, 130));
    }

    @Override
    public float[] getBearingOfMap() {
        return  new float[]{
                150f, -5f, -120f, 50f, -140f, 95f, 180f, -25f, 160f,
                -95f, -35f, -165f, -15f, -155f, 60f, 95f, -10f, 100f};
    }

    @Override
    public float[] getZoomOnMap() {
        return  new float[]{
                16.7f, 17f, 18.1f, 16.6f, 17.1f, 18f, 17f, 17f, 16.8f,
                18f, 16.6f, 17f, 17.1f, 17.3f, 17.2f, 16.4f, 17f, 18.3f};
    }

    @Override
    public LatLng[] getStartLatitudeAndLongitude() {

        return new LatLng[]{
                new LatLng(55.994715, 12.224372), new LatLng(55.991962, 12.229251), new LatLng(55.995013, 12.228442),
                new LatLng(55.995374, 12.226511), new LatLng(55.998580, 12.232421), new LatLng(55.995352, 12.230451),
                new LatLng(55.995146, 12.233529), new LatLng(55.992303, 12.233114), new LatLng(55.995295, 12.229846),
                new LatLng(55.992290, 12.233115), new LatLng(55.992113, 12.228787), new LatLng(55.995132, 12.218351),
                new LatLng(55.992055, 12.215012), new LatLng(55.994021, 12.210675), new LatLng(55.991621, 12.209184),
                new LatLng(55.991859, 12.211977), new LatLng(55.992200, 12.220072), new LatLng(55.995369, 12.219563),
        };
    }

    @Override
    public LatLng[] getEndLatitudeAndLongitude() {

        return new LatLng[]{
                new LatLng(55.991938, 12.228251), new LatLng(55.994708, 12.228514), new LatLng(55.994405, 12.226274),
                new LatLng(55.997692, 12.231926), new LatLng(55.996719, 12.229086), new LatLng(55.995368, 12.232967),
                new LatLng(55.992410, 12.233785), new LatLng(55.994847, 12.230956), new LatLng(55.992461, 12.232450),
                new LatLng(55.992191, 12.230495), new LatLng(55.995184, 12.224525), new LatLng(55.992365, 12.216794),
                new LatLng(55.994599, 12.213486), new LatLng(55.991920, 12.208846), new LatLng(55.992843, 12.213091),
                new LatLng(55.991852, 12.219624), new LatLng(55.994947, 12.219130), new LatLng(55.995285, 12.221605),
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

