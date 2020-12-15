package utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * @author uladzislau.hatsko
 */
public final class DateUtils {

    private static final String DEFAULT_DATE_PATTERN = "yyyyMMdd";

    private DateUtils() {
    }

    public static LocalDate toLocalDate(Date date) {
        return date != null ? Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.of("UTC")).toLocalDate() : null;
    }

    public static Instant toInstant(LocalDate localDate) {
        notNull(localDate, "Local date should not be null");

        return localDate.atStartOfDay().toInstant(ZoneOffset.UTC);
    }

    public static Date toDate(LocalDate localDate) {
        notNull(localDate, "Local date should not be null");

        return Date.from(localDate.atStartOfDay(ZoneId.of("UTC")).toInstant());
    }

    public static Date toDate(Instant instant) {
        notNull(instant, "Local date should not be null");

        return Date.from(instant);
    }

    public static boolean dateIsBetween(LocalDate from, LocalDate to, LocalDate date) {
        return from.compareTo(date) * date.compareTo(to) >= 0;
    }

    public static boolean dateIsBetween(Instant from, Instant to, Instant date) {
        return from.compareTo(date) * date.compareTo(to) >= 0;
    }

    public static Instant successFactorDateToInstant(String milliSeconds) {
        return Instant.ofEpochMilli(DateUtils.extractMillisecsFromDateString(milliSeconds));
    }

    public static String wrapToSuccessFactorDateFormat(Long milliSeconds) {
        return format("/Date(%s)/", milliSeconds);
    }

    public static String sfDateToStringIfNotEmpty(String dateString) {
        return isEmpty(dateString) ? dateString : successFactorsDateToString(dateString);
    }

    public static String successFactorsDateToString(String dateString) {
        return successFactorsDateToStringWithPattern(dateString, DEFAULT_DATE_PATTERN);
    }

    public static String successFactorsDateToStringWithPattern(String dateString, String datePattern) {
        Long milliSecondsDate = extractMillisecsFromDateString(dateString);
        Date date = new Date(milliSecondsDate);
        DateFormat dateFormat = new SimpleDateFormat(datePattern);
        return dateFormat.format(date);
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
}
