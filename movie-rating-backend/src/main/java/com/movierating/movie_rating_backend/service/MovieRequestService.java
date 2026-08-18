package com.movierating.movie_rating_backend.service;

import com.movierating.movie_rating_backend.dto.MovieRequestCreate;
import com.movierating.movie_rating_backend.entity.MovieRequestEntity;

public interface MovieRequestService {

    MovieRequestEntity createRequest(
            MovieRequestCreate request,
            String userEmail
    );

}