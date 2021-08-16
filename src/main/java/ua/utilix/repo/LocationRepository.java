package ua.utilix.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.utilix.model.Device;
import ua.utilix.model.Location;


public interface LocationRepository extends JpaRepository<Location, Long> {

    Location findBySigfoxId(String sigfoxId);

}
