package com.wexad.BurgerHub.service;

import com.wexad.BurgerHub.repository.CompoundRepository;
import org.springframework.stereotype.Service;

@Service
public class CompoundService {
    private final CompoundRepository compoundRepository;

    public CompoundService(CompoundRepository compoundRepository) {
        this.compoundRepository = compoundRepository;
    }
}
