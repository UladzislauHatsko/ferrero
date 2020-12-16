package deliveryshippmentjobs;

import deliveryshippmentjobs.model.TaskConfigurationRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ScheduledFuture;

/**
 * @author uladzislau.hatsko
 */
@Service
@AllArgsConstructor
public class SchedulingService implements SchedulingConfigurer {

    private final TaskScheduler scheduler;
    private final TaskConfigurationRepository taskConfigurationRepository;
    private final Map<Integer, ScheduledFuture<?>> jobsMap;
    private final TaskExecutionService taskExecutionService;

    @EventListener({ContextRefreshedEvent.class})
    public void contextRefreshedEvent() {
    }

    //    @PostConstruct
    //    public void initDatabase() {
    //        taskConfigurationRepository.save(new TaskConfiguration(1, DELIVERY, "*/10 * * * * *", "ZC01, ZNL2, ZI01, ZC05, ZC14", 5, DELIVERY_DATE, "FRPA,FRPB,FRDB,FRDC,FRDI,FRDG,FRDL,FRDM,FRDF,FRDJ,FRDO, PLBB", "10, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 20"));
    //        taskConfigurationRepository.save(new TaskConfiguration(2, DELIVERY, "*/20 * * * * *", "ZC01", 28, DELIVERY_DATE, "PLDD, PLDB, PLDG, PLDA", "10, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 20"));
    //        taskConfigurationRepository.save(new TaskConfiguration(3, DELIVERY, "*/5 * * * * *"));
    //    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(scheduler);

        taskConfigurationRepository.findAll().forEach(
                it -> scheduler.schedule(() -> taskExecutionService.executeTask(it.getJobId()),
                        new CronTrigger(it.getCron(), TimeZone.getTimeZone(TimeZone.getDefault().getID()))));
    }

    public void removeTaskFromScheduler(int id) {
        ScheduledFuture<?> scheduledTask = jobsMap.get(id);
        if (scheduledTask != null) {
            scheduledTask.cancel(true);
            jobsMap.put(id, null);
        }
    }
}
