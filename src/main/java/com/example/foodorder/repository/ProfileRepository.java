package com.example.foodorder.repository;

import com.example.foodorder.model.Profile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile,Long> {

    public Profile findByUserId(Long userId);
    @Query(value ="SELECT ",nativeQuery = true)
    public Profile findByUserName(String userName);
}
