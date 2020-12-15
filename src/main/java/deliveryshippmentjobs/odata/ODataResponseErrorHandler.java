package deliveryshippmentjobs.odata;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author Alena Khrapko
 */
@Slf4j
public class ODataResponseErrorHandler extends DefaultResponseErrorHandler {

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        log.error("Inside ODataResponseErrorHandler!!!");
        byte[] bytes = getResponseBody(response);
        String errorResponse = new String(bytes, StandardCharsets.UTF_8);
        log.error(errorResponse);
        log.error("Error status code = " + response.getStatusCode());
        super.handleError(response);
    }

}
