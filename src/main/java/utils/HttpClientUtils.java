package utils;

/**
 * @author uladzislau.hatsko
 */
public final class HttpClientUtils {

    private HttpClientUtils() {
    }

    public static String replaceSpecialSymbols(String path) {
        return path.replaceAll(" ", "%20").replaceAll("'", "%27");
    }
}
