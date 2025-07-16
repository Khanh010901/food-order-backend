package com.example.foodorder.mapper;

import com.example.foodorder.dto.CategoryDto;
import com.example.foodorder.dto.FoodDto;
import com.example.foodorder.model.Category;
import com.example.foodorder.model.Food;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface FoodMapper {
    FoodDto toDto(Food food);
    Food toEntity(FoodDto dto);

//    CategoryDto toCategoryDto(Category category);
//    Category toCategory(CategoryDto dto);
//
//    @AfterMapping
//    default void mapCategory(@MappingTarget FoodDto dto, Food entity) {
//        if (entity.getCategory() != null) {
//            dto.setCategory(toCategoryDto(entity.getCategory()));
//        }
//    }
}
