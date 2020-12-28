package utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

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

    public static <T> String to(T object) {
        ObjectWriter ow = MAPPER_WITHOUT_ROOT_NAME.writer().withDefaultPrettyPrinter();

        try {
            return ow.writeValueAsString(object);
        } catch (IOException e) {
            log.error("Cannot serialize object {}", object);
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

    public static <T> List<T> fromList(String jsonString, Class<T> clazz) {
        notBlank(jsonString);
        try {
            return MAPPER_WITHOUT_ROOT_NAME.readValue(jsonString, MAPPER_WITHOUT_ROOT_NAME.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (IOException e) {
            log.error("Cannot parse string '{}' to object list {}", jsonString, clazz);
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
