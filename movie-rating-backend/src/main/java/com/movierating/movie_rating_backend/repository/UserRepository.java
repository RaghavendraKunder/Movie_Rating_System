package com.movierating.movie_rating_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.movierating.movie_rating_backend.entity.Role;
import com.movierating.movie_rating_backend.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Authentication
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    // Admin
    List<User> findByRole(Role role);

    List<User> findByEnabledTrue();

    List<User> findByEnabledFalse();

    // Search
    List<User> findByFullNameContainingIgnoreCase(String fullName);

}