package com.academy.LibraryManagementSystem.repository;

import com.academy.LibraryManagementSystem.model.User;
import org.apache.catalina.mapper.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.DoubleStream;


@Service
public interface UserRepository extends JpaRepository<User, Integer> {

    

    User findByEmail(String email);


    void deleteByEmail(final String email);

    Optional<User> findByUsername(String username);
}
