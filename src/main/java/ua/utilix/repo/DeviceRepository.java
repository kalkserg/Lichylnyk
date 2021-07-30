package ua.utilix.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.utilix.model.Device;

import java.util.List;


public interface DeviceRepository extends JpaRepository<Device, Long> {
    @Query("SELECT d FROM Device d WHERE d.notified = false " +
            "AND d.sigfoxId IS NOT NULL")
    List<Device> findNewDevices();

    Device findById(long id);

    Device[] findByChatId(long chatId);

    Device findByChatIdAndSigfoxId(long chatId, String sigfoxId);

    Device findByIdAndChatId(long Id, long chatId);

    Device[] findBySigfoxId(String sigfoxId);

    @Query("SELECT d FROM Device d WHERE d.notified = true AND d.allMessage = true " +
            "AND d.sigfoxId IS NOT NULL AND d.chatId = ?1")
    List<Device> findByChatIdAllMessageDevices(long chatId);

    @Query("SELECT d FROM Device d WHERE d.notified = true AND d.allMessage = false " +
            "AND d.sigfoxId IS NOT NULL AND d.chatId = ?1")
    List<Device> findByChatIdErrMessageDevices(long chatId);

}
