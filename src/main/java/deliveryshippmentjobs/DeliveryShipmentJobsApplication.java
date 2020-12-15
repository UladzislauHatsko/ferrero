package deliveryshippmentjobs;

import com.fasterxml.jackson.databind.DeserializationFeature;
import deliveryshippmentjobs.odata.ODataResponseErrorHandler;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.client.RestTemplate;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
@EnableScheduling
public class DeliveryShipmentJobsApplication {

    @Bean
    public TaskScheduler poolScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
        scheduler.setPoolSize(10);
        scheduler.initialize();
        return scheduler;
    }

    @Bean
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new ODataResponseErrorHandler());
        restTemplate.getMessageConverters().stream()
                .filter(httpMessageConverter -> httpMessageConverter instanceof MappingJackson2HttpMessageConverter)
                .findFirst()
                .ifPresent(converter -> {
                    ((MappingJackson2HttpMessageConverter) converter).getObjectMapper().configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
                    ((MappingJackson2HttpMessageConverter) converter).getObjectMapper().configure(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS, true);
                });
        return restTemplate;
    }

    public static void main(String[] args) {
        run(DeliveryShipmentJobsApplication.class, args);

    }

}
