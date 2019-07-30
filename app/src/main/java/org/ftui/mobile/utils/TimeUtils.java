package org.ftui.mobile.utils;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {

    public static String convertEpochToLocalizedString(Long epoch, int dateFormatStyle, Locale locale){
        Date date = new Date(epoch*1000);

        DateFormat df = DateFormat.getDateInstance(dateFormatStyle, locale);

        return df.format(date);
    }
}
