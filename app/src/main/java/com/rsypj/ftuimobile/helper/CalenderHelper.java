package com.rsypj.ftuimobile.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by dhadotid on 10/04/2018.
 */

public class CalenderHelper {

    public static long getCalender(){
        Calendar calendar = Calendar.getInstance(Locale.US);
        calendar.add(Calendar.HOUR_OF_DAY, 0);

        long times = calendar.getTime().getTime();
        return times;
    }

    public static String convertLongToTime(long time){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat namedayParser = new SimpleDateFormat("EEEE");
        SimpleDateFormat dayParser = new SimpleDateFormat("dd");
        SimpleDateFormat monthParser = new SimpleDateFormat("MM");
        SimpleDateFormat yearParser = new SimpleDateFormat("yyyy");
        SimpleDateFormat hourParser = new SimpleDateFormat("HH");
        SimpleDateFormat minuteParser = new SimpleDateFormat("mm");
        String namedayServer = namedayParser.format(time);
        long dayServer = Integer.parseInt(dayParser.format(time));
        long monthServer = Integer.parseInt(monthParser.format(time));
        long yearServer = Integer.parseInt(yearParser.format(time));
        long jamServer = Integer.parseInt(hourParser.format(time));
        long minuteServer = Integer.parseInt(minuteParser.format(time));
        return namedayServer + ", " +  dayServer + "-" + monthServer + "-" + yearServer + "\n" + jamServer + ":" + minuteServer;
    }
}
