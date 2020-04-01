package com.example.scoreboard.utility;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

public class HideSoftKeyUtility {
    public static void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) activity.getSystemService(
                            Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(), 0);

        } catch (Exception e) {

        }

    }
}
