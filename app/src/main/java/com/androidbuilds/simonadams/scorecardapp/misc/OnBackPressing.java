package com.androidbuilds.simonadams.scorecardapp.misc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * Created by simonadams on 28/06/15.
 */
public class OnBackPressing {


    public static void onBackPressed(Activity activity) {

        final Activity activity1 = activity;

        AlertDialog.Builder builder = new AlertDialog.Builder(activity1);

        builder.setMessage("Afslut programmet?")
                .setCancelable(false)
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        activity1.finish();

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
}
