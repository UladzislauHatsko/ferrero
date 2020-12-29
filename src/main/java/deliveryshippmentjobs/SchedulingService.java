package deliveryshippmentjobs;

import deliveryshippmentjobs.model.TaskConfigurationRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ScheduledFuture;

import static java.util.Collections.singletonList;
import static org.springframework.util.CollectionUtils.isEmpty;

/**
 * @author uladzislau.hatsko
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SchedulingService implements SchedulingConfigurer {

    private static final String DEFAULT_CRON = "0 0 7,19 * * *";

    private final TaskScheduler scheduler;
    private final TaskConfigurationRepositoryCustom taskConfigurationRepository;
    private final Map<Integer, ScheduledFuture<?>> jobsMap;
    private final TaskExecutionService taskExecutionService;

    @EventListener({ContextRefreshedEvent.class})
    public void contextRefreshedEvent() {
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(scheduler);

        taskConfigurationRepository.findAll().forEach(
                it -> {
                    List<String> crons = isEmpty(it.getCron()) ? singletonList(DEFAULT_CRON) : it.getCron();
                    crons.forEach(cron -> scheduler
                            .schedule(() -> taskExecutionService.executeTask(it), new CronTrigger(cron, TimeZone.getTimeZone(TimeZone.getDefault().getID()))));
                });
    }

    public void removeTaskFromScheduler(int id) {
        ScheduledFuture<?> scheduledTask = jobsMap.get(id);
        if (scheduledTask != null) {
            scheduledTask.cancel(true);
            jobsMap.remove(id);
        }
    }
}
