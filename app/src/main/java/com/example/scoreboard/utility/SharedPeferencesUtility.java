package com.example.scoreboard.utility;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPeferencesUtility {

    private static final String TEAM_ID = "saved_team";
    private static final String PREFERENCES_KEY = "global_preferences";


    public  static void setSavedTeam(String teamID, Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES_KEY, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(TEAM_ID, teamID);
        editor.apply();
    }

    public static String getSavedTeam(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES_KEY, 0);
        return settings.getString(TEAM_ID, null);
    }
}
