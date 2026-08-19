package com.movierating.movie_rating_backend.service;

import java.util.List;

import com.movierating.movie_rating_backend.dto.MovieRequestAdminResponse;
import com.movierating.movie_rating_backend.dto.MovieRequestCreate;
import com.movierating.movie_rating_backend.entity.MovieRequestEntity;

public interface MovieRequestService {

    MovieRequestEntity createRequest(
            MovieRequestCreate request,
            String userEmail
    );

    List<MovieRequestAdminResponse> getAllRequests();

    MovieRequestAdminResponse getRequestById(Long id);

    MovieRequestAdminResponse approveRequest(Long id);

    MovieRequestAdminResponse rejectRequest(Long id);
}