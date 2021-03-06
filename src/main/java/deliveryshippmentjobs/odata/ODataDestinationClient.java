package deliveryshippmentjobs.odata;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author uladzislau.hatsko
 */
@Service
@Slf4j
public class ODataDestinationClient extends HttpDestinationClient {

    @Value("${destination.read.prefix}")
    private String readPrefix;
    @Value("${destination.write.prefix}")
    private String writePrefix;
    @Value("${destination.name}")
    private String destinationName;

    @Override
    protected String destinationName() {
        return destinationName;
    }

    @Override
    protected String schemaReadPrefix() {
        return readPrefix;
    }

    @Override
    protected String schemaWritePrefix() {
        return writePrefix;
    }
}
