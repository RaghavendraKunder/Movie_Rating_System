package com.movierating.movie_rating_backend.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.movierating.movie_rating_backend.dto.MovieRequestCreate;
import com.movierating.movie_rating_backend.entity.Genre;
import com.movierating.movie_rating_backend.entity.MovieRequestEntity;
import com.movierating.movie_rating_backend.entity.RequestStatus;
import com.movierating.movie_rating_backend.entity.User;
import com.movierating.movie_rating_backend.exception.GenreNotFoundException;
import com.movierating.movie_rating_backend.repository.GenreRepository;
import com.movierating.movie_rating_backend.repository.MovieRequestRepository;
import com.movierating.movie_rating_backend.repository.UserRepository;
import com.movierating.movie_rating_backend.service.MovieRequestService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovieRequestServiceImpl implements MovieRequestService {

    private final MovieRequestRepository movieRequestRepository;

    private final GenreRepository genreRepository;

    private final UserRepository userRepository;

    @Override
    public MovieRequestEntity createRequest(
            MovieRequestCreate request,
            String userEmail) {

        // Find the logged-in user
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found with email: " + userEmail
                        ));

        // Convert genre IDs to Genre entities
        List<Genre> genres = request.getGenreIds()
                .stream()
                .map(id -> genreRepository.findById(id)
                        .orElseThrow(() ->
                                new GenreNotFoundException(
                                        "Genre not found with ID: " + id
                                )))
                .collect(Collectors.toList());

        // Create request entity
        MovieRequestEntity movieRequest = MovieRequestEntity.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .releaseDate(request.getReleaseDate())
                .duration(request.getDuration())
                .language(request.getLanguage())
                .director(request.getDirector())
                .cast(request.getCast())
                .posterUrl(request.getPosterUrl())
                .trailerUrl(request.getTrailerUrl())
                .genres(genres)
                .user(user)
                .status(RequestStatus.PENDING)
                .requestedAt(LocalDateTime.now())
                .build();

        return movieRequestRepository.save(movieRequest);
    }
}