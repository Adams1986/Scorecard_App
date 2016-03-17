package com.androidbuilds.simonadams.scorecardapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.androidbuilds.simonadams.scorecardapp.dto.Round;
import com.androidbuilds.simonadams.scorecardapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.w3c.dom.Text;

/**
 * Created by simonadams on 07/07/15.
 */
public class MapOfHoles extends Activity{

    private LatLng[] startPosHole;
    private LatLng[] perspectivePosHole;
    private LatLng[] endPosHole;

    private GoogleMap googleMap;
    private TextView meterText;
    private float[] distanceToHole;
    private float[] distanceFromHole;
    private Marker markerStart;
    private Marker markerDuring;
    private Marker markerEnd;
    private Polyline line;
    private Polyline lineToCurrPosition;
    private int holeNumber;
    private Round round;
    private float[] bearingOfMap;
    private float[] zoomOnMap;
    private CameraPosition startingCamPos;
    private TextView holeText;
    private TextView meterToPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_of_holes);

        try {

            if (googleMap == null) {

                googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            }

            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

            googleMap.setMyLocationEnabled(true);

            googleMap.setTrafficEnabled(false);


            googleMap.getUiSettings().setZoomControlsEnabled(true);

            googleMap.getUiSettings().setMapToolbarEnabled(false);


            setPositionOnMap();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        Intent goToHoleOverview;

        if(round.getCourse().holeImages() != null) {
            goToHoleOverview = new Intent(this, HoleOverview.class);
        }
        else
            goToHoleOverview = new Intent(this, ScorecardOverview.class);

        goToHoleOverview.putExtra("show", holeNumber);
        goToHoleOverview.putExtra("Scorecard", round);

        startActivity(goToHoleOverview);
        finish();
    }

    private void setPositionOnMap() {

        getResourcesForMap();

        startingCamPos = CameraPosition.fromLatLngZoom(perspectivePosHole[holeNumber-1], zoomOnMap[holeNumber-1]);

        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(
                CameraPosition.builder(startingCamPos).bearing(bearingOfMap[holeNumber - 1]).build()));


        distanceToHole = new float[1];
        distanceToHole[0] = 0;

        distanceFromHole = new float[1];
        distanceFromHole[0] = 0;

        BitmapDescriptor flag = BitmapDescriptorFactory.fromResource(R.drawable.golfflag);

        markerStart = googleMap.addMarker(new MarkerOptions().position(startPosHole[holeNumber-1])
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        markerEnd = googleMap.addMarker(new MarkerOptions().position(endPosHole[holeNumber-1]).icon(flag));

                /*Måde at få reelle længde mellem start og slutpunkterne.
                Location.distanceBetween(
                        startPosHole[i].latitude, startPosHole[i].longitude,
                        endPosHole[i].latitude, endPosHole[i].longitude, distanceToHole);

                meterText.setText(String.format("%.0f", distanceToHole[0]) + getString(R.string.meter_text_string));*/

        meterText.setText(String.format("%d", round.getCourse().getHoles().get(holeNumber-1).getLength()) + getString(R.string.meters_text_string));
        meterToPos.setText(getString(R.string.tee_to_position_meters_text_reset));
        holeText.setText(getString(R.string.hole_map_view_text)+" "+round.getCourse().getHoles().get(holeNumber-1).getNumber());

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                addMarkerAndLine(latLng);


                meterText.setText(String.format("%.0f", distanceToHole[0]) + getString(R.string.meters_text_string));
                meterToPos.setText(String.format("%.0f", distanceFromHole[0]) + getString(R.string.tee_to_position_meters_text));

            }

        });

        googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {

                if (googleMap.getMyLocation() != null) {
                    LatLng myPos = new LatLng(
                            googleMap.getMyLocation().getLatitude(), googleMap.getMyLocation().getLongitude());

                    Location.distanceBetween(
                            myPos.latitude, myPos.longitude,
                            endPosHole[holeNumber - 1].latitude, endPosHole[holeNumber - 1].longitude, distanceToHole);

                    addMarkerAndLine(myPos);

                    meterText.setText(String.format("%.0f ", distanceToHole[0]) + getString(R.string.meters_text_string));
                    meterToPos.setText(String.format("%.0f ", distanceFromHole[0]) + getString(R.string.tee_to_position_meters_text));

                } else
                    Toast.makeText(MapOfHoles.this, "Location not found, try again", Toast.LENGTH_SHORT).show();

                return false;
            }
        });

    }

    public void onResetMap(View view) {

        moveCameraView(holeNumber - 1);

    }

    private void moveCameraView(int hole) {

        startingCamPos = CameraPosition.fromLatLngZoom(perspectivePosHole[hole], zoomOnMap[hole]);

        markerStart.setPosition(startPosHole[hole]);
        markerEnd.setPosition(endPosHole[hole]);

        Location.distanceBetween(
                startPosHole[hole].latitude, startPosHole[hole].longitude,
                endPosHole[hole].latitude, endPosHole[hole].longitude, distanceToHole);

        Location.distanceBetween(
                startPosHole[hole].latitude, startPosHole[hole].longitude,
                endPosHole[hole].latitude, endPosHole[hole].longitude, distanceFromHole);


        meterText.setText(String.format("%d", round.getCourse().getHoles().get(hole).getLength()) + getString(R.string.meters_text_string));
        meterToPos.setText(getString(R.string.tee_to_position_meters_text_reset));
        holeText.setText(getString(R.string.hole_map_view_text)+" "+round.getCourse().getHoles().get(hole).getNumber());
        //Reelle længde mellem start og slutpunkterne
        //meterText.setText(String.format("%.0f", distanceToHole[0]) + getString(R.string.meter_text_string));
        removeMarkerAndLine();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(
                CameraPosition.builder(startingCamPos).bearing(bearingOfMap[hole]).build()));
    }


    private void addMarkerAndLine(LatLng latLng){
        removeMarkerAndLine();
        markerDuring = googleMap.addMarker(
                new MarkerOptions().position(latLng)
//                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ball)).anchor(0.45f, 0.6f));

        Location.distanceBetween(
                latLng.latitude, latLng.longitude, endPosHole[holeNumber - 1].latitude, endPosHole[holeNumber - 1].longitude, distanceToHole);

        Location.distanceBetween(
                latLng.latitude, latLng.longitude, startPosHole[holeNumber - 1].latitude, startPosHole[holeNumber - 1].longitude, distanceFromHole);


        line = googleMap.addPolyline(new PolylineOptions().add(latLng).add(endPosHole[holeNumber-1]).color(Color.WHITE));
        lineToCurrPosition = googleMap.addPolyline(new PolylineOptions().add(latLng).add(startPosHole[holeNumber-1]).color(Color.BLUE));
    }

    private void removeMarkerAndLine() {

        if (markerDuring != null && line != null){
            markerDuring.remove();
            line.remove();
            lineToCurrPosition.remove();
        }
    }

    private void getResourcesForMap(){

        holeNumber = getIntent().getIntExtra("show", 1);
        round = (Round) getIntent().getSerializableExtra("Scorecard");
        startPosHole = round.getCourse().getStartLatitudeAndLongitude();
        endPosHole = round.getCourse().getEndLatitudeAndLongitude();
        perspectivePosHole = round.getCourse().getPerspectiveLatitudeAndLongitude();
        bearingOfMap = round.getCourse().getBearingOfMap();
        zoomOnMap = round.getCourse().getZoomOnMap();
        meterText = (TextView) findViewById(R.id.meters_to_pin_text_view);
        meterToPos = (TextView) findViewById(R.id.tee_to_position__text_view);
        holeText = (TextView) findViewById(R.id.hole_text_map_view);
    }

    public void onBackMap(View view) {

        if(holeNumber == 1){
            holeNumber = 19;
        }
        holeNumber--;
        moveCameraView(holeNumber-1);

    }

    public void onForwardMap(View view) {

        if(holeNumber == 18) {
            holeNumber = 0;
        }
        holeNumber++;
        moveCameraView(holeNumber - 1);
    }
}
