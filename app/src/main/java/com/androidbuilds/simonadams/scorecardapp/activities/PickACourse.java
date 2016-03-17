package com.androidbuilds.simonadams.scorecardapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.androidbuilds.simonadams.scorecardapp.R;

@SuppressWarnings("deprecation") //bg drawable metoderne
public class PickACourse extends AppCompatActivity {

    private Spinner courseSpinner;
    private String itemSelected;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_acourse);

        courseSpinner = (Spinner) findViewById(R.id.pick_a_course_spinner);

        addCoursesToSpinner();
        addItemListenerToSpinner();
    }

    private void goToMainActivity(){

        Intent goToMain = new Intent(this, MainActivity.class);
        goToMain.putExtra("Course", itemSelected);

        startActivity(goToMain);
    }

    private void addCoursesToSpinner(){

        ArrayAdapter<CharSequence>courseSpinnerAdapter
                = ArrayAdapter.createFromResource(this, R.array.golf_courses_database, android.R.layout.simple_spinner_item);

        courseSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        courseSpinner.setAdapter(courseSpinnerAdapter);
    }

    private void addItemListenerToSpinner(){

        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                itemSelected = adapterView.getItemAtPosition(i).toString();

                RelativeLayout rLayout = (RelativeLayout) findViewById(R.id.pick_course_layout);


                if (itemSelected.equals(getResources().getStringArray(R.array.golf_courses_database)[0])) {
                    rLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.harekaer));
                } else if (itemSelected.equals(getResources().getStringArray(R.array.golf_courses_database)[1])) {
                    rLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.broendby));
                } else if (itemSelected.equals(getResources().getStringArray(R.array.golf_courses_database)[2])) {
                    rLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.soroe));
                } else if (itemSelected.equals(getResources().getStringArray(R.array.golf_courses_database)[3])) {
                    rLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.vaerebro));
                } else if (itemSelected.equals(getResources().getStringArray(R.array.golf_courses_database)[4])) {
                    rLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.ree));
                } else if (itemSelected.equals(getResources().getStringArray(R.array.golf_courses_database)[5])) {
                    rLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.midtsjaelland));
                }
                else
                    rLayout.setBackgroundDrawable(getResources().getDrawable(android.R.color.holo_green_light));


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                itemSelected = "";

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pick_acourse, menu);
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

    public void confirmChoice(View view) {

        goToMainActivity();

    }
}
