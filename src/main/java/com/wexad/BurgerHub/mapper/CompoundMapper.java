package com.wexad.BurgerHub.mapper;

import com.wexad.BurgerHub.dto.CompoundDTO;
import com.wexad.BurgerHub.model.Compound;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CompoundMapper {
    CompoundMapper COMPOUND_MAPPER = Mappers.getMapper(CompoundMapper.class);

    Compound toEntity(CompoundDTO compoundDTO);

    CompoundDTO toDTO(Compound compound);

    List<Compound> toEntity(List<CompoundDTO> compoundDTOList);

    List<CompoundDTO> toDTO(List<Compound> compoundList);
}
