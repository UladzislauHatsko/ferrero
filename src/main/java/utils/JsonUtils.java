package utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static java.lang.String.format;
import static org.apache.commons.lang3.Validate.notBlank;

/**
 * @author uladzislau.hatsko
 */
@Slf4j
public final class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final ObjectMapper MAPPER_WITHOUT_ROOT_NAME = new ObjectMapper();

    static {
        MAPPER.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
    }

    private JsonUtils() {
    }

    public static <T> String toJson(T object) {
        ObjectWriter ow = MAPPER_WITHOUT_ROOT_NAME.writer().withDefaultPrettyPrinter();

        try {
            return ow.writeValueAsString(object);
        } catch (IOException e) {
            log.error("Cannot serialize object {}", object);
            log.error("Error:", e);
            throw new RuntimeException(format("Cannot serialize object %s.", object), e);
        }
    }

    public static <T> T from(String jsonString, Class<T> clazz) {
        notBlank(jsonString);
        try {
            return MAPPER.readValue(jsonString, clazz);
        } catch (IOException e) {
            log.error("Cannot parse string '{}' to object {}", jsonString, clazz, e);
        }
        return null;
    }

    public static <T> T fromWithoutRootName(String jsonString, Class<T> clazz) {
        notBlank(jsonString);

        try {
            return MAPPER_WITHOUT_ROOT_NAME.readValue(jsonString, clazz);
        } catch (IOException e) {
            log.error("Cannot parse string '{}' to object {}", jsonString, clazz, e);
        }
        return null;
    }
}
