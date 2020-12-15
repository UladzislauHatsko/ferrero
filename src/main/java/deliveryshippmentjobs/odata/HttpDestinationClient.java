package deliveryshippmentjobs.odata;

import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.springframework.beans.factory.annotation.Value;
import utils.JsonUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.sap.cloud.sdk.cloudplatform.connectivity.DestinationAccessor.getDestination;
import static com.sap.cloud.sdk.cloudplatform.connectivity.HttpClientAccessor.getHttpClient;
import static org.apache.logging.log4j.util.Strings.EMPTY;
import static utils.HttpClientUtils.replaceSpecialSymbols;

/**
 * @author uladzislau.hatsko
 */
@Slf4j
public abstract class HttpDestinationClient {

    public <T> T get(String path, Class<T> clazz) {
        String response = httpGet(path);

        return JsonUtils.from(response, clazz);
    }

    public String destinationUri() {
        HttpDestination httpDestination = getDestination(destinationName().name()).asHttp();
        return httpDestination.getUri().toString();
    }

    protected abstract DestinationAlias destinationName();

    protected abstract String schemaPrefix();

    private String httpGet(String path) {
        HttpDestination httpDestination = getDestination(destinationName().name()).asHttp();
        HttpClient client = getHttpClient(httpDestination);

        try {
            String requestUrl = httpDestination.getUri() + schemaPrefix() + replaceSpecialSymbols(path);
            log.info("GET: " + requestUrl);

            HttpResponse response = client.execute(new HttpGet(requestUrl));

            return IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("Connection was aborted", e);
        }

        return EMPTY;
    }

}
