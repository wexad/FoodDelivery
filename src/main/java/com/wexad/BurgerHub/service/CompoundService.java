package com.wexad.BurgerHub.service;

import com.wexad.BurgerHub.model.Compound;
import com.wexad.BurgerHub.repository.CompoundRepository;
import org.springframework.stereotype.Service;

import static com.wexad.BurgerHub.mapper.CompoundMapper.COMPOUND_MAPPER;

@Service
public class CompoundService {
    private final CompoundRepository compoundRepository;

    public CompoundService(CompoundRepository compoundRepository) {
        this.compoundRepository = compoundRepository;
    }

    public Compound save(Compound compoundDTO) {
        return compoundDTO;
    }
}
