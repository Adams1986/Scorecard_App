package com.androidbuilds.simonadams.scorecardapp.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.androidbuilds.simonadams.scorecardapp.dto.Round;
import com.androidbuilds.simonadams.scorecardapp.database.ExtendedHistoryListAdapter;
import com.androidbuilds.simonadams.scorecardapp.database.PlayerRoundDatabase;
import com.androidbuilds.simonadams.scorecardapp.R;


public class ExtendedHistoryActivity extends AppCompatActivity {

    private PlayerRoundDatabase database;
    private Round round;
    private Round test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        test = null;
        database = new PlayerRoundDatabase(this);
        round = (Round) getIntent().getSerializableExtra("Scorecard");


//        final HashMap temp = database.getExtendedHistoryContent(this, round);


        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.expandable_list_view);
        final ExtendedHistoryListAdapter listAdapter = new ExtendedHistoryListAdapter(
                this,
                database.getExtendedHistoryList(round),
                database.getExtendedHistoryContent(this, round));

        expandableListView.setAdapter(listAdapter);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPos, long l) {

                Toast.makeText(ExtendedHistoryActivity.this,
                        getString(R.string.how_to_delete_round_info_text), Toast.LENGTH_LONG).show();

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

                    test = (Round) listAdapter.getChild(groupPosition, childPosition);

                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                    builder.setMessage(getString(R.string.delete_history_dialog_text))
                            .setCancelable(false)
                            .setPositiveButton(getString(R.string.dialog_yes_text), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    database.deleteExtendedRound(test.getIdentifier());
                                    database.deleteRound(test.getIdentifier());

                                    finish();
                                    startActivity(getIntent());

                                }
                            }).setNegativeButton(getString(R.string.dialog_no_text), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {

                            dialog.cancel();
                        }
                    });

                    AlertDialog alert = builder.create();

                    alert.show();

                    // Return true as we are handling the event.
                    return true;
                }

                return false;
            }
        });
    }

    public void onEditRound(View view) {

        round = test;

        Intent intent = new Intent(ExtendedHistoryActivity.this, ScorecardOverview.class);

        intent.putExtra("History", true);
        intent.putExtra("RoundFromHistory", round);

        startActivity(intent);
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
