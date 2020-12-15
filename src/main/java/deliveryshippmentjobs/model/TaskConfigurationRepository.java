package deliveryshippmentjobs.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author uladzislau.hatsko
 */
@Repository
public interface TaskConfigurationRepository extends JpaRepository<TaskConfiguration, Long> {
}
