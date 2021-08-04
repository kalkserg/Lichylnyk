package ua.utilix.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.utilix.model.User;
import ua.utilix.repo.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public User findByChatId(long chatId) {
        return userRepository.findByChatId(chatId);
    }

    @Transactional(readOnly = true)
    public User findById(long id) {
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void addUser(User user) {
        user.setAdmin(false);
        userRepository.save(user);
    }

    @Transactional
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void delUser(User user) {
        userRepository.delete(user);
    }
}

