package com.example.foodorder.controller;

import com.example.foodorder.dto.FoodDto;
import com.example.foodorder.model.Food;
import com.example.foodorder.service.CloudinaryService;
import com.example.foodorder.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/foods")
public class FoodController {
    @Autowired
    private FoodService foodService;

    @GetMapping
    public ResponseEntity<List<FoodDto>> getAllFoods(){
        return ResponseEntity.ok(foodService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFoodById(@PathVariable Long id) {
        return ResponseEntity.ok(foodService.findById(id));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateFood(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("price") double price,
            @RequestParam("quantity") int quantity,
            @RequestParam Long categoryId,
            @RequestParam(value = "image", required = false) MultipartFile imageFile
    ) {
        try {

            foodService.Save(id,name,price,quantity,imageFile,categoryId,true);
            return ResponseEntity.ok("Cập nhật thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi cập nhật: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Food> deleteFood(@PathVariable Long id) {
        return ResponseEntity.ok(foodService.delete(id));
    }

    @PostMapping("/add")// la ham addFood
    public ResponseEntity<?> uploadFood(
            @RequestParam String name,
            @RequestParam double price,
            @RequestParam int quantity,
            @RequestParam Long categoryId,
            @RequestParam(name = "image") MultipartFile imageFile
    ) {
        try {

            foodService.Save(null,name,price,quantity,imageFile,categoryId,false); // gọi service để lưu vào DB

            return ResponseEntity.ok("Thêm món ăn thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi lưu ảnh: " + e.getMessage());
        }
    }


}
