package com.example.foodorder.mapper;

import com.example.foodorder.dto.CategoryDto;
import com.example.foodorder.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto(Category category);
    Category toEntity(CategoryDto categoryDto);
}
