package com.androidbuilds.simonadams.scorecardapp.database;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.androidbuilds.simonadams.scorecardapp.R;
import com.androidbuilds.simonadams.scorecardapp.dto.Round;

import java.util.HashMap;
import java.util.List;

public class ExtendedHistoryListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<Round>> listDataContent;

    public ExtendedHistoryListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<Round>> listDataContent) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataContent = listDataContent;
    }

    public View getsChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.content_history, null);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.content_history_text_view);
        txtListChild.setText(childText);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        //final String childText = (String) getChild(groupPosition, childPosition);

        Round childRound = (Round) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.extended_content_history, null);
        }

        loadHistoryTable(convertView, childRound);

        return convertView;
    }

    private void loadHistoryTable(View convertView, Round childRound) {

        //Laver streger til hver celle/textview i for-loop (ikke alle cellerne der er i loop'et...)
        /*ShapeDrawable border = new ShapeDrawable(new RectShape());
        border.getPaint().setStyle(Paint.Style.STROKE);
        border.getPaint().setColor(Color.BLACK);*/
        //implementer ved textview.setBackGround(border);

        TextView [] holeNumberTextViews = new TextView[HistoryTableIDs.historyContentHoleNumbers.length];
        TextView [] strokesIndexTextViews = new TextView[HistoryTableIDs.historyContentHoleNumbers.length];
        TextView [] scoreTextViews = new TextView[HistoryTableIDs.historyContentHoleNumbers.length];

        for(int i = 0; i < HistoryTableIDs.historyContentHoleNumbers.length; i++){

            holeNumberTextViews[i] = (TextView) convertView.findViewById(HistoryTableIDs.historyContentHoleNumbers[i]);
            holeNumberTextViews[i].setText(childRound.getCourse().getHoles().get(i).getNumber()+"");

            strokesIndexTextViews[i] = (TextView) convertView.findViewById(HistoryTableIDs.historyContentStrokeIndex[i]);
            strokesIndexTextViews[i].setText(childRound.getCourse().getHoles().get(i).getHcp()+"");

            scoreTextViews[i] = (TextView) convertView.findViewById(HistoryTableIDs.historyContentScore[i]);

            if(childRound.getPlayers()[0].getScore()[i] != 0) {
                scoreTextViews[i].setText(childRound.getPlayers()[0].getScore()[i] + "");
            }
            else
                scoreTextViews[i].setText("-");



            //SI+Score
        }

        TextView pointsTotal = (TextView) convertView.findViewById(R.id.extended_content_history_points_text_view);
        pointsTotal.setText(childRound.getPlayers()[0].getPoints(childRound.getCourse()) + "");

        TextView handicap = (TextView) convertView.findViewById(R.id.extended_content_history_handicap_text_view);
        handicap.setText(childRound.getPlayers()[0].getHandicap() + "");

        TextView relativeScore = (TextView) convertView.findViewById(R.id.extended_content_history_relative_total_text_view);
        relativeScore.setText(childRound.getCourse().getScoreRelative(childRound.getPlayers()[0].getScore()) + "");

        TextView totalScore = (TextView) convertView.findViewById(R.id.extended_content_history_total_text_view);
        totalScore.setText(
                childRound.getPlayers()[0].getScoreTotalFrontNine()+childRound.getPlayers()[0].getScoreTotalBackNine() + "");


        TextView outScore = (TextView) convertView.findViewById(R.id.hole_out_total_score_content_text_view);
        outScore.setText(childRound.getPlayers()[0].getScoreTotalFrontNine() + "");

        TextView inScore = (TextView) convertView.findViewById(R.id.hole_in_total_score_content_text_view);
        inScore.setText(childRound.getPlayers()[0].getScoreTotalBackNine() + "");
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return listDataContent.get( listDataHeader.get(groupPosition) ).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    @Override
    public int getChildrenCount(int groupPosition) {

        return listDataContent.get( listDataHeader.get(groupPosition) ).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String headerTitle = (String) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.header_history, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.header_history_text_view);

        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

   }