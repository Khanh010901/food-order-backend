package com.example.foodorder.service;

import com.example.foodorder.dto.ProfileDto;
import com.example.foodorder.mapper.ProfileMapper;
import com.example.foodorder.model.Profile;
import com.example.foodorder.model.User;
import com.example.foodorder.repository.ProfileRepository;
import com.example.foodorder.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private ProfileMapper profileMapper;

    public ProfileDto getProfileByUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Profile profile = profileRepository.findByUserId(user.getId());
        return profileMapper.toProfileDto(profile);
    }

    public ProfileDto updateProfile(ProfileDto profileDto, MultipartFile fileImage) throws IOException {

        if (profileDto == null)  throw new IllegalArgumentException("Profile data is required");
        User user = userRepository.findByUsername(profileDto.getUsername()).orElseThrow(() -> new RuntimeException("Coud not find user"));
        Profile profile = profileRepository.findByUserId(user.getId());
        if (profile == null) throw new EntityNotFoundException("Profile not found for user");

        if (fileImage != null && !fileImage.isEmpty()) {
            String image = cloudinaryService.upload(fileImage);
            profileDto.setImage(image);
        }

        Profile updatedProfile = profileMapper.toProfileEntity(profileDto);
        updatedProfile.setId(profile.getId());
        updatedProfile.setUser(user);
        profileRepository.save(updatedProfile);
        return profileMapper.toProfileDto(updatedProfile);
    }

}
