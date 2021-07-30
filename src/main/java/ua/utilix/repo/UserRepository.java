package ua.utilix.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.utilix.model.User;


public interface UserRepository extends JpaRepository<User, Long> {

    User findById(long id);

    User findByChatId(long chatId);

}
