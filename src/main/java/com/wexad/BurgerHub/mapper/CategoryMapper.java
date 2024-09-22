package com.wexad.BurgerHub.mapper;

import com.wexad.BurgerHub.dto.CategoryDTO;
import com.wexad.BurgerHub.dto.CategoryDataDTO;
import com.wexad.BurgerHub.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CategoryMapper {
    CategoryMapper CATEGORY_MAPPER = Mappers.getMapper(CategoryMapper.class);

    Category toEntity(CategoryDTO categoryDTO);

    CategoryDTO toDTO(Category category);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "categoryName", target = "name")
    @Mapping(source = "image.path", target = "path")
    CategoryDataDTO toDataDTO(Category category);

    List<CategoryDataDTO> toDataDTOList(List<Category> categoryList);

    void updateEntityFromDTO(CategoryDTO categoryDTO, @MappingTarget Category category);
}
