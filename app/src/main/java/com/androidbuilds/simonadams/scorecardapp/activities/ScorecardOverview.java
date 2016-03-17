package com.androidbuilds.simonadams.scorecardapp.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.androidbuilds.simonadams.scorecardapp.dto.Round;
import com.androidbuilds.simonadams.scorecardapp.database.PlayerRoundDatabase;
import com.androidbuilds.simonadams.scorecardapp.R;


public class ScorecardOverview extends AppCompatActivity {

    private Round currentRound;

    private int strokeTotalFrontNine;
    private int strokeTotalBackNine;

    private TextView numberHoles[] = new TextView[TableIDs.holeNumbers.length];
    private TextView lengthHoles[] = new TextView[TableIDs.holeLengths.length];
    private TextView parHoles[] = new TextView[TableIDs.holePars.length];
    private TextView hcpHoles[] = new TextView[TableIDs.holeHCPs.length];
    private TextView strokesHoles[] = new TextView[TableIDs.holeStrokes.length];
    private TextView scoreHoles[] = new TextView[TableIDs.holeScores.length];


    private int showHole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorecard_overview);

        //Navn på bane i titlen. Forsvinder hvis man vil ind og ændre info.
        String title = getIntent().getStringExtra("Course");

        if(title != null) {
            setTitle(title);
        }

        setCurrentRound(savedInstanceState);

        setPoints();
        setRelativeScore();
        setScoreTotal();

        loadHeaderPlayerInfo();

        //indlæs scoretotaler
        scoreHoles[scoreHoles.length-2] = (TextView) findViewById(R.id.score_total_text_view);
        scoreHoles[scoreHoles.length-1] = (TextView) findViewById(TableIDs.holeScores[TableIDs.holeScores.length-1]);

        loadTableInfo();

        lengthHoles[TableIDs.holeLengths.length-2] = (TextView) findViewById(R.id.length_total_text_view);
        lengthHoles[TableIDs.holeLengths.length-2].setText(Integer.toString(currentRound.getCourse().getCourseLengthFrontNine()));

        parHoles[TableIDs.holePars.length-2] = (TextView) findViewById(TableIDs.holePars[TableIDs.holePars.length-2]);
        parHoles[TableIDs.holePars.length-2].setText(Integer.toString(currentRound.getCourse().getParFrontNine()));

        for(int i = 0; i < (TableIDs.holeStrokes.length/2-1); i++){

            strokeTotalFrontNine += Integer.parseInt(strokesHoles[i].getText().toString());

        }
        strokesHoles[TableIDs.holeStrokes.length-1] = (TextView) findViewById(R.id.strokes_total_text_view);
        strokesHoles[TableIDs.holeStrokes.length-1].setText(Integer.toString(strokeTotalFrontNine));


        lengthHoles[TableIDs.holeLengths.length-1] = (TextView) findViewById(TableIDs.holeLengths[TableIDs.holeLengths.length-1]);
        lengthHoles[TableIDs.holeLengths.length-1].setText(Integer.toString(currentRound.getCourse().getCourseLengthBackNine()));

        parHoles[TableIDs.holePars.length-1] = (TextView) findViewById(TableIDs.holePars[TableIDs.holePars.length-1]);
        parHoles[TableIDs.holePars.length-1].setText(Integer.toString(currentRound.getCourse().getParBackNine()));

        for(int i = 9; i < (TableIDs.holeStrokes.length-2); i++){

            strokeTotalBackNine += Integer.parseInt(strokesHoles[i].getText().toString());

        }
        strokesHoles[TableIDs.holeStrokes.length-1] = (TextView) findViewById(R.id.strokes_backnine_text_view);
        strokesHoles[TableIDs.holeStrokes.length-1].setText(Integer.toString(strokeTotalBackNine));
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(getString(R.string.quit_round))
                .setNeutralButton(getString(R.string.dialog_cancel_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setCancelable(false)
                .setPositiveButton(getString(R.string.dialog_yes_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        saveCurrentRound();
                        finish();

                    }
                }).setNegativeButton(getString(R.string.dialog_no_text), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

                finish();
            }
        });

        AlertDialog alert = builder.create();

        alert.show();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scorecard_overview, menu);
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
            goToActivityWithCurrentRound(MainActivity.class);
        }
        else if(id == R.id.history){
            goToActivityWithCurrentRound(HistoryActivity.class);
        }
        else if(id == R.id.extended_history){
            goToActivityWithCurrentRound(ExtendedHistoryActivity.class);
        }

        return super.onOptionsItemSelected(item);
    }

    private void setCurrentRound(Bundle savedState) {

        if(savedState == null)
            currentRound = (Round) getIntent().getSerializableExtra("Scorecard");

        boolean fromHistory = getIntent().getBooleanExtra("History", false);
        if(fromHistory){

            currentRound = (Round) getIntent().getSerializableExtra("RoundFromHistory");

            Toast.makeText(this,
                    "Points: " + currentRound.getPlayers()[0].getPoints(currentRound.getCourse()), Toast.LENGTH_LONG).show();
        }
    }

    private void goToActivityWithCurrentRound(Class activityClass){

        Intent i = new Intent(this, activityClass);
        i.putExtra("Scorecard", currentRound);

        if(activityClass == MainActivity.class){
            i.putExtra("loadPlayer", true);
            finish();
        }
        else if(activityClass == HoleOverview.class || activityClass == MapOfHoles.class){
            i.putExtra("show", showHole);
            finish();
        }

        startActivity(i);
    }

    private void loadTableInfo() {

        for(int id=0; id < (TableIDs.holeNumbers.length-2); id++){


            numberHoles[id] = (TextView) findViewById(TableIDs.holeNumbers[id]);
            numberHoles[id].setText(Integer.toString(currentRound.getCourse().getHoles().get(id).getNumber()));

            lengthHoles[id] = (TextView) findViewById(TableIDs.holeLengths[id]);
            lengthHoles[id].setText(Integer.toString(currentRound.getCourse().getHoles().get(id).getLength()));

            parHoles[id] = (TextView) findViewById(TableIDs.holePars[id]);
            parHoles[id].setText(Integer.toString(currentRound.getCourse().getHoles().get(id).getPar()));

            hcpHoles[id] = (TextView) findViewById(TableIDs.holeHCPs[id]);
            hcpHoles[id].setText(Integer.toString(currentRound.getCourse().getHoles().get(id).getHcp()));

            strokesHoles[id] = (TextView) findViewById(TableIDs.holeStrokes[id]);
            strokesHoles[id].setText(String.format("%.0f", currentRound.getPlayers()[0].coursePar(currentRound.getCourse(), id)));


            scoreHoles[id] = (TextView) findViewById(TableIDs.holeScores[id]);

            if(currentRound.getPlayers()[0].getScore()[id] != 0) {

                scoreHoles[id].setText(Integer.toString(currentRound.getPlayers()[0].getScore()[id]));

                setScoresOutnIn();

            }

            scoreHoles[id].setOnEditorActionListener(new OnClickAddScore());
            scoreHoles[id].setOnFocusChangeListener(new OnFocusChangedAddScore());

        }

    }


    private void loadHeaderPlayerInfo() {

        TextView infoNameView = (TextView) findViewById(R.id.info_name_text_view);
        infoNameView.setText(currentRound.getPlayers()[0].getName());

        TextView infoHandicapView = (TextView) findViewById(R.id.info_handicap_text_view);
        infoHandicapView.setText(Double.toString(currentRound.getPlayers()[0].getHandicap()));

        TextView infoCourseHCPView = (TextView) findViewById(R.id.info_banehcp_text_view);
        infoCourseHCPView.setText(Double.toString(currentRound.getPlayers()[0].getCourseHandicap()));
    }

    public void setPoints(){

        TextView pointsScoreText = (TextView) findViewById(R.id.points_text_view);
        int points = currentRound.getPlayers()[0].getPoints(currentRound.getCourse());
        pointsScoreText.setText(Integer.toString(points) + " p");
    }

    public void setScoresOutnIn(){

        if(currentRound.getPlayers()[0].getScoreTotalFrontNine() != 0)
            scoreHoles[scoreHoles.length - 2].setText(Integer.toString(currentRound.getPlayers()[0].getScoreTotalFrontNine()));
        else
            scoreHoles[scoreHoles.length - 2].setText("-");
        if(currentRound.getPlayers()[0].getScoreTotalBackNine() != 0)
            scoreHoles[scoreHoles.length - 1].setText(Integer.toString(currentRound.getPlayers()[0].getScoreTotalBackNine()));
        else
            scoreHoles[scoreHoles.length - 1].setText("-");
    }

    public void setScoreTotal(){

        TextView scoreTotalText = (TextView) findViewById(R.id.total_text_view);
        int scoreTotal = currentRound.getPlayers()[0].getScoreTotalFrontNine() + currentRound.getPlayers()[0].getScoreTotalBackNine();

        if(scoreTotal != 0){

            scoreTotalText.setText(
                    Integer.toString(scoreTotal));
        }
        else
            scoreTotalText.setText("-");

    }

    public void setRelativeScore(){

        TextView relativeScoreText = (TextView) findViewById(R.id.relative_par_text_view);
        int relativeScore = currentRound.getCourse().getScoreRelative(currentRound.getPlayers()[0].getScore());

        if(relativeScore > 0){

            relativeScoreText.setText("+" + relativeScore);

        }
        else if (relativeScore < 0){
            relativeScoreText.setText(Integer.toString(relativeScore));
        }
        else
            relativeScoreText.setText(R.string.title_par_text_view);

    }

    public void goToHole(View view) {

        showHole = 0;

        for (int i = 0; i < TableIDs.holeNumbers.length-2; i++) {


            if (view.getId() == TableIDs.holeNumbers[i]) {
                showHole = (i + 1);
                break;
            }
        }
        if(currentRound.getCourse().holeImages() != null) {

            goToActivityWithCurrentRound(HoleOverview.class);
        }
        else {
//            Toast.makeText(this, getString(R.string.course_pics_not_implemented_text), Toast.LENGTH_LONG).show();
            goToActivityWithCurrentRound(MapOfHoles.class);

        }
    }

    public void showMain(View view) {

        goToActivityWithCurrentRound(MainActivity.class);
    }

    public void onSaveRound(View view) {

        saveCurrentRound();
    }

    private void saveCurrentRound() {

        PlayerRoundDatabase database = new PlayerRoundDatabase(this);

        database.saveTotals(currentRound);
        database.saveNewRound(currentRound);
        Toast.makeText(this, getString(R.string.round_saved_info_text), Toast.LENGTH_LONG).show();
    }


    private class OnClickAddScore implements TextView.OnEditorActionListener {



        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {


            if(i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT){

                for(int id = 0; id < TableIDs.holeScores.length-2; id++) {

                    if(!scoreHoles[id].getText().toString().equals("")) {

                        if (textView.getId() == TableIDs.holeScores[id]) {

                            currentRound.getPlayers()[0].getScore()[id] = Integer.parseInt(scoreHoles[id].getText().toString());
                        }
                    }
                    else{

                        currentRound.getPlayers()[0].setHoleScore(id, 0);
                    }

                    setScoresOutnIn();
                    setPoints();
                    setScoreTotal();
                    setRelativeScore();

                }
            }
            return false;
        }
    }

    private class OnFocusChangedAddScore implements View.OnFocusChangeListener {


        @Override
        public void onFocusChange(View view, boolean hasFocus) {

            for (int id = 0; id < TableIDs.holeScores.length-2; id++) {



                if (!scoreHoles[id].getText().toString().equals("")) {

                    if (view.getId() == TableIDs.holeScores[id]) {

                        currentRound.getPlayers()[0].getScore()[id] = Integer.parseInt(scoreHoles[id].getText().toString());
                    }
                } else {

                    currentRound.getPlayers()[0].setHoleScore(id, 0);
                }

                setScoresOutnIn();
                setPoints();
                setScoreTotal();
                setRelativeScore();


            }
        }
    }
}