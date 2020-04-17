package com.rsypj.ftuimobile.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Author By dhadotid
 * Date 3/06/2018.
 */
public class TimeDiffLib {

    enum TimeStatus {
        STATUS_UN_UPDATED,
        STATUS_UPDATED
    }

    public static String getTimeComparator(String createdAt, String updatedAt) {
        TimeStatus timeStatus = TimeStatus.STATUS_UPDATED;
        long timeDiffMillis = 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Helper.DATE_FORMAT, Locale.US);

        try {
            Date createdAtDate = simpleDateFormat.parse(createdAt);
            Date updatedAtDate = simpleDateFormat.parse(updatedAt);

            if (createdAtDate.getTime() == updatedAtDate.getTime()) {
                timeStatus = TimeStatus.STATUS_UN_UPDATED;
            }

            if (timeStatus.equals(TimeStatus.STATUS_UPDATED)) {
                timeDiffMillis = updatedAtDate.getTime() - createdAtDate.getTime();
            } else {
                timeDiffMillis = new Date().getTime() - createdAtDate.getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getTimeDifference(timeDiffMillis);
    }

    private static String getTimeDifference(long timeDifference) {
        if (timeDifference < Helper.SEC_PER_MINUTE) {
            return timeDifference / Helper.SEC_PER_MINUTE + " menit lalu";
        } else if (timeDifference < Helper.SEC_PER_DAY) {
            return timeDifference / Helper.SEC_PER_HOUR + " jam lalu";
        } else if (timeDifference < (Helper.SEC_PER_DAY * Helper.TOTAL_DAYS_TO_MONTH)) {
            return timeDifference / Helper.SEC_PER_DAY + " hari lalu";
        } else {
            return timeDifference / (Helper.SEC_PER_DAY * Helper.TOTAL_DAYS_TO_MONTH) + " bulan lalu";
        }
    }
}
