package utils;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * @author uladzislau.hatsko
 */
public final class DateUtils {

    private static final String DEFAULT_DATE_PATTERN = "yyyyMMdd";

    private DateUtils() {
    }

    public static Long extractMillisecsFromDateString(String dateString) {
        if (isEmpty(dateString) || dateString.equals("null")) {
            return 0L;
        }
        String date = dateString.replaceAll("/", "");
        date = date.replaceAll("Date\\(", "");
        date = date.replaceAll("\\\\", "");
        date = date.replaceAll("\\)", "");
        date = date.replaceAll("\\+0000", "");
        return Long.parseLong(date);
    }

    public static String wrapToFilterValue(Instant filterDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return "datetime'" + formatter.format(ZonedDateTime.ofInstant(filterDate, ZoneOffset.UTC)) + "'";
    }

    public static String wrapToPayloadValue(Instant date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        return formatter.format(ZonedDateTime.ofInstant(date, ZoneOffset.UTC));
    }
}
