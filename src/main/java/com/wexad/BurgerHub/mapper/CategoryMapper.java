package com.wexad.BurgerHub.mapper;

import com.wexad.BurgerHub.dto.CategoryDTO;
import com.wexad.BurgerHub.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {
    CategoryMapper CATEGORY_MAPPER = Mappers.getMapper(CategoryMapper.class);

    Category toEntity(CategoryDTO categoryDTO);

    CategoryDTO toDTO(Category category);

    void updateEntityFromDTO(CategoryDTO categoryDTO, @MappingTarget Category category);
}
