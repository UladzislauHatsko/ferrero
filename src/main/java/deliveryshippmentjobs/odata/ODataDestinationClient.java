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

    @Value("${destination.prefix}")
    private String prefix;

    @Override
    protected DestinationAlias destinationName() {
        return DestinationAlias.GTTDeliveryProcessFerreroV2;
    }

    @Override
    protected String schemaPrefix() {
        return prefix;
    }
}
