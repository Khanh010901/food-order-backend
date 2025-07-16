package com.example.foodorder.service;

import com.example.foodorder.dto.CategoryDto;
import com.example.foodorder.mapper.CategoryMapper;
import com.example.foodorder.model.Category;
import com.example.foodorder.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private CloudinaryService cloudinaryService;

    public List<CategoryDto> findAllCategories(){
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }


    public CategoryDto createCategory(String name, MultipartFile image) throws Exception {

            Category category = new Category();
            category.setName(name);
            if(image != null && !image.isEmpty()) {
                category.setImage(cloudinaryService.upload(image));
            }

        return categoryMapper.toDto(categoryRepository.save(category));
    }


    public CategoryDto updateCategory(Long id, String name, MultipartFile image) throws Exception {
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isPresent()) {
            category.get().setName(name);
            if(image != null && !image.isEmpty()) {
                category.get().setImage(cloudinaryService.upload(image));
            }
        }

        return categoryMapper.toDto(categoryRepository.save(category.get()));
    }


    public CategoryDto getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
            return categoryMapper.toDto(category.get());
    }

}
