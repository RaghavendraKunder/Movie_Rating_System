package com.movierating.movie_rating_backend.service.impl;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movierating.movie_rating_backend.dto.MovieRequestAdminResponse;
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
import com.movierating.movie_rating_backend.entity.Movie;
import com.movierating.movie_rating_backend.exception.MovieAlreadyExistsException;
import com.movierating.movie_rating_backend.repository.MovieRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovieRequestServiceImpl implements MovieRequestService {

    private final MovieRequestRepository movieRequestRepository;
    private final GenreRepository genreRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

/* 
     =========================================================
     CREATE REQUEST - USER SIDE
     =========================================================
*/
    @Override
    public MovieRequestEntity createRequest(MovieRequestCreate request, String userEmail){
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));
        List<Genre> genres = request.getGenreIds()
                .stream()
                .map(id -> genreRepository.findById(id)
                        .orElseThrow(() -> new GenreNotFoundException("Genre not found with ID: " + id)))
                .collect(Collectors.toList());
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

/*
     =========================================================
     GET ALL REQUESTS - ADMIN
     =========================================================
*/
    @Override
    public List<MovieRequestAdminResponse> getAllRequests() {
        return movieRequestRepository
                .findAllByOrderByRequestedAtDesc()
                .stream()
                .map(this::convertToAdminResponse)
                .collect(Collectors.toList());
    }


/*
     =========================================================
     GET SINGLE REQUEST
     =========================================================
*/
    @Override
    public MovieRequestAdminResponse getRequestById(Long id) {
        MovieRequestEntity request =
                movieRequestRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Movie request not found with ID: " + id));
        return convertToAdminResponse(request);
    }


    // =========================================================
    // APPROVE REQUEST
    // =========================================================

    @Override
    @Transactional
    public MovieRequestAdminResponse approveRequest(Long id) {

        MovieRequestEntity request =
                movieRequestRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Movie request not found with ID: " + id
                                )
                        );

        // Prevent approving the same request twice
        if (request.getStatus() == RequestStatus.APPROVED) {
            throw new RuntimeException("This movie request is already approved.");
        }

        // Check if movie already exists
        if (movieRepository.existsByTitleIgnoreCase(request.getTitle())) {
            throw new RuntimeException(
                    "Movie already exists with title: " + request.getTitle()
            );
        }

        // Create a NEW Movie
        Movie movie = Movie.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .releaseDate(request.getReleaseDate())
                .duration(request.getDuration())
                .language(request.getLanguage())
                .posterUrl(request.getPosterUrl())
                .trailerUrl(request.getTrailerUrl())
                .director(request.getDirector())
                .cast(request.getCast())

                // IMPORTANT: create a NEW collection
                .genres(new ArrayList<>(request.getGenres()))

                .averageRating(0.0)
                .totalRatings(0L)
                .totalReviews(0L)
                .createdAt(LocalDateTime.now())
                .build();

        // Save movie to movies table
        movieRepository.save(movie);

        // Update request status
        request.setStatus(RequestStatus.APPROVED);
        request.setReviewedAt(LocalDateTime.now());

        MovieRequestEntity savedRequest =
                movieRequestRepository.save(request);

        return convertToAdminResponse(savedRequest);
    }
    // =========================================================
    // REJECT REQUEST
    // =========================================================

    @Override
    public MovieRequestAdminResponse rejectRequest(Long id) {
        MovieRequestEntity request = movieRequestRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Movie request not found with ID: " + id));
        request.setStatus(RequestStatus.REJECTED);
        request.setReviewedAt(LocalDateTime.now());
        MovieRequestEntity saved = movieRequestRepository.save(request);
        return convertToAdminResponse(saved);
    }


    // =========================================================
    // ENTITY → DTO
    // =========================================================

    private MovieRequestAdminResponse convertToAdminResponse(MovieRequestEntity request) {
        return MovieRequestAdminResponse.builder()
                .id(request.getId())
                .title(request.getTitle())
                .description(request.getDescription())
                .releaseDate(request.getReleaseDate())
                .duration(request.getDuration())
                .language(request.getLanguage())
                .director(request.getDirector())
                .cast(request.getCast())
                .posterUrl(request.getPosterUrl())
                .trailerUrl(request.getTrailerUrl())
                .genres(request.getGenres()
                                .stream()
                                .map(Genre::getName)
                                .collect(Collectors.toSet())
                )
                .status(request.getStatus().name())
                .requestedAt(request.getRequestedAt())
                .reviewedAt(request.getReviewedAt())
                .userId(request.getUser().getId())
                .requestedBy(request.getUser().getFullName())
                .userEmail(request.getUser().getEmail())
                .build();
    	}
	}