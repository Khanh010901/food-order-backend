package com.example.foodorder.service;

import com.example.foodorder.dto.FoodDto;
import com.example.foodorder.mapper.FoodMapper;
import com.example.foodorder.model.Category;
import com.example.foodorder.model.Food;
import com.example.foodorder.repository.CategoryRepository;
import com.example.foodorder.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodService {
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private FoodMapper foodMapper;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CloudinaryService cloudinaryService;

    public List<FoodDto> findAll() {
        List<FoodDto> foodDtos = foodRepository.findAll().stream()
                .map(foodMapper::toDtoManual)
                .collect(Collectors.toList());
        return foodDtos;
    }

    public FoodDto findById(long id) {
        return foodMapper.toDtoManual(foodRepository.findById(id).orElseThrow(() -> new RuntimeException("Could not find food with id " + id)));
    }

    public Food findByName(String name) {
        return null;
    }

    public FoodDto Save(Long id, String name, double price, int quantity,MultipartFile imageFile, Long categoryId, boolean isUpdate) throws IOException {

        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new RuntimeException("Could not find category"));
        Food food;
        if(isUpdate) {
            food = foodRepository.findById(id).orElseThrow(()-> new RuntimeException("Could not find food"));
            food.setUpdatedAt(LocalDateTime.now());
            food.setUpdatedBy("Giang");
        }else{
            food = new Food();
            food.setCreatedAt(LocalDateTime.now());
            food.setCreatedBy("Khanh");
            food.setStatus(true);
        }

        if (imageFile != null && !imageFile.isEmpty()) {
            String image = cloudinaryService.upload(imageFile);
            food.setImage(image);
        }

        food.setName(name);
        food.setPrice(price);
        food.setQuantity(quantity);
        food.setCategory(category);

        return foodMapper.toDtoManual(foodRepository.save(food));
    }



    public Food delete(Long id) {
        Optional<Food> foodOptional = foodRepository.findById(id);
        if (foodOptional.isPresent()) {
            Food food = foodOptional.get();
            foodRepository.delete(food);
            return food;
        } else {
            throw new RuntimeException("Không tìm thấy món ăn có id: " + id);
        }
    }

}
