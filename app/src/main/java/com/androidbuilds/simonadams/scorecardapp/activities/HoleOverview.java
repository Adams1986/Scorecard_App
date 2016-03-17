package com.androidbuilds.simonadams.scorecardapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;

import com.androidbuilds.simonadams.scorecardapp.dto.Round;
import com.androidbuilds.simonadams.scorecardapp.R;


public class HoleOverview extends AppCompatActivity implements NumberPicker.OnValueChangeListener {

    private Round round;
    private int holeNumber;
    private ImageView holeImageView;
    private NumberPicker scoreNumberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hole_overview);

        /*LinearLayout overviewLayout = (LinearLayout) findViewById(R.id.hole_overview_layout);
        overviewLayout.setBackgroundColor(Color.rgb(185, 221, 180));*/

        round = (Round) getIntent().getSerializableExtra("Scorecard");

        holeNumber = getIntent().getIntExtra("show", 1);

        holeImageView = (ImageView) findViewById(R.id.imageView);

        setHoleToShow(holeImageView);

        scoreNumberPicker = (NumberPicker) findViewById(R.id.hole_one_score_input);

        initNumberPicker(scoreNumberPicker);


        if (round.getPlayers()[0].getScore()[holeNumber - 1] != 0)
            scoreNumberPicker.setValue(round.getPlayers()[0].getScore()[holeNumber - 1]);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hole_one, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        goToScorecard();
    }

    public void initNumberPicker(NumberPicker numPicker){

        numPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numPicker.setMaxValue(15);
        numPicker.setMinValue(0);
        numPicker.setWrapSelectorWheel(false);
        numPicker.setOnValueChangedListener(this);
    }

    public void showOverview(View view) {

        if(holeNumber == 1){
            holeNumber = 19;
        }
        holeNumber--;

        setHoleToShow(holeImageView);


        if(round.getPlayers()[0].getScore()[holeNumber - 1] != 0)
            scoreNumberPicker.setValue(round.getPlayers()[0].getScore()[holeNumber - 1]);
        else
            scoreNumberPicker.setValue(0);
    }

    public void goToScorecard(){

        Intent i = new Intent(this, ScorecardOverview.class);
        i.putExtra("Scorecard", round);
        startActivity(i);
        finish();
    }

    public void showNextHole(View view){

        if(holeNumber == 18){
            holeNumber = 0;
        }
        holeNumber++;

        setHoleToShow(holeImageView);


        if(round.getPlayers()[0].getScore()[holeNumber - 1] != 0)
            scoreNumberPicker.setValue(round.getPlayers()[0].getScore()[holeNumber - 1]);
        else
            scoreNumberPicker.setValue(0);

    }


    public void setHoleToShow(ImageView iv){

        int[] holeImagesHarekaer = round.getCourse().holeImages();
        
            Log.d("Simon", holeNumber+"");
                iv.setImageResource(holeImagesHarekaer[holeNumber-1]);
    }



    public void saveScore(){

        if(scoreNumberPicker.getValue() != 0)
            round.getPlayers()[0].getScore()[holeNumber-1] = scoreNumberPicker.getValue();
        else
            round.getPlayers()[0].getScore()[holeNumber-1] = 0;
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int newVal) {

        Log.i("value is", "" + newVal);
        saveScore();

    }

    public void showMapOfHole(View view) {

        Intent goToGoogleMaps = new Intent(this, MapOfHoles.class);

        goToGoogleMaps.putExtra("show", holeNumber);
        goToGoogleMaps.putExtra("Scorecard", round);

        startActivity(goToGoogleMaps);
        finish();
    }
}


