package com.example.foodorder.dto;

import com.example.foodorder.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ProfileDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String image;
    private LocalDateTime dob;
    private String phone;
    private String address;
    private String username;

}
