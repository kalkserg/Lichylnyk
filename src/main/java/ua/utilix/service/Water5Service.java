package ua.utilix.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.utilix.model.Kamstrup;
import ua.utilix.model.Water5;
import ua.utilix.repo.KamstrupRepository;
import ua.utilix.repo.Water5Repository;

@Service
public class Water5Service {

    private final Water5Repository water5Repository;

    public Water5Service(Water5Repository water5Repository) {
        this.water5Repository = water5Repository;
    }

    @Transactional
    public Water5 findParameters(String sigfoxId) {
        return water5Repository.findParameters(sigfoxId);
    }
}

