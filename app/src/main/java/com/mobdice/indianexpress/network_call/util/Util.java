package com.mobdice.indianexpress.network_call.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
    public static String getSelectedDateFormat(String date) {
//        new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
        String convertedDat = null;
        try {
            Date parse = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z").parse(date);
            SimpleDateFormat formater1 = new SimpleDateFormat("EEE, MMM d, ''yy");
            convertedDat = formater1.format(parse);
            Log.e("DATE", date + " - to -" + convertedDat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        date = date.substring(0, date.indexOf("+") - 1);
        return date;
    }
}
