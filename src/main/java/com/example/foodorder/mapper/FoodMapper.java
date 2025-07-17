package com.example.foodorder.mapper;

import com.example.foodorder.dto.CategoryDto;
import com.example.foodorder.dto.FoodDto;
import com.example.foodorder.model.Category;
import com.example.foodorder.model.Food;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
public class FoodMapper {

    public FoodDto toDtoManual(Food food) {
        if (food == null) return null;

        Category category = food.getCategory();
        CategoryDto categoryDto = category != null ? new CategoryDto(
                category.getId(),
                category.getName(),
                category.isStatus(),
                category.getImage(),
                category.getCreatedAt(),
                category.getUpdatedAt(),
                category.getCreatedBy(),
                category.getUpdatedBy()
        ) : null;

        return new FoodDto(
                food.getId(),
                food.getName(),
                food.getPrice(),
                food.isStatus(),
                food.getCreatedBy(),
                food.getUpdatedBy(),
                food.getCreatedAt(),
                food.getUpdatedAt(),
                food.getImage(),
                food.getQuantity(),
                categoryDto
        );
    }
}