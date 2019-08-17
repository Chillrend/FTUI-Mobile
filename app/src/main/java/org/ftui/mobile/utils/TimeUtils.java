package org.ftui.mobile.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {

    public static String convertEpochToLocalizedString(Long epoch){
        Date date = new Date(epoch*1000);

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMMM yyyy, HH:mm", Locale.getDefault());


        return sdf.format(date);
    }
}
