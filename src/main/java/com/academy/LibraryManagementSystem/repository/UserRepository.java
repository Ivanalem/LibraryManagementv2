package com.academy.LibraryManagementSystem.repository;

import com.academy.LibraryManagementSystem.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;


@Service
public interface UserRepository extends CrudRepository<User, Integer> {

    

    User findByEmail(String email);


    void deleteByEmail(final String email);
}
