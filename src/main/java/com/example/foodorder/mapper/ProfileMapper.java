package com.example.foodorder.mapper;

import com.example.foodorder.dto.ProfileDto;
import com.example.foodorder.model.Profile;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {

    public ProfileDto toProfileDto(Profile profile) {
        if (profile == null) return null;

        ProfileDto profileDto = new ProfileDto();
        profileDto.setId(profile.getId());
        profileDto.setAddress(profile.getAddress());
        profileDto.setDob(profile.getDob());
        profileDto.setPhone(profile.getPhone());
        profileDto.setImage(profile.getImage());
        profileDto.setFirstName(profile.getFirstName());
        profileDto.setLastName(profile.getLastName());
        String fName = (profile.getFirstName()!= null)?profile.getFirstName() : "Khanh";
        String lName = (profile.getLastName()!= null)?profile.getLastName() : "DZ";
        profileDto.setUsername(lName + " " + fName);

        return profileDto;
    }

    public Profile toProfileEntity(ProfileDto dto) {
        if (dto == null) return null;

        Profile profile = new Profile();
        profile.setId(dto.getId());
        profile.setAddress(dto.getAddress());
        profile.setDob(dto.getDob());
        profile.setPhone(dto.getPhone());
        profile.setImage(dto.getImage());
        profile.setFirstName(dto.getFirstName());
        profile.setLastName(dto.getLastName());

        return profile;
    }
}
