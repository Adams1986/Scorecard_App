package com.androidbuilds.simonadams.scorecardapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.androidbuilds.simonadams.scorecardapp.dto.Round;
import com.androidbuilds.simonadams.scorecardapp.database.HistoryListAdapter;
import com.androidbuilds.simonadams.scorecardapp.database.PlayerRoundDatabase;
import com.androidbuilds.simonadams.scorecardapp.R;


public class HistoryActivity extends AppCompatActivity {

    PlayerRoundDatabase database;
    Round round;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        database = new PlayerRoundDatabase(this);
        round = (Round) getIntent().getSerializableExtra("Scorecard");

        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.expandable_list_view);
        final HistoryListAdapter listAdapter = new HistoryListAdapter(
                this,
                database.getHistoryList(round),
                database.getHistoryContent(this, round));

        expandableListView.setAdapter(listAdapter);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {

                Toast.makeText(HistoryActivity.this, "" + i, Toast.LENGTH_LONG).show();

                return false;
            }
        });

        expandableListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long id) {

                if (ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                    int groupPosition = ExpandableListView.getPackedPositionGroup(id);
                    int childPosition = ExpandableListView.getPackedPositionChild(id);

                    // You now have everything that you would as if this was an OnChildClickListener()
                    // Add your logic here.
                    Toast.makeText(HistoryActivity.this, listAdapter.getGroup(i-1).toString(), Toast.LENGTH_LONG).show();

                    //Kan ikke få en round ud af denne, så find anden måde hvis slet
                    /*Round round = (Round) listAdapter.getChild(groupPosition, childPosition);

                    database.deleteRound(round.getIdentifier());
                    finish();
                    startActivity(getIntent());*/

                    // Return true as we are handling the event.
                    return true;
                }

                return false;
            }
        });
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_history, menu);
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
    }*/
}
