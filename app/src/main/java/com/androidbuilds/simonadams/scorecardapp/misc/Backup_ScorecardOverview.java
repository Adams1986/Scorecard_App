package com.androidbuilds.simonadams.scorecardapp.misc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.androidbuilds.simonadams.scorecardapp.dto.Round;
import com.androidbuilds.simonadams.scorecardapp.activities.HoleOverview;
import com.androidbuilds.simonadams.scorecardapp.activities.MainActivity;
import com.androidbuilds.simonadams.scorecardapp.R;


public class Backup_ScorecardOverview extends AppCompatActivity {

    private Round round;
    private int strokeTotal;
    private int strokeTotalBackNine;
    private int scoreTotal = 0;
    private TextView scoreTotalText;
    private int relativeScore = 0;
    private TextView relativeScoreText;
    private int points = 0;
    private TextView pointsScoreText;


    private static final int[] holeNumbers =
            {R.id.number_hole1_text_view,R.id.number_hole2_text_view, R.id.number_hole3_text_view,R.id.number_hole4_text_view,
                    R.id.number_hole5_text_view,R.id.number_hole6_text_view,R.id.number_hole7_text_view,R.id.number_hole8_text_view,
                    R.id.number_hole9_text_view,

                    R.id.number_hole10_text_view,R.id.number_hole11_text_view, R.id.number_hole12_text_view,R.id.number_hole13_text_view,
                    R.id.number_hole14_text_view,R.id.number_hole15_text_view,R.id.number_hole16_text_view,R.id.number_hole17_text_view,
                    R.id.number_hole18_text_view,

                    R.id.number_total_text_view, R.id.number_backnine_text_view};

    private TextView numberHoles[] = new TextView[holeNumbers.length];

    private static final int[] holeLengths =
            {R.id.length_hole1_text_view, R.id.length_hole2_text_view, R.id.length_hole3_text_view, R.id.length_hole4_text_view,
                    R.id.length_hole5_text_view, R.id.length_hole6_text_view, R.id.length_hole7_text_view, R.id.length_hole8_text_view,
                    R.id.length_hole9_text_view,

                    R.id.length_hole10_text_view, R.id.length_hole11_text_view, R.id.length_hole12_text_view, R.id.length_hole13_text_view,
                    R.id.length_hole14_text_view, R.id.length_hole15_text_view, R.id.length_hole16_text_view, R.id.length_hole17_text_view,
                    R.id.length_hole18_text_view,
                    R.id.length_total_text_view, R.id.length_backnine_text_view};

    private TextView lengthHoles[] = new TextView[holeLengths.length];

    private static final int[] holePars =
            {R.id.par_hole1_text_view, R.id.par_hole2_text_view, R.id.par_hole3_text_view, R.id.par_hole4_text_view,
                    R.id.par_hole5_text_view, R.id.par_hole6_text_view, R.id.par_hole7_text_view, R.id.par_hole8_text_view,
                    R.id.par_hole9_text_view,

                    R.id.par_hole10_text_view, R.id.par_hole11_text_view, R.id.par_hole12_text_view, R.id.par_hole13_text_view,
                    R.id.par_hole14_text_view, R.id.par_hole15_text_view, R.id.par_hole16_text_view, R.id.par_hole17_text_view,
                    R.id.par_hole18_text_view,
                    R.id.par_total_text_view, R.id.par_backnine_text_view};

    private TextView parHoles[] = new TextView[holePars.length];

    private static final int[] holeHCPs =
            {R.id.hcp_hole1_text_view, R.id.hcp_hole2_text_view, R.id.hcp_hole3_text_view, R.id.hcp_hole4_text_view,
                    R.id.hcp_hole5_text_view, R.id.hcp_hole6_text_view, R.id.hcp_hole7_text_view, R.id.hcp_hole8_text_view,
                    R.id.hcp_hole9_text_view,

                    R.id.hcp_hole10_text_view, R.id.hcp_hole11_text_view, R.id.hcp_hole12_text_view, R.id.hcp_hole13_text_view,
                    R.id.hcp_hole14_text_view, R.id.hcp_hole15_text_view, R.id.hcp_hole16_text_view, R.id.hcp_hole17_text_view,
                    R.id.hcp_hole18_text_view,
                    R.id.hcp_total_text_view, R.id.hcp_backnine_text_view};

    private TextView hcpHoles[] = new TextView[holeHCPs.length];

    private static final int[] holeStrokes =
            {R.id.strokes_hole1_text_view, R.id.strokes_hole2_text_view, R.id.strokes_hole3_text_view, R.id.strokes_hole4_text_view,
                    R.id.strokes_hole5_text_view, R.id.strokes_hole6_text_view, R.id.strokes_hole7_text_view,
                    R.id.strokes_hole8_text_view, R.id.strokes_hole9_text_view,

                    R.id.strokes_hole10_text_view, R.id.strokes_hole11_text_view, R.id.strokes_hole12_text_view, R.id.strokes_hole13_text_view,
                    R.id.strokes_hole14_text_view, R.id.strokes_hole15_text_view, R.id.strokes_hole16_text_view,
                    R.id.strokes_hole17_text_view, R.id.strokes_hole18_text_view,
                    R.id.strokes_total_text_view, R.id.strokes_backnine_text_view};

    private TextView strokesHoles[] = new TextView[holeStrokes.length];


    private static final int[] holeScores =
            {R.id.score_hole1_text_view, R.id.score_hole2_text_view, R.id.score_hole3_text_view, R.id.score_hole4_text_view,
                    R.id.score_hole5_text_view, R.id.score_hole6_text_view, R.id.score_hole7_text_view,
                    R.id.score_hole8_text_view, R.id.score_hole9_text_view,

                    R.id.score_hole10_text_view, R.id.score_hole11_text_view, R.id.score_hole12_text_view, R.id.score_hole13_text_view,
                    R.id.score_hole14_text_view, R.id.score_hole15_text_view, R.id.score_hole16_text_view,
                    R.id.score_hole17_text_view, R.id.score_hole18_text_view,
                    R.id.score_total_text_view, R.id.score_backnine_text_view};

    private TextView scoreHoles[] = new TextView[holeScores.length];


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorecard_overview);

        round = (Round) getIntent().getSerializableExtra("Scorecard");

        scoreTotal = round.getPlayers()[0].getScoreTotalFrontNine()+round.getPlayers()[0].getScoreTotalBackNine();
        scoreTotalText = (TextView) findViewById(R.id.total_text_view);

        relativeScore = round.getCourse().getScoreRelative(round.getPlayers()[0].getScore());
        relativeScoreText = (TextView) findViewById(R.id.relative_par_text_view);

//        points = round.getPlayers()[0].getPoints(round.getCourse());
        points = round.getPlayers()[0].getPoints(round.getCourse());
        pointsScoreText = (TextView) findViewById(R.id.points_text_view);
        pointsScoreText.setText(Integer.toString(points)+" p");


        if(relativeScore > 0){

            relativeScoreText.setText("+" + relativeScore);

        }
        else if (relativeScore < 0){
            relativeScoreText.setText(Integer.toString(relativeScore));
        }
        else
            relativeScoreText.setText(R.string.title_par_text_view);

        if(scoreTotal != 0){

            scoreTotalText.setText(
                    Integer.toString(scoreTotal));
        }
        else
            scoreTotalText.setText("-");



        TextView infoNameView = (TextView) findViewById(R.id.info_name_text_view);
        infoNameView.setText(round.getPlayers()[0].getName());

        TextView infoHandicapView = (TextView) findViewById(R.id.info_handicap_text_view);
        infoHandicapView.setText(Double.toString(round.getPlayers()[0].getHandicap()));

        TextView infoCourseHCPView = (TextView) findViewById(R.id.info_banehcp_text_view);
//        infoCourseHCPView.setText(Double.toString(round.getPlayers()[0].getCourseHandicap()));
        infoCourseHCPView.setText(Double.toString(round.getPlayers()[0].getCourseHandicap()));
        //indlæs scoretotalen
        scoreHoles[scoreHoles.length-2] = (TextView) findViewById(R.id.score_total_text_view);
        scoreHoles[scoreHoles.length-1] = (TextView) findViewById(holeScores[holeScores.length-1]);

        for(int id=0; id < (holeNumbers.length-2); id++){

            TextView holeNumber = (TextView) findViewById(holeNumbers[id]);
            numberHoles[id] = holeNumber;
            numberHoles[id].setText(Integer.toString(round.getCourse().getHoles().get(id).getNumber()));

            TextView holeLength = (TextView) findViewById(holeLengths[id]);
            lengthHoles[id] = holeLength;
            lengthHoles[id].setText(Integer.toString(round.getCourse().getHoles().get(id).getLength()));

            TextView holePar = (TextView) findViewById(holePars[id]);
            parHoles[id] = holePar;
            parHoles[id].setText(Integer.toString(round.getCourse().getHoles().get(id).getPar()));

            TextView holeHCP = (TextView) findViewById(holeHCPs[id]);
            hcpHoles[id] = holeHCP;
            hcpHoles[id].setText(Integer.toString(round.getCourse().getHoles().get(id).getHcp()));

            TextView holeStroke = (TextView) findViewById(holeStrokes[id]);
            strokesHoles[id] = holeStroke;
//            strokesHoles[id].setText(String.format("%.0f", round.getPlayers()[0].coursePar(round.getCourse(), id)));
            strokesHoles[id].setText(String.format("%.0f", round.getPlayers()[0].coursePar(round.getCourse(), id)));


            TextView holeScore = (TextView) findViewById(holeScores[id]);
            scoreHoles[id] = holeScore;

            if(round.getPlayers()[0].getScore()[id] != 0) {

                scoreHoles[id].setText(Integer.toString(round.getPlayers()[0].getScore()[id]));

                if(round.getPlayers()[0].getScoreTotalFrontNine() != 0)
                    scoreHoles[scoreHoles.length - 2].setText(Integer.toString(round.getPlayers()[0].getScoreTotalFrontNine()));
                else
                    scoreHoles[scoreHoles.length - 2].setText("-");
                if(round.getPlayers()[0].getScoreTotalBackNine() != 0)
                    scoreHoles[scoreHoles.length - 1].setText(Integer.toString(round.getPlayers()[0].getScoreTotalBackNine()));
                else
                    scoreHoles[scoreHoles.length - 1].setText("-");

            }

            scoreHoles[id].setOnEditorActionListener(new OnClickAddScore());

        }

        lengthHoles[holeLengths.length-2] = (TextView) findViewById(R.id.length_total_text_view);
        lengthHoles[holeLengths.length-2].setText(Integer.toString(round.getCourse().getCourseLengthFrontNine()));

        parHoles[holePars.length-2] = (TextView) findViewById(holePars[holePars.length-2]);
        parHoles[holePars.length-2].setText(Integer.toString(round.getCourse().getParFrontNine()));

        for(int i = 0; i < (holeStrokes.length/2-1); i++){

            strokeTotal += Integer.parseInt(strokesHoles[i].getText().toString());

        }
        strokesHoles[holeStrokes.length-1] = (TextView) findViewById(R.id.strokes_total_text_view);
        strokesHoles[holeStrokes.length-1].setText(Integer.toString(strokeTotal));


        lengthHoles[holeLengths.length-1] = (TextView) findViewById(holeLengths[holeLengths.length-1]);
        lengthHoles[holeLengths.length-1].setText(Integer.toString(round.getCourse().getCourseLengthBackNine()));

        parHoles[holePars.length-1] = (TextView) findViewById(holePars[holePars.length-1]);
        parHoles[holePars.length-1].setText(Integer.toString(round.getCourse().getParBackNine()));

        for(int i = 9; i < (holeStrokes.length-2); i++){

            strokeTotalBackNine += Integer.parseInt(strokesHoles[i].getText().toString());

        }
        strokesHoles[holeStrokes.length-1] = (TextView) findViewById(R.id.strokes_backnine_text_view);
        strokesHoles[holeStrokes.length-1].setText(Integer.toString(strokeTotalBackNine));
    }

    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if( keyCode== KeyEvent.KEYCODE_BACK)
        {

            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("Scorecard", round);
            i.putExtra("loadPlayer", true);
            startActivity(i);
            finish();

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("Afslut programmet?")
                    .setCancelable(false)
                    .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            finish();

                        }
                    }).setNegativeButton("Nej", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {

                    dialog.cancel();
                }
            });

            AlertDialog alert = builder.create();

            alert.show();
        }

        return super.onKeyDown(keyCode, event);

    }*/

     /*@Override
    protected void onResume() {
        super.onResume();

        if(fromHistory){
//            currentRound = (Round) getIntent().getSerializableExtra("History");
//            startActivity(getIntent());
            setScoresOutnIn();
            setScoreTotal();
            setRelativeScore();
            setPoints();
        }
    }*/

    //    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        savedState.putSerializable("Scorecard not destroyed", currentRound);
//    }
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//        outState.putSerializable("Scorecard saved", currentRound);
//    }


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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goToHole(View view) {

        if(round.getCourse().holeImages() != null) {
            int showHole = 0;

            for (int i = 0; i < holeNumbers.length-2; i++){

                if(view.getId() == holeNumbers[i]){
                    showHole = (i+1);
                    //break;
                }
            }

            Intent i = new Intent(this, HoleOverview.class);
            i.putExtra("show", showHole);
            i.putExtra("Scorecard", round);


            startActivity(i);
            finish();
        }
        else {
            Toast.makeText(this, getString(R.string.course_pics_not_implemented_text), Toast.LENGTH_LONG).show();
        }
    }

    public void showMain(View view) {

        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("Scorecard", round);
        i.putExtra("loadPlayer", true);
        startActivity(i);
        finish();
    }

    private class OnClickAddScore implements TextView.OnEditorActionListener {



        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {


            if(i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT){

                for(int id = 0; id < holeScores.length-2; id++) {

                    if(!scoreHoles[id].getText().toString().equals("")) {

                        if (textView.getId() == holeScores[id]) {

                            round.getPlayers()[0].getScore()[id] = Integer.parseInt(scoreHoles[id].getText().toString());

                        }

                    }
                    else{

//                        round.getPlayers()[0].setHoleScore(id, 0);

                    }
                    scoreTotal = round.getPlayers()[0].getScoreTotalFrontNine()+round.getPlayers()[0].getScoreTotalBackNine();
                    relativeScore = round.getCourse().getScoreRelative(round.getPlayers()[0].getScore());

//                    points = round.getPlayers()[0].getPoints(round.getCourse());
                    points = round.getPlayers()[0].getPoints(round.getCourse());
                    pointsScoreText.setText(Integer.toString(points)+" p");

                    if(round.getPlayers()[0].getScoreTotalFrontNine() != 0)
                        scoreHoles[scoreHoles.length - 2].setText(Integer.toString(round.getPlayers()[0].getScoreTotalFrontNine()));
                    else
                        scoreHoles[scoreHoles.length - 2].setText("-");
                    if(round.getPlayers()[0].getScoreTotalBackNine() != 0)
                        scoreHoles[scoreHoles.length - 1].setText(Integer.toString(round.getPlayers()[0].getScoreTotalBackNine()));
                    else
                        scoreHoles[scoreHoles.length - 1].setText("-");

                    if(scoreTotal != 0){

                        scoreTotalText.setText(
                                Integer.toString(scoreTotal));
                    }
                    else
                        scoreTotalText.setText("-");

                    if(relativeScore > 0){

                        relativeScoreText.setText("+" + relativeScore);

                    }
                    else if (relativeScore < 0){
                        relativeScoreText.setText(Integer.toString(relativeScore));
                    }
                    else
                        relativeScoreText.setText(R.string.title_par_text_view);

                }
            }
            return false;
        }
    }
}
