package deliveryshippmentjobs.odata;

import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
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

    protected abstract String destinationName();

    protected abstract String schemaReadPrefix();

    protected abstract String schemaWritePrefix();

    private String httpGet(String path) {
        HttpDestination httpDestination = getDestination(destinationName()).asHttp();
        HttpClient client = getHttpClient(httpDestination);

        try {
            String requestUrl = httpDestination.getUri() + schemaReadPrefix() + replaceSpecialSymbols(path);
            log.debug("GET: " + requestUrl);

            HttpResponse response = client.execute(new HttpGet(requestUrl));

            return IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("Connection was aborted", e);
        }
        return EMPTY;
    }

    public <T> int post(String path, T object) {
        try {
            String jsonBody = JsonUtils.to(object);
            HttpResponse httpResponse = httpPost(path, jsonBody);
            return httpResponse.getStatusLine().getStatusCode();
        } catch (IOException exception) {
            log.error("Post request processing failed with message: {}", exception.getMessage());
            return 500;
        }
    }

    private HttpResponse httpPost(String path, String jsonBody) throws IOException {
        HttpDestination httpDestination = getDestination(destinationName()).asHttp();
        HttpClient client = getHttpClient(httpDestination);

        String requestUrl = httpDestination.getUri() + schemaWritePrefix() + replaceSpecialSymbols(path);
        log.debug("POST: {} with body: {}", requestUrl, jsonBody);
        HttpPost httpPost = new HttpPost(requestUrl);

        httpPost.setEntity(new StringEntity(jsonBody));
        httpPost.setHeader("Content-type", "application/json");

        return client.execute(httpPost);
    }

}
