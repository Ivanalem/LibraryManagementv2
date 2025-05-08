package com.academy.LibraryManagementSystem.repository;

import com.academy.LibraryManagementSystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserRepository extends CrudRepository<User, Integer> {

    

    User findByEmail(String email);


    void deleteByEmail(final String email);
}
