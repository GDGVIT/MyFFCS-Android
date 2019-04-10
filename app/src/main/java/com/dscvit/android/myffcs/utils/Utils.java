package com.dscvit.android.myffcs.utils;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

import java.util.HashMap;

public class Utils {
    private static HashMap<String, String> slotToDays = null;

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static boolean containsDigit(String s) {
        boolean containsDigit = false;

        if (s != null && !s.isEmpty()) {
            for (char c : s.toCharArray()) {
                if (containsDigit = Character.isDigit(c)) {
                    break;
                }
            }
        }

        return containsDigit;
    }

    public static HashMap<String, String> getSlotToDay() {
        if (slotToDays == null) {
            slotToDays = new HashMap<>();
            slotToDays.put("A1", "Monday|Wednesday");
            slotToDays.put("B1", "Tuesday|Thursday");
            slotToDays.put("C1", "Wednesday|Friday");
            slotToDays.put("D1", "Thursday|Monday");
            slotToDays.put("E1", "Friday|Tuesday");
            slotToDays.put("F1", "Monday|Wednesday");
            slotToDays.put("G1", "Tuesday|Thursday");
            slotToDays.put("TA1", "Friday");
            slotToDays.put("TB1", "Monday");
            slotToDays.put("TC1", "Tuesday");
            slotToDays.put("V1", "Wednesday");
            slotToDays.put("TE1", "Thursday");
            slotToDays.put("TF1", "Friday");
            slotToDays.put("TG1", "Monday");
            slotToDays.put("TAA1", "Tuesday");
            slotToDays.put("V2", "Wednesday");
            slotToDays.put("TCC1", "Thursday");
            slotToDays.put("TD1", "Friday");
            // Afternoon slots
            slotToDays.put("A2", "Monday|Wednesday");
            slotToDays.put("B2", "Tuesday|Thursday");
            slotToDays.put("C2", "Wednesday|Friday");
            slotToDays.put("D2", "Thursday|Monday");
            slotToDays.put("E2", "Friday|Tuesday");
            slotToDays.put("F2", "Monday|Wednesday");
            slotToDays.put("G2", "Tuesday|Thursday");
            slotToDays.put("TA2", "Friday");
            slotToDays.put("TB2", "Monday");
            slotToDays.put("TC2", "Tuesday");
            slotToDays.put("TD2", "Wednesday");
            slotToDays.put("TE2", "Thursday");
            slotToDays.put("TF2", "Friday");
            slotToDays.put("TG2", "Monday");
            slotToDays.put("TAA2", "Tuesday");
            slotToDays.put("TBB2", "Wednesday");
            slotToDays.put("TCC2", "Thursday");
            slotToDays.put("TDD2", "Friday");
            slotToDays.put("V3", "Monday");
            slotToDays.put("V4", "Tuesday");
            slotToDays.put("V5", "Wednesday");
            slotToDays.put("V6", "Thursday");
            slotToDays.put("V7", "Friday");
        }
        return slotToDays;
    }
}
