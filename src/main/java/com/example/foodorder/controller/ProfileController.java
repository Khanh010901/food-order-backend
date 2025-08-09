package com.example.foodorder.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.foodorder.dto.ProfileDto;
import com.example.foodorder.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    ProfileService profileService;
    private final ObjectMapper objectMapper;

    public ProfileController(ProfileService profileService, ObjectMapper objectMapper) {
        this.profileService = profileService;
        this.objectMapper = objectMapper;
    }
    @GetMapping
    public ResponseEntity<?> getProfileByUsername(@RequestParam("username") String username) {
        try {
            return ResponseEntity.ok(profileService.getProfileByUser(username));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateProfile(@RequestPart("profile") String profileJson,
                                           @RequestPart(value = "image", required = false) MultipartFile fileImage) {
        System.out.println("profileJson = " + profileJson);
        System.out.println("fileImage = " + (fileImage != null ? fileImage.getOriginalFilename() : "null"));
        try {
            // Parse JSON string thành DTO
            ProfileDto profileDto = objectMapper.readValue(profileJson, ProfileDto.class);

            // Gọi service update
            ProfileDto updatedProfile = profileService.updateProfile(profileDto, fileImage);

            return ResponseEntity.ok(updatedProfile);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Invalid JSON for profile: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
