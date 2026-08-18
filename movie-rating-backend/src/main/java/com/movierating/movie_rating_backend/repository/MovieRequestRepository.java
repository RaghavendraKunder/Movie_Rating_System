package com.movierating.movie_rating_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.movierating.movie_rating_backend.entity.MovieRequestEntity;
import com.movierating.movie_rating_backend.entity.RequestStatus;

@Repository
public interface MovieRequestRepository
        extends JpaRepository<MovieRequestEntity, Long> {

    List<MovieRequestEntity> findByStatus(RequestStatus status);

    List<MovieRequestEntity> findByUserId(Long userId);

}