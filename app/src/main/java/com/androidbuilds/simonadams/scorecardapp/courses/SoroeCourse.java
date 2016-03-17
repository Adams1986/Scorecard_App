package com.androidbuilds.simonadams.scorecardapp.courses;

import com.androidbuilds.simonadams.scorecardapp.dto.Course;
import com.androidbuilds.simonadams.scorecardapp.dto.Hole;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by simonadams on 22/06/15.
 */
public class SoroeCourse extends Course implements Serializable {


//	Course harekaer = new Course(132, 70.6);


    public SoroeCourse(){
        super(133, 71.4);

        addHoles();
        System.out.println(getCourseLength());

    }


    public void printParForCourse(){

        System.out.println(getParTotal());

    }

    @Override
    public void addHoles(){

        addHole(new Hole(1, 9, 5, 450));
        addHole(new Hole(2, 15, 3, 153));
        addHole(new Hole(3, 7, 4, 368));
        addHole(new Hole(4, 5, 4, 337));
        addHole(new Hole(5, 1, 5, 462));
        addHole(new Hole(6, 13, 4, 327));
        addHole(new Hole(7, 17, 3, 123));
        addHole(new Hole(8, 11, 4, 339));
        addHole(new Hole(9, 3, 4, 378));
        addHole(new Hole(10, 5, 5, 480));
        addHole(new Hole(11, 10, 4, 269));
        addHole(new Hole(12, 18, 3, 163));
        addHole(new Hole(13, 12, 4, 332));
        addHole(new Hole(14, 14, 3, 164));
        addHole(new Hole(15, 6, 4, 351));
        addHole(new Hole(16, 4, 5, 499));
        addHole(new Hole(17, 8, 4, 367));
        addHole(new Hole(18, 16, 3, 141));
    }

    @Override
    public float[] getBearingOfMap() {
        return  new float[]{
                -25f, 130f, -40f, -10f, 170f, -30f, -60f, 160f, 140f,
                -90f, -55f, 100f, 105f, -60f, -90f, 70f, -220f, 100f};
    }

    @Override
    public float[] getZoomOnMap() {
        return  new float[]{
                16.6f, 17.9f, 16.9f, 16.9f, 16.5f, 16.8f, 17.9f, 16.9f, 16.6f,
                16.4f, 17.4f, 18f, 16.9f, 18f, 16.8f, 16.5f, 16.6f, 17.8f};
    }

    @Override
    public LatLng[] getStartLatitudeAndLongitude() {

        return new LatLng[]{
                new LatLng(55.383532, 11.565951), new LatLng(55.386298, 11.564518), new LatLng(55.385962, 11.567062),
                new LatLng(55.387273, 11.562752), new LatLng(55.389838, 11.562016), new LatLng(55.384730, 11.562736),
                new LatLng(55.388659, 11.560988), new LatLng(55.389436, 11.557869), new LatLng(55.387046, 11.557125),
                new LatLng(55.383780, 11.561630), new LatLng(55.382903, 11.554289), new LatLng(55.384463, 11.550510),
                new LatLng(55.384666, 11.552705), new LatLng(55.384136, 11.559318), new LatLng(55.385129, 11.557941),
                new LatLng(55.384334, 11.550473), new LatLng(55.386606, 11.556809), new LatLng(55.383497, 11.561154),
        };
    }

    @Override
    public LatLng[] getEndLatitudeAndLongitude() {

        return new LatLng[]{
                new LatLng(55.386869, 11.562870), new LatLng(55.385439, 11.566811), new LatLng(55.388321, 11.563695),
                new LatLng(55.390402, 11.562300), new LatLng(55.385803, 11.563268), new LatLng(55.387394, 11.559414),
                new LatLng(55.389355, 11.558726), new LatLng(55.386455, 11.559018), new LatLng(55.384445, 11.562069),
                new LatLng(55.383631, 11.554059), new LatLng(55.384112, 11.550911), new LatLng(55.384318, 11.553102),
                new LatLng(55.384102, 11.558290), new LatLng(55.384831, 11.556996), new LatLng(55.384916, 11.552133),
                new LatLng(55.385585, 11.556873), new LatLng(55.384170, 11.561449), new LatLng(55.383153, 11.563897),
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

