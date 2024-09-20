package com.wexad.BurgerHub.service;

import com.wexad.BurgerHub.dto.CategoryDTO;
import com.wexad.BurgerHub.model.Category;
import com.wexad.BurgerHub.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import static com.wexad.BurgerHub.mapper.CategoryMapper.CATEGORY_MAPPER;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;


    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void saveCategory(CategoryDTO categoryDTO) {
        Category category = CATEGORY_MAPPER.toEntity(categoryDTO);
        categoryRepository.save(category);
    }

    public void updateCategory(Long id, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        CATEGORY_MAPPER.updateEntityFromDTO(categoryDTO, category);
        categoryRepository.save(category);
    }

    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        return CATEGORY_MAPPER.toDTO(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.updateDeletedBy(id);
    }
}

