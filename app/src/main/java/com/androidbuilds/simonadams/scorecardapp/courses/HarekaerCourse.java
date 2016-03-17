package com.androidbuilds.simonadams.scorecardapp.courses;

import com.androidbuilds.simonadams.scorecardapp.dto.Course;
import com.androidbuilds.simonadams.scorecardapp.dto.Hole;
import com.androidbuilds.simonadams.scorecardapp.R;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by simonadams on 22/06/15.
 */
public class HarekaerCourse extends Course implements Serializable {


    //	Course harekaer = new Course(132, 70.6);
    public HarekaerCourse(){
        super(132, 70.6);

        addHoles();
        System.out.println(getCourseLength());

    }

    @Override
    public int [] holeImages(){

        return new int[]{
                R.drawable.hul1, R.drawable.hul2, R.drawable.hul3, R.drawable.hul4, R.drawable.hul5, R.drawable.hul6,
                R.drawable.hul7, R.drawable.hul8, R.drawable.hul9, R.drawable.hul10, R.drawable.hul11, R.drawable.hul12,
                R.drawable.hul13, R.drawable.hul14, R.drawable.hul15, R.drawable.hul16, R.drawable.hul17, R.drawable.hul18};
    }

    @Override
    public float[] getBearingOfMap() {
        return  new float[]{
                0f, -90f, 100f, 0f, 180f, -10f, -100f, 190f, 190f,
                180f, 0f, 180f, 0f, 200f, 200f, 30f, 90f, 120f};
    }

    @Override
    public float[] getZoomOnMap() {
        return  new float[]{
                18f, 16.5f, 16.7f, 18f, 17f, 17.1f, 16.6f, 18.2f, 17f,
                17f, 16.9f, 16.8f, 16.4f, 16.7f, 18.3f, 16.3f, 18.5f, 17.1f};
    }

    @Override
    public LatLng[] getStartLatitudeAndLongitude() {

        return new LatLng[]{
                new LatLng(55.489252, 12.104942), new LatLng(55.490379, 12.104108), new LatLng(55.490778, 12.097419),
                new LatLng(55.490882, 12.104269), new LatLng(55.492428, 12.105054), new LatLng(55.489797, 12.106511),
                new LatLng(55.492435, 12.103754), new LatLng(55.491313, 12.097111), new LatLng(55.488979, 12.096060),
                new LatLng(55.486118, 12.095676), new LatLng(55.483126, 12.093985), new LatLng(55.486615, 12.094512),
                new LatLng(55.484519, 12.092482), new LatLng(55.488833, 12.094660), new LatLng(55.485873, 12.091394),
                new LatLng(55.485519, 12.090043), new LatLng(55.489238, 12.093748), new LatLng(55.489681, 12.098958),
        };
    }

    @Override
    public LatLng[] getEndLatitudeAndLongitude() {

        return new LatLng[]{
                new LatLng(55.490553, 12.104757), new LatLng(55.490444, 12.097302), new LatLng(55.490728, 12.103651),
                new LatLng(55.492255, 12.104291), new LatLng(55.489439, 12.105393), new LatLng(55.492370, 12.105671),
                new LatLng(55.491104, 12.097506), new LatLng(55.490097, 12.096691), new LatLng(55.486355, 12.095303),
                new LatLng(55.483139, 12.094606), new LatLng(55.485895, 12.094973), new LatLng(55.483497, 12.093314),
                new LatLng(55.488623, 12.095139), new LatLng(55.485694, 12.092076), new LatLng(55.484979, 12.090102),
                new LatLng(55.489033, 12.094142), new LatLng(55.489511, 12.095471), new LatLng(55.489089, 12.104031),
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

    public void printParForCourse(){

        System.out.println(getParTotal());

    }

    @Override
    public void addHoles(){

        addHole(new Hole(1, 15, 3, 145));
        addHole(new Hole(2, 3, 5, 449));
        addHole(new Hole(3, 5, 4, 393));
        addHole(new Hole(4, 13, 3, 153));
        addHole(new Hole(5, 7, 4, 332));
        addHole(new Hole(6, 11, 4, 291));
        addHole(new Hole(7, 1, 5, 469));
        addHole(new Hole(8, 17, 3, 138));
        addHole(new Hole(9, 9, 4, 301));
        addHole(new Hole(10, 12, 4, 338));
        addHole(new Hole(11, 14, 4, 313));
        addHole(new Hole(12, 10, 4, 356));
        addHole(new Hole(13, 2, 5, 486));
        addHole(new Hole(14, 6, 4, 386));
        addHole(new Hole(15, 16, 3, 129));
        addHole(new Hole(16, 4, 5, 467));
        addHole(new Hole(17, 18, 3, 113));
        addHole(new Hole(18, 8, 4, 326));
    }
}

