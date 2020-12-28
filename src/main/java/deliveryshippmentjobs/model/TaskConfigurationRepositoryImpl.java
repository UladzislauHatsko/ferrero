package deliveryshippmentjobs.model;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import utils.JsonUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author uladzislau.hatsko
 */
@Profile("!database")
@Component
@Slf4j
public class TaskConfigurationRepositoryImpl implements TaskConfigurationRepositoryCustom {

    @Value("classpath:jobs-configuration.json")
    private Resource tasks;

    @Override
    public List<TaskConfiguration> findAll() {
        try {
            InputStream inputStream = tasks.getInputStream();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String employees = reader.lines().collect(Collectors.joining("\n"));
                log.info(employees);
                List<TaskConfiguration> taskConfigList = JsonUtils.fromList(employees, TaskConfiguration.class);
                log.info("extracted taskConfig {}", taskConfigList);
                return taskConfigList;
            }
        } catch (IOException ioException) {
            log.error("Can't read job configuration : {}", ioException.getMessage());
            return Collections.emptyList();
        }
    }
}
