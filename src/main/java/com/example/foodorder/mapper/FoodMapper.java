package com.example.foodorder.mapper;

import com.example.foodorder.dto.CategoryDto;
import com.example.foodorder.dto.FoodDto;
import com.example.foodorder.model.Category;
import com.example.foodorder.model.Food;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FoodMapper {

    FoodDto toDto(Food food);  // KHÔNG @Mapping

    Food toEntity(FoodDto dto);

    CategoryDto toCategoryDto(Category category);

    Category toCategory(CategoryDto dto);
}