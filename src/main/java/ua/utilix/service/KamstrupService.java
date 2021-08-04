package ua.utilix.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.utilix.model.Kamstrup;
import ua.utilix.repo.KamstrupRepository;

@Service
public class KamstrupService {

    private final KamstrupRepository kamstrupRepository;

    public KamstrupService(KamstrupRepository kamstrupRepository) {
        this.kamstrupRepository = kamstrupRepository;
    }

    @Transactional
    public Kamstrup findDecodeKey(String sigfoxId) {
        return kamstrupRepository.findDecodeKey(sigfoxId);
    }
}

