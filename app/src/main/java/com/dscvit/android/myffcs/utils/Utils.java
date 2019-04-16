package com.dscvit.android.myffcs.utils;

import android.util.Log;

import com.dscvit.android.myffcs.models.ClassroomResponse;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Utils {
    private static final String TAG = "Utils";
    private static HashMap<String, String> slotToDays = null;
    private static HashMap<String, HashMap<String, String>> timingMap = null;
    private static BidiMap<String, String> slotsThatCanClash = null;

    public static List<String> getSlotDays(String slot) {
        slot = slot.replaceAll("\n", "");
        if (!slot.equals("NIL")) {
            return new ArrayList<>(Arrays.asList(Objects.requireNonNull(Utils.getSlotToDay().get(slot)).split("\\|")));
        } else {
            return new ArrayList<>();
        }
    }

    public static List<String> getSlotsFromCourse(ClassroomResponse course) {
        return new ArrayList<>(Arrays.asList(course.getSlot().split("\\+")));
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

    private static HashMap<String, HashMap<String, String>> getTimingMap() {
        if (timingMap == null) {
            HashMap<String, String> mondayMap = new HashMap<>();
            mondayMap.put("A1", "8:00-8:50 AM");
            mondayMap.put("F1", "9:00-9:50 AM");
            mondayMap.put("D1", "10:00-10:50 AM");
            mondayMap.put("TB1", "11:00-11:50 AM");
            mondayMap.put("TG1", "12:00-12:50 PM");
            mondayMap.put("A2", "2:00-2:50 PM");
            mondayMap.put("F2", "3:00-3:50 PM");
            mondayMap.put("D2", "4:00-4:50 PM");
            mondayMap.put("TB2", "5:00-5:50 PM");
            mondayMap.put("TG2", "6:00-6:50 PM");
            mondayMap.put("V3", "7:00-7:50 PM");
            // Labs
            mondayMap.put("L1", "8:00-8:45 AM");
            mondayMap.put("L2", "8:46-9:30 AM");
            mondayMap.put("L3", "10:00-10:45 AM");
            mondayMap.put("L4", "10:46-11:30 AM");
            mondayMap.put("L5", "11:31 AM -12:15 PM");
            mondayMap.put("L6", "12:16-1:00 PM");
            mondayMap.put("L31", "2:00-2:45 PM");
            mondayMap.put("L32", "2:46-3:30 PM");
            mondayMap.put("L33", "4:00-4:45 PM");
            mondayMap.put("L34", "4:46-5:30 PM");
            mondayMap.put("L35", "5:31-6:15 PM");
            mondayMap.put("L36", "6:16-7:00 PM");

            HashMap<String, String> tuesdayMap = new HashMap<>();
            tuesdayMap.put("B1", "8:00-8:50 AM");
            tuesdayMap.put("G1", "9:00-9:50 AM");
            tuesdayMap.put("E1", "10:00-10:50 AM");
            tuesdayMap.put("TC1", "11:00-11:50 AM");
            tuesdayMap.put("TAA1", "12:00-12:50 PM");
            tuesdayMap.put("B2", "2:00-2:50 PM");
            tuesdayMap.put("G2", "3:00-3:50 PM");
            tuesdayMap.put("E2", "4:00-4:50 PM");
            tuesdayMap.put("TC2", "5:00-5:50 PM");
            tuesdayMap.put("TAA2", "6:00-6:50 PM");
            tuesdayMap.put("V4", "7:00-7:50 PM");
            // Labs
            tuesdayMap.put("L7", "8:00-8:45 AM");
            tuesdayMap.put("L8", "8:46-9:30 AM");
            tuesdayMap.put("L9", "10:00-10:45 AM");
            tuesdayMap.put("L10", "10:46-11:30 AM");
            tuesdayMap.put("L11", "11:31 AM -12:15 PM");
            tuesdayMap.put("L12", "12:16-1:00 PM");
            tuesdayMap.put("L37", "2:00-2:45 PM");
            tuesdayMap.put("L38", "2:46-3:30 PM");
            tuesdayMap.put("L39", "4:00-4:45 PM");
            tuesdayMap.put("L40", "4:46-5:30 PM");
            tuesdayMap.put("L41", "5:31-6:15 PM");
            tuesdayMap.put("L42", "6:16-7:00 PM");

            HashMap<String, String> wednesdayMap = new HashMap<>();
            wednesdayMap.put("C1", "8:00-8:50 AM");
            wednesdayMap.put("A1", "9:00-9:50 AM");
            wednesdayMap.put("F1", "10:00-10:50 AM");
            wednesdayMap.put("V1", "11:00-11:50 AM");
            wednesdayMap.put("V2", "12:00-12:50 PM");
            wednesdayMap.put("C2", "2:00-2:50 PM");
            wednesdayMap.put("A2", "3:00-3:50 PM");
            wednesdayMap.put("F2", "4:00-4:50 PM");
            wednesdayMap.put("TD2", "5:00-5:50 PM");
            wednesdayMap.put("TBB2", "6:00-6:50 PM");
            wednesdayMap.put("V5", "7:00-7:50 PM");
            // Labs
            wednesdayMap.put("L13", "8:00-8:45 AM");
            wednesdayMap.put("L14", "8:46-9:30 AM");
            wednesdayMap.put("L15", "10:00-10:45 AM");
            wednesdayMap.put("L16", "10:46-11:30 AM");
//            wednesdayMap.put("L11", "11:31 AM -12:15 PM");
//            wednesdayMap.put("L12", "12:16-1:00 PM");
            wednesdayMap.put("L43", "2:00-2:45 PM");
            wednesdayMap.put("L44", "2:46-3:30 PM");
            wednesdayMap.put("L45", "4:00-4:45 PM");
            wednesdayMap.put("L46", "4:46-5:30 PM");
            wednesdayMap.put("L47", "5:31-6:15 PM");
            wednesdayMap.put("L48", "6:16-7:00 PM");

            HashMap<String, String> thursdayMap = new HashMap<>();
            thursdayMap.put("D1", "8:00-8:50 AM");
            thursdayMap.put("B1", "9:00-9:50 AM");
            thursdayMap.put("G1", "10:00-10:50 AM");
            thursdayMap.put("TE1", "11:00-11:50 AM");
            thursdayMap.put("TCC1", "12:00-12:50 PM");
            thursdayMap.put("D2", "2:00-2:50 PM");
            thursdayMap.put("B2", "3:00-3:50 PM");
            thursdayMap.put("G2", "4:00-4:50 PM");
            thursdayMap.put("TE2", "5:00-5:50 PM");
            thursdayMap.put("TCC2", "6:00-6:50 PM");
            thursdayMap.put("V6", "7:00-7:50 PM");
            // Labs
            thursdayMap.put("L19", "8:00-8:45 AM");
            thursdayMap.put("L20", "8:46-9:30 AM");
            thursdayMap.put("L21", "10:00-10:45 AM");
            thursdayMap.put("L22", "10:46-11:30 AM");
            thursdayMap.put("L23", "11:31 AM -12:15 PM");
            thursdayMap.put("L24", "12:16-1:00 PM");
            thursdayMap.put("L49", "2:00-2:45 PM");
            thursdayMap.put("L50", "2:46-3:30 PM");
            thursdayMap.put("L51", "4:00-4:45 PM");
            thursdayMap.put("L52", "4:46-5:30 PM");
            thursdayMap.put("L53", "5:31-6:15 PM");
            thursdayMap.put("L54", "6:16-7:00 PM");

            HashMap<String, String> fridayMap = new HashMap<>();
            fridayMap.put("E1", "8:00-8:50 AM");
            fridayMap.put("C1", "9:00-9:50 AM");
            fridayMap.put("TA1", "10:00-10:50 AM");
            fridayMap.put("TF1", "11:00-11:50 AM");
            fridayMap.put("TD1", "12:00-12:50 PM");
            fridayMap.put("E2", "2:00-2:50 PM");
            fridayMap.put("C2", "3:00-3:50 PM");
            fridayMap.put("TA2", "4:00-4:50 PM");
            fridayMap.put("TF2", "5:00-5:50 PM");
            fridayMap.put("TDD2", "6:00-6:50 PM");
            fridayMap.put("V7", "7:00-7:50 PM");
            // Labs
            fridayMap.put("L25", "8:00-8:45 AM");
            fridayMap.put("L26", "8:46-9:30 AM");
            fridayMap.put("L27", "10:00-10:45 AM");
            fridayMap.put("L28", "10:46-11:30 AM");
            fridayMap.put("L29", "11:31 AM -12:15 PM");
            fridayMap.put("L30", "12:16-1:00 PM");
            fridayMap.put("L55", "2:00-2:45 PM");
            fridayMap.put("L56", "2:46-3:30 PM");
            fridayMap.put("L57", "4:00-4:45 PM");
            fridayMap.put("L58", "4:46-5:30 PM");
            fridayMap.put("L59", "5:31-6:15 PM");
            fridayMap.put("L60", "6:16-7:00 PM");


            timingMap = new HashMap<>();
            timingMap.put("Monday", mondayMap);
            timingMap.put("Tuesday", tuesdayMap);
            timingMap.put("Wednesday", wednesdayMap);
            timingMap.put("Thursday", thursdayMap);
            timingMap.put("Friday", fridayMap);
        }

        return timingMap;
    }

    private static String getTimingFromSlotAndDay(String slot, String day) {
        slot = slot.replaceAll("\n", "");
//        Log.d(TAG, "getTimingFromSlotAndDay: " + slot + " " + day);
        return Objects.requireNonNull(getTimingMap().get(day)).get(slot);
    }

    public static String getTimingFromCourseAndDay(ClassroomResponse response, String day) {
        List<String> daySlotList = new ArrayList<>();
        for (String slot : getSlotsFromCourse(response)) {
            for (String days : getSlotDays(slot)) {
                if (day.equals(days)) {
                    daySlotList.add(slot);
                }
            }
        }
        String startTime = getTimingFromSlotAndDay(daySlotList.get(0), day).split("-")[0];
        String endTime = getTimingFromSlotAndDay(daySlotList.get(daySlotList.size() - 1), day).split("-")[1];
        return startTime + "-" + endTime;
    }

    private static HashMap<String, String> getSlotToDay() {
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
            // Labs
            slotToDays.put("L1", "Monday");
            slotToDays.put("L2", "Monday");
            slotToDays.put("L3", "Monday");
            slotToDays.put("L4", "Monday");
            slotToDays.put("L5", "Monday");
            slotToDays.put("L6", "Monday");
            slotToDays.put("L31", "Monday");
            slotToDays.put("L32", "Monday");
            slotToDays.put("L33", "Monday");
            slotToDays.put("L34", "Monday");
            slotToDays.put("L35", "Monday");
            slotToDays.put("L36", "Monday");

            slotToDays.put("L7", "Tuesday");
            slotToDays.put("L8", "Tuesday");
            slotToDays.put("L9", "Tuesday");
            slotToDays.put("L10", "Tuesday");
            slotToDays.put("L11", "Tuesday");
            slotToDays.put("L12", "Tuesday");
            slotToDays.put("L37", "Tuesday");
            slotToDays.put("L38", "Tuesday");
            slotToDays.put("L39", "Tuesday");
            slotToDays.put("L40", "Tuesday");
            slotToDays.put("L41", "Tuesday");
            slotToDays.put("L42", "Tuesday");

            slotToDays.put("L13", "Wednesday");
            slotToDays.put("L14", "Wednesday");
            slotToDays.put("L15", "Wednesday");
            slotToDays.put("L16", "Wednesday");
            slotToDays.put("L43", "Wednesday");
            slotToDays.put("L44", "Wednesday");
            slotToDays.put("L45", "Wednesday");
            slotToDays.put("L46", "Wednesday");
            slotToDays.put("L47", "Wednesday");
            slotToDays.put("L48", "Wednesday");

            slotToDays.put("L19", "Thursday");
            slotToDays.put("L20", "Thursday");
            slotToDays.put("L21", "Thursday");
            slotToDays.put("L22", "Thursday");
            slotToDays.put("L23", "Thursday");
            slotToDays.put("L24", "Thursday");
            slotToDays.put("L49", "Thursday");
            slotToDays.put("L50", "Thursday");
            slotToDays.put("L51", "Thursday");
            slotToDays.put("L52", "Thursday");
            slotToDays.put("L53", "Thursday");
            slotToDays.put("L54", "Thursday");

            slotToDays.put("L25", "Friday");
            slotToDays.put("L26", "Friday");
            slotToDays.put("L27", "Friday");
            slotToDays.put("L28", "Friday");
            slotToDays.put("L29", "Friday");
            slotToDays.put("L30", "Friday");
            slotToDays.put("L55", "Friday");
            slotToDays.put("L56", "Friday");
            slotToDays.put("L57", "Friday");
            slotToDays.put("L58", "Friday");
            slotToDays.put("L59", "Friday");
            slotToDays.put("L60", "Friday");

        }
        return slotToDays;
    }

    public static boolean canClashWith(ClassroomResponse courseToSave, List<ClassroomResponse> savedCourses) {
        if (slotsThatCanClash == null) {
            slotsThatCanClash = new DualHashBidiMap<>();

            slotsThatCanClash.put("A1", "L1|L14");
            slotsThatCanClash.put("B1", "L7|L20");
            slotsThatCanClash.put("C1", "L13|L26");
            slotsThatCanClash.put("D1", "L19|L3");
            slotsThatCanClash.put("E1", "L25|L9");
            slotsThatCanClash.put("F1", "L2|L15");
            slotsThatCanClash.put("G1", "L8|L21");

            slotsThatCanClash.put("TA1", "L27");
            slotsThatCanClash.put("TB1", "L4");
            slotsThatCanClash.put("TC1", "L10");
            slotsThatCanClash.put("V1", "L16");
            slotsThatCanClash.put("TE1", "L22");
            slotsThatCanClash.put("TF1", "L28");
            slotsThatCanClash.put("TG1", "L5");

            slotsThatCanClash.put("TAA1", "L11");
            slotsThatCanClash.put("TCC1", "L23");
            slotsThatCanClash.put("TD1", "L29");

            slotsThatCanClash.put("A2", "L31|L44");
            slotsThatCanClash.put("B2", "L37|L50");
            slotsThatCanClash.put("C2", "L43|L56");
            slotsThatCanClash.put("D2", "L49|L33");
            slotsThatCanClash.put("E2", "L55|L39");
            slotsThatCanClash.put("F2", "L32|L45");
            slotsThatCanClash.put("G2", "L38|L51");

            slotsThatCanClash.put("TA2", "L57");
            slotsThatCanClash.put("TB2", "L34");
            slotsThatCanClash.put("TC2", "L40");
            slotsThatCanClash.put("TD2", "L46");
            slotsThatCanClash.put("TE2", "L52");
            slotsThatCanClash.put("TF2", "L58");
            slotsThatCanClash.put("TG2", "L35");

            slotsThatCanClash.put("TAA2", "L41");
            slotsThatCanClash.put("TBB2", "L47");
            slotsThatCanClash.put("TCC2", "L53");
            slotsThatCanClash.put("TDD2", "L59");

            slotsThatCanClash.put("V3", "L36");
            slotsThatCanClash.put("V4", "L42");
            slotsThatCanClash.put("V5", "L48");
            slotsThatCanClash.put("V6", "L54");
            slotsThatCanClash.put("V7", "L60");

        }
        for (ClassroomResponse item : savedCourses) {
            for (String savedSlot : getSlotsFromCourse(item)) {
                for (String currentSlot : getSlotsFromCourse(courseToSave)) {
                    if (savedSlot.equals(currentSlot) && !savedSlot.equals("NIL")) {
                        return true;
                    }
                    try {
                        for (String splitSlot : Objects.requireNonNull(slotsThatCanClash.get(currentSlot)).split("\\|")) {
                            if (savedSlot.equals(splitSlot)) {
                                return true;
                            }
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "canClashWith: ", e);
                    }

                    for (String value : slotsThatCanClash.values()) {
                        if (value.contains(currentSlot)) {
                            String key = slotsThatCanClash.getKey(value);
                            if (savedSlot.equals(key)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
