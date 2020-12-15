package utils.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Date;

import static utils.DateUtils.extractMillisecsFromDateString;

/**
 * @author uladzislau.hatsko
 */
public class JsonDateDeserializer extends JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return new Date(extractMillisecsFromDateString(jsonParser.getText().trim()));
    }
}
