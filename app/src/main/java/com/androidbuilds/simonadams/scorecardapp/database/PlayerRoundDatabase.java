package com.androidbuilds.simonadams.scorecardapp.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.androidbuilds.simonadams.scorecardapp.courses.BrondbyCourse;
import com.androidbuilds.simonadams.scorecardapp.courses.HarekaerCourse;
import com.androidbuilds.simonadams.scorecardapp.courses.MidtsjaellandCourse;
import com.androidbuilds.simonadams.scorecardapp.dto.Player;
import com.androidbuilds.simonadams.scorecardapp.R;
import com.androidbuilds.simonadams.scorecardapp.courses.ReeCourse;
import com.androidbuilds.simonadams.scorecardapp.dto.Round;
import com.androidbuilds.simonadams.scorecardapp.courses.SoroeCourse;
import com.androidbuilds.simonadams.scorecardapp.courses.VaerebroCourse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by simonadams on 28/07/15.
 */
public class PlayerRoundDatabase extends SQLiteOpenHelper {


    public PlayerRoundDatabase(Context context) {

        super(context, "Rounds.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS players (id INTEGER PRIMARY KEY, name VARCHAR, handicap DOUBLE);");

        sqLiteDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS round (roundID INTEGER PRIMARY KEY, date TEXT DEFAULT CURRENT_TIMESTAMP, " +
                        "playerID INTEGER NOT NULL, courseName TEXT NOT NULL, " +
                        "FOREIGN KEY (playerID) REFERENCES players (id) );");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS hole (holeID INTEGER PRIMARY KEY, holenumber INTEGER NOT NULL, " +
                "score INTEGER, idRound INTEGER NOT NULL, " +
                "FOREIGN KEY (idRound) REFERENCES round (roundID) );");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS roundOverview (overviewID INTEGER PRIMARY KEY, date TEXT DEFAULT " +
                "CURRENT_TIMESTAMP, courseName TEXT NOT NULL, playerID INTEGER NOT NULL, totalScore INTEGER NOT NULL, " +
                "relativeScore INTEGER NOT NULL, pointsTotal INTEGER NOT NULL, " +
                "FOREIGN KEY (playerID) REFERENCES players (id) );");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int versionOld, int versionNew) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS round, holes");
        onCreate(sqLiteDatabase);

    }
    public void saveTotals(Round round) {

        SQLiteDatabase database = getWritableDatabase();

        database.execSQL("INSERT INTO roundOverview (courseName, playerID, totalScore, relativeScore, pointsTotal)" +
                "values('"
                + round.getCourse().getClass().toString().substring(56, (round.getCourse().getClass().toString().length() - 6))
                + "', " +
                "(SELECT id FROM players WHERE name = '" + round.getPlayers()[0].getName()
                + "'), " + (round.getPlayers()[0].getScoreTotalBackNine() + round.getPlayers()[0].getScoreTotalFrontNine())
                + ", " + round.getCourse().getScoreRelative(round.getPlayers()[0].getScore())
//                + ", " + round.getPlayers()[0].getPoints(round.getCourse()) + ");");
                + ", " + round.getPlayers()[0].getPoints(round.getCourse()) + ");");

    }

    public void saveNewRound(Round round){

        SQLiteDatabase database = this.getWritableDatabase();

//        database.execSQL("INSERT INTO round (playerID, courseName) VALUES(1, 'Harekær');");

        //ikke sikkert det her duer
        database.execSQL("INSERT INTO round (playerID, courseName) VALUES((SELECT id FROM players WHERE name = '"
                + round.getPlayers()[0].getName() + "'), '" + round.getCourse().getClass().toString() + "' ); ");


        Cursor metaData = database.rawQuery("SELECT roundID FROM round ORDER BY date DESC LIMIT 1;", null);

        if(metaData.moveToFirst()){
            round.setIdentifier(metaData.getInt(metaData.getColumnIndex("roundID")));
        }


        for(int i = 0; i < round.getPlayers()[0].getScore().length; i++) {
            database.execSQL("INSERT INTO hole (holenumber, score, idRound) values("+
                    round.getCourse().getHoles().get(i).getNumber()+ ", "+ round.getPlayers()[0].getScore()[i] +", " +
                    round.getIdentifier() + ");");
        }

        metaData.close();
    }

    public void updatePlayer(Round round) {

        SQLiteDatabase database = this.getWritableDatabase();

        database.execSQL(
                "UPDATE players SET name = '" + round.getPlayers()[0].getName()
                        + "', handicap = '" + round.getPlayers()[0].getHandicap() + "' WHERE id = 1;");
    }

    public void createNewPlayer(Round round) {

        SQLiteDatabase database = this.getWritableDatabase();

        database.execSQL("INSERT INTO players (name, handicap)" +
                " values('" + round.getPlayers()[0].getName() + "', " + round.getPlayers()[0].getHandicap() + ");");
    }

    public boolean getPlayersFromDatabase(Round round, int[] score) {

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor metaData = database.rawQuery("SELECT * FROM players", null);

        int nameColumn = metaData.getColumnIndex("name");
        int handicapColumn = metaData.getColumnIndex("handicap");

        metaData.moveToFirst();

        if(metaData.moveToFirst()) {
            round.getPlayers()[0] =
                    new Player(metaData.getString(nameColumn), metaData.getDouble(handicapColumn), score, round.getCourse());

            metaData.close();

            return true;
        }
        metaData.close();

        return false;
    }

    public List<String> getHistoryList(Round round) {
        SQLiteDatabase transactionDB = this.getReadableDatabase();

        Cursor metaData = transactionDB.rawQuery("SELECT * FROM roundOverview WHERE playerID = " +
                "(SELECT id from players WHERE name ='" + round.getPlayers()[0].getName() + "');", null);

        List<String> history = new ArrayList<String>();

        int idColumn = metaData.getColumnIndex("overviewID");
        int dateColumn = metaData.getColumnIndex("date");
        int totalScoreColumn = metaData.getColumnIndex("totalScore");
        int relativeScoreColumn = metaData.getColumnIndex("relativeScore");
        int pointsTotalColumn = metaData.getColumnIndex("pointsTotal");
        int courseNameColumn = metaData.getColumnIndex("courseName");

        if(metaData.moveToFirst()) {

            do {
                history.add(metaData.getInt(idColumn) + "\t" + metaData.getString(dateColumn).substring(0, 10) + "\t" +
                        metaData.getString(courseNameColumn));
            }
            while (metaData.moveToNext());
        }
        metaData.close();

        return history;
    }

    public HashMap<String, List<String>> getHistoryContent(Context context, Round round) {
        SQLiteDatabase transactionDB = this.getReadableDatabase();

        Cursor metaData = transactionDB.rawQuery("SELECT * FROM roundOverview WHERE playerID = " +
                "(SELECT id from players WHERE name ='" + round.getPlayers()[0].getName() + "');", null);

        HashMap<String, List<String>> history = new HashMap<String, List<String>>();

        int idColumn = metaData.getColumnIndex("overviewID");
        int dateColumn = metaData.getColumnIndex("date");
        int totalScoreColumn = metaData.getColumnIndex("totalScore");
        int relativeScoreColumn = metaData.getColumnIndex("relativeScore");
        int pointsTotalColumn = metaData.getColumnIndex("pointsTotal");
        int courseNameColumn = metaData.getColumnIndex("courseName");

        if(metaData.moveToFirst()) {

            int i = 0;

            do {

                List<String> list = new ArrayList<String>();

                list.add(context.getString(R.string.date_history_text) + metaData.getString(dateColumn).substring(0, 10)
                        + context.getString(R.string.course_history_text) + metaData.getString(courseNameColumn)
                        + context.getString(R.string.score_history_text) + metaData.getInt(totalScoreColumn)
                        + context.getString(R.string.over_under_par_history_text) + metaData.getInt(relativeScoreColumn)
                        + context.getString(R.string.points_history_text) + metaData.getInt(pointsTotalColumn));

                //gemt id til at bruge til at slette inde fra anden menu, giver ekstra view for hvert element
//                list.add(metaData.getInt(idColumn)+"");

                history.put(getHistoryList(round).get(i), list);

                i++;

            }
            while (metaData.moveToNext());
        }
        metaData.close();

        return history;
    }



    public HashMap<String,List<Round>> getExtendedHistoryContent(Context context, Round round) {

        SQLiteDatabase database = this.getReadableDatabase();

        Cursor overViewMetaData = database.rawQuery("SELECT * FROM round WHERE playerID = " +
                "(SELECT id FROM players WHERE name = '" + round.getPlayers()[0].getName() + "');", null);


        int roundIDColumn = overViewMetaData.getColumnIndex("roundID");
        int courseNameColumn = overViewMetaData.getColumnIndex("courseName");


        //Måske gøre det her i for-loop med et Round-arrayliste, ellers kan man ikke få flere runder. Evt kan det lettere nedbrydes
        //Med de nye metoder.
        HashMap <String, List<Round>> allRounds = new HashMap<String, List<Round>>();



        if(overViewMetaData.moveToFirst()) {

            int content = 0;
            int header = 0;
            List<Round> rounds = null;


            do {

                rounds = new ArrayList<Round>();

                int id = overViewMetaData.getInt(roundIDColumn);

                String courseName = overViewMetaData.getString(courseNameColumn);

                Round tempRound = null;

                Player[] player = new Player[4];
                int[] score = new int[18];

                if (courseName.equals(BrondbyCourse.class.toString())) {

                    player[0] = new Player(
                            round.getPlayers()[0].getName(), round.getPlayers()[0].getHandicap(), score, new BrondbyCourse());
                    tempRound = new Round(new BrondbyCourse(), player);

                    /*setScore(player[0], tempRound.getIdentifier());*/


                } else if (courseName.equals(HarekaerCourse.class.toString())) {

                    player[0] = new Player(
                            round.getPlayers()[0].getName(), round.getPlayers()[0].getHandicap(), score, new HarekaerCourse());

                    tempRound = new Round(new HarekaerCourse(), player);


                } else if (courseName.equals(VaerebroCourse.class.toString())) {

                    player[0] = new Player(
                            round.getPlayers()[0].getName(), round.getPlayers()[0].getHandicap(), score, new VaerebroCourse());

                    tempRound = new Round(new VaerebroCourse(), player);


                } else if (courseName.equals(ReeCourse.class.toString())) {

                    player[0] = new Player(
                            round.getPlayers()[0].getName(), round.getPlayers()[0].getHandicap(), score, new ReeCourse());

                    tempRound = new Round(new ReeCourse(), player);


                } else if (courseName.equals(MidtsjaellandCourse.class.toString())) {

                    player[0] = new Player(
                            round.getPlayers()[0].getName(), round.getPlayers()[0].getHandicap(), score, new MidtsjaellandCourse());

                    tempRound = new Round(new MidtsjaellandCourse(), player);


                } else if (courseName.equals(SoroeCourse.class.toString())) {

                    player[0] = new Player(
                            round.getPlayers()[0].getName(), round.getPlayers()[0].getHandicap(), score, new SoroeCourse());

                    tempRound = new Round(new SoroeCourse(), player);

                }

                if (tempRound != null) {



                    tempRound.setIdentifier(id);
                    rounds.add(tempRound);
                    setScore(tempRound.getPlayers()[0], tempRound.getIdentifier());

                    Log.d("MEMEMEMEMEMEMEMEME", "Round-object: " + rounds.toString() + "Header: " + header);
                    Log.d("MEMEMEMEMEMEMEMEME", "Round-object: " + tempRound.toString());




                }


                allRounds.put(getExtendedHistoryList(round).get(header), rounds);

                header++;


            } while (overViewMetaData.moveToNext()) ;
        }

        overViewMetaData.close();


        return allRounds;
    }

    private void setScore(Player player, int id) {

        SQLiteDatabase database = getReadableDatabase();
        Cursor metaData = database.rawQuery("SELECT hole.holenumber, hole.score, round.date, hole.idRound, round.courseName FROM hole " +
                        "INNER JOIN round ON round.roundID = hole.idRound " +
                        "INNER JOIN players ON players.id = round.playerID WHERE name = '" + player.getName()
                        + "' AND roundID = " + id
                        + " ;",
                null);

        int idColumn = metaData.getColumnIndex("idRound");
        int holeNumberColumn = metaData.getColumnIndex("holenumber");
        int scoreColumn = metaData.getColumnIndex("score");
        int dateColumn = metaData.getColumnIndex("date");


        if (metaData.moveToFirst()) {


            do {
                player.setHoleScore(
                        (metaData.getInt(holeNumberColumn) - 1), metaData.getInt(scoreColumn));

            } while (metaData.moveToNext());
        }
    }

    public List<String> getExtendedHistoryList(Round round){
        SQLiteDatabase transactionDB = this.getReadableDatabase();

        Cursor metaData = transactionDB.rawQuery("SELECT * FROM round WHERE playerID = " +
                "(SELECT id from players WHERE name ='" + round.getPlayers()[0].getName() + "');", null);

        List<String> history = new ArrayList<String>();

        int idColumn = metaData.getColumnIndex("roundID");
        int dateColumn = metaData.getColumnIndex("date");
        int courseNameColumn = metaData.getColumnIndex("courseName");

        if (metaData.moveToFirst()) {

            do {
                history.add(metaData.getInt(idColumn) + "\t" + metaData.getString(dateColumn).substring(0, 10) + "\t" +
                        metaData.getString(courseNameColumn).substring(56, (metaData.getString(courseNameColumn).length() - 6)));
                Log.d("WWWWW", metaData.getString(courseNameColumn));
            }
            while (metaData.moveToNext());
        }
        metaData.close();

        return history;
    }

    public void deleteRound(int identifier) {

        SQLiteDatabase database = getWritableDatabase();

        database.execSQL("DELETE FROM roundOverview WHERE overviewID = " + identifier + ";");
    }

    public void deleteExtendedRound(int identifier) {

        SQLiteDatabase database = getWritableDatabase();

        database.execSQL("DELETE FROM round WHERE roundID = " + identifier + ";");
        Log.d("WWWWWWWWWWWW", "DELETE FROM round WHERE roundID = " + identifier + ";");
    }
}
