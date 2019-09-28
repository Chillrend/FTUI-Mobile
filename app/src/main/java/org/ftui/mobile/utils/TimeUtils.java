package org.ftui.mobile.utils;

import org.ftui.mobile.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class TimeUtils {

    public static String convertEpochToLocalizedString(Long epoch){
        Date date = new Date(epoch*1000);

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMMM yyyy, HH:mm", Locale.getDefault());


        return sdf.format(date);
    }

    public static String convertDateStringToLocalizedString(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMMM yyyy, HH:mm", Locale.getDefault());

        try {
            Date formatedDate = sdf.parse(date);
            return formatedDate.toLocaleString();
        }catch (ParseException pe){
            pe.printStackTrace();
            return date;
        }
    }

    public static Map<Integer, Long> getDateDiffFromNow(Date date) {

        Date date2 = new Date();

        long diffInMillies = date2.getTime() - date.getTime();

        //create the list
        List<TimeUnit> units = new ArrayList<TimeUnit>(EnumSet.allOf(TimeUnit.class));
        Collections.reverse(units);

        //create the result map of TimeUnit and difference
        Map<TimeUnit,Long> result = new LinkedHashMap<TimeUnit,Long>();
        long milliesRest = diffInMillies;

        for ( TimeUnit unit : units ) {

            //calculate difference in millisecond
            long diff = unit.convert(milliesRest,TimeUnit.MILLISECONDS);
            long diffInMilliesForUnit = unit.toMillis(diff);
            milliesRest = milliesRest - diffInMilliesForUnit;

            //put the result in the map
            result.put(unit,diff);
        }

        Map<Integer, Long> diffs = new LinkedHashMap<>();

        if(result.get(TimeUnit.DAYS) > 0){
            diffs.put(R.string.days, result.get(TimeUnit.DAYS));
        }else if(result.get(TimeUnit.HOURS) > 0){
            diffs.put(R.string.hours, result.get(TimeUnit.HOURS));
        }else if(result.get(TimeUnit.MINUTES) > 0){
            diffs.put(R.string.minutes, result.get(TimeUnit.MINUTES));
        }else{
            diffs.put(R.string.seconds, result.get(TimeUnit.SECONDS));
        }

        return diffs;
    }
}
