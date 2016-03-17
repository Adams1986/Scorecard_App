package com.androidbuilds.simonadams.scorecardapp.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidbuilds.simonadams.scorecardapp.courses.BrondbyCourse;
import com.androidbuilds.simonadams.scorecardapp.courses.HarekaerCourse;
import com.androidbuilds.simonadams.scorecardapp.courses.MidtsjaellandCourse;
import com.androidbuilds.simonadams.scorecardapp.courses.ReeCourse;
import com.androidbuilds.simonadams.scorecardapp.courses.SoroeCourse;
import com.androidbuilds.simonadams.scorecardapp.courses.VaerebroCourse;
import com.androidbuilds.simonadams.scorecardapp.dto.Course;
import com.androidbuilds.simonadams.scorecardapp.dto.Player;
import com.androidbuilds.simonadams.scorecardapp.dto.Round;
import com.androidbuilds.simonadams.scorecardapp.database.PlayerRoundDatabase;
import com.androidbuilds.simonadams.scorecardapp.R;


public class MainActivity extends AppCompatActivity {

    private Player[] players;
    //private HarekaerCourse gcH;
    //private BrondbyCourse bif;
    private Course courseChosen;
    int score[];
    private Round round;
    private boolean playerAdded = false;
    private boolean fromScorecard = false;

    private Intent startRound;
    private EditText playerName;
    private EditText playerHandicap;

    private PlayerRoundDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
    }

    @Override
    public void onBackPressed() {

        if(fromScorecard) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage(getString(R.string.quit_round))
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
        else
            finish();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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



    public void onClickAddPlayer(View view) {

        playerAdded = true;

        double playerHcp = 0;

        if(!playerName.getText().toString().equals("") && !playerHandicap.getText().toString().equals("")) {

            playerHcp = Double.parseDouble(playerHandicap.getText().toString());

            players[0] = new Player(
                    playerName.getText().toString(), playerHcp, score, courseChosen);

            if(fromScorecard){
                database.updatePlayer(round);
            } else
                database.createNewPlayer(round);

            Toast.makeText(this, getString(R.string.player_added_toast), Toast.LENGTH_LONG).show();


            TextView playerNameView = (TextView) findViewById(R.id.player1_text_view);
            TextView playerHandicapView = (TextView) findViewById(R.id.handicap1_text_view);
            TextView playerCourseHCPView = (TextView) findViewById(R.id.coursehandicap1_text_view);

            playerNameView.setText(round.getPlayers()[0].getName());
            playerHandicapView.setText(Double.toString(players[0].getHandicap()));
            playerCourseHCPView.setText(String.format("%.0f", players[0].getCourseHandicap()));

            startRound.putExtra("Scorecard", round);
        }
        else
            Toast.makeText(this, getString(R.string.player_info_missing), Toast.LENGTH_LONG).show();

    }

    public void onClickStartRound(View view) {

        if(playerAdded) {
            goToScorecard();
        }
        else
            Toast.makeText(this, R.string.add_player_toast, Toast.LENGTH_LONG).show();

    }

    private void goToScorecard() {

        startRound.putExtra("Course", getIntent().getStringExtra("Course"));
        startActivity(startRound);
        finish();
    }

    public void initData() {

        playerName = (EditText) findViewById(R.id.name_edit_text);
        playerHandicap = (EditText) findViewById(R.id.handicap_edit_text);

        startRound = new Intent(this, ScorecardOverview.class);

        database = new PlayerRoundDatabase(this);

        TextView welcomeText = (TextView) findViewById(R.id.text_view_welcome);

        fromScorecard = getIntent().getBooleanExtra("loadPlayer", false);

        if (fromScorecard) {
            loadExistingData(welcomeText);

        }
        else {

            loadFromResources(welcomeText);

            if(database.getPlayersFromDatabase(round, score)) {
                startRound.putExtra("Scorecard", round);


                goToScorecard();
            }

        }
    }


    private void loadExistingData(TextView textView) {

        round = (Round) getIntent().getSerializableExtra("Scorecard");
        courseChosen = round.getCourse();
        score = round.getPlayers()[0].getScore();
        players = round.getPlayers();
        playerHandicap.setText(Double.toString(round.getPlayers()[0].getHandicap()));
        playerName.setText(round.getPlayers()[0].getName());
        textView.setText(getString(R.string.edit_info_text));
    }

    private void loadFromResources(TextView textView){

        String[] courses = getResources().getStringArray(R.array.golf_courses_database);

        String courseSelected = getIntent().getStringExtra("Course");

        if (courseSelected.equals(courses[0])) {
            courseChosen = new HarekaerCourse();
            textView.setText(getString(R.string.text_view_welcome) + "\n" + courses[0]);
        } else if (courseSelected.equals(courses[1])) {
            courseChosen = new BrondbyCourse();
            textView.setText(getString(R.string.text_view_welcome) + "\n" + courses[1]);
        } else if (courseSelected.equals(courses[2])) {
            courseChosen = new SoroeCourse();
            textView.setText(getString(R.string.text_view_welcome) + "\n" + courses[2]);
        } else if (courseSelected.equals(courses[3])) {
            courseChosen = new VaerebroCourse();
            textView.setText(getString(R.string.text_view_welcome) + "\n" + courses[3]);
        } else if (courseSelected.equals(courses[4])) {
            courseChosen = new ReeCourse();
            textView.setText(getString(R.string.text_view_welcome) + "\n" + courses[4]);
        } else if (courseSelected.equals(courses[5])) {
            courseChosen = new MidtsjaellandCourse();
            textView.setText(getString(R.string.text_view_welcome) + "\n" + courses[5]);
        } else {
            Toast.makeText(this, getString(R.string.feature_not_implemented), Toast.LENGTH_LONG).show();
            finish();
        }

        players = new Player[4];
        score = new int[18];
        round = new Round(courseChosen, players);
    }
}
