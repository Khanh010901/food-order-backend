package com.example.foodorder.controller;

import com.example.foodorder.dto.CategoryDto;
import com.example.foodorder.mapper.CategoryMapper;
import com.example.foodorder.model.Category;
import com.example.foodorder.service.CategoryService;
import com.example.foodorder.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin("*")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
       return ResponseEntity.ok(categoryService.findAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<?> createCategory(@RequestParam String name, @RequestParam MultipartFile image) {
        try {

            return ResponseEntity.ok(categoryService.createCategory(name,image));


        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi cập nhật: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestParam String name, @RequestParam MultipartFile image) {
        try {
        return ResponseEntity.ok(categoryService.updateCategory(id,name,image));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi cập nhật: " + e.getMessage());

        }
    }
}
