package com.example.foodorder.mapper;

import com.example.foodorder.dto.CategoryDto;
import com.example.foodorder.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDto toDto(Category category) {
        if (category == null) {
            return null;
        }
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setStatus(category.isStatus());
        dto.setImage(category.getImage());
        dto.setCreatedAt(category.getCreatedAt());
        dto.setUpdatedAt(category.getUpdatedAt());
        dto.setCreatedBy(category.getCreatedBy());
        dto.setUpdatedBy(category.getUpdatedBy());
        return dto;
    }

    public Category toEntity(CategoryDto dto) {
        if (dto == null) {
            return null;
        }
        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        category.setStatus(dto.isStatus());
        category.setImage(dto.getImage());
        category.setCreatedAt(dto.getCreatedAt());
        category.setUpdatedAt(dto.getUpdatedAt());
        category.setCreatedBy(dto.getCreatedBy());
        category.setUpdatedBy(dto.getUpdatedBy());
        return category;
    }
}