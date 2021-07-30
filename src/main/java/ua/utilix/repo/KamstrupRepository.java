package ua.utilix.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.utilix.model.Kamstrup;

public interface KamstrupRepository extends JpaRepository<Kamstrup, Long> {

    @Query("SELECT k FROM Kamstrup k WHERE k.sigfoxId = ?1")
    Kamstrup findDecodeKey(String sigfoxId);
}
