package deliveryshippmentjobs.model;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author uladzislau.hatsko
 */
@Profile("database")
@Repository
public interface TaskConfigurationRepository extends JpaRepository<TaskConfiguration, Long>, TaskConfigurationRepositoryCustom {
}
