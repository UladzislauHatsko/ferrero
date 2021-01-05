package utils;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author uladzislau.hatsko
 */
public final class FormatUtils {

    private static final int STATUS_LENGTH = 3;
    private static final String SEPARATOR = ",";

    private FormatUtils() {

    }

    public static String unifyStatusLength(String status) {
        if (status.length() < STATUS_LENGTH) {
            return StringUtils.leftPad(status, STATUS_LENGTH, '0');
        }
        return status;
    }

    public static List<String> splitStringListValue(String value) {
        return Stream.of(StringUtils.split(value, SEPARATOR)).map(StringUtils::trim).collect(Collectors.toList());
    }

    public static boolean areStatusEqual(String status1, String status2) {
        return StringUtils.equals(unifyStatusLength(status2), unifyStatusLength(status1));
    }

}
