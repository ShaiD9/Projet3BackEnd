package com.openclassroom.apiRentalApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.openclassroom.apiRentalApp.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String Name);
}