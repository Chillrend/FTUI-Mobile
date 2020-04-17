package com.rsypj.ftuimobile.helper;

import android.util.Log;

/**
 * Created Date: 22/05/2018
 * Author: dhadotid
 */
public class TimeCalculate {


    public final static String monthAgo =  " bulan";
    public final static String weekAgo =  " minggu";
    public final static String daysAgo =  " hari";
    public final static String hoursAgo = " jam";
    public final static String minAgo =  " menit";
    public final static String secAgo = " detik";
    static int second = 1000; // milliseconds
    static int minute = 60;
    static int hour = minute * 60;
    static int day = hour * 24;
    static int week = day * 7;
    static int month = day * 30;
    static int year = month * 12;

    public static String DateDifference(long created, long updated) {
        long diff = 0;
        diff = updated - created;

        int diffInSec = Math.abs((int) (diff / (second)));
        String difference = "";
        if(diffInSec < minute)
        {
            difference = diffInSec+secAgo;
        }
        else if((diffInSec / hour) < 1)
        {
            difference = (diffInSec/minute)+minAgo;
        }
        else if((diffInSec/ day) < 1)
        {
            difference = (diffInSec/hour)+hoursAgo;
        }
        else if((diffInSec/ week) < 1)
        {
            difference = (diffInSec/day)+daysAgo;
        }
        else if((diffInSec/month)<1)
        {
            difference = (diffInSec / week)+weekAgo;
        }
        else if((diffInSec/year)<1)
        {
            difference = (diffInSec / month)+monthAgo;
        }
        else
        {
//            // return date
//            Calendar c = Calendar.getInstance();
//            c.setTimeInMillis(fromDate);
//
//            SimpleDateFormat format_before = new SimpleDateFormat(
//                    "yyyy-MM-dd HH:mm:ss");
//
//            difference = format_before.format(c.getTime());
        }


        Log.e("time difference is: ","" + difference);
        return difference;
    }

    public static String DateDifference(long fromDate) {
        long diff = 0;
        long ms2 = System.currentTimeMillis();
        // get difference in milli seconds
        diff = ms2 - fromDate;

        int diffInSec = Math.abs((int) (diff / (second)));
        String difference = "";
        if(diffInSec < minute)
        {
            difference = diffInSec+secAgo;
        }
        else if((diffInSec / hour) < 1)
        {
            difference = (diffInSec/minute)+minAgo;
        }
        else if((diffInSec/ day) < 1)
        {
            difference = (diffInSec/hour)+hoursAgo;
        }
        else if((diffInSec/ week) < 1)
        {
            difference = (diffInSec/day)+daysAgo;
        }
        else if((diffInSec/month)<1)
        {
            difference = (diffInSec / week)+weekAgo;
        }
        else if((diffInSec/year)<1)
        {
            difference = (diffInSec / month)+monthAgo;
        }
        else
        {
//            // return date
//            Calendar c = Calendar.getInstance();
//            c.setTimeInMillis(fromDate);
//
//            SimpleDateFormat format_before = new SimpleDateFormat(
//                    "yyyy-MM-dd HH:mm:ss");
//
//            difference = format_before.format(c.getTime());
        }


        Log.e("time difference is: ","" + difference);
        return difference;
    }
}
