package ua.utilix.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.utilix.model.Water5;

public interface Water5Repository extends JpaRepository<Water5, Long> {

    @Query("SELECT w FROM Water5 w WHERE w.sigfoxId = ?1")
    Water5 findParameters(String sigfoxId);
}
