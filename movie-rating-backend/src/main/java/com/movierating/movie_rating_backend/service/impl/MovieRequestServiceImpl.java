package com.movierating.movie_rating_backend.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovieRequestServiceImpl implements MovieRequestService {

    private final MovieRequestRepository movieRequestRepository;
    private final GenreRepository genreRepository;
    private final UserRepository userRepository;


    // =========================================================
    // CREATE REQUEST - USER SIDE
    // =========================================================

    @Override
    public MovieRequestEntity createRequest(
            MovieRequestCreate request,
            String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found with email: " + userEmail
                        ));

        List<Genre> genres = request.getGenreIds()
                .stream()
                .map(id -> genreRepository.findById(id)
                        .orElseThrow(() ->
                                new GenreNotFoundException(
                                        "Genre not found with ID: " + id
                                )))
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


    // =========================================================
    // GET ALL REQUESTS - ADMIN
    // =========================================================

    @Override
    public List<MovieRequestAdminResponse> getAllRequests() {

        return movieRequestRepository
                .findAllByOrderByRequestedAtDesc()
                .stream()
                .map(this::convertToAdminResponse)
                .collect(Collectors.toList());
    }


    // =========================================================
    // GET SINGLE REQUEST
    // =========================================================

    @Override
    public MovieRequestAdminResponse getRequestById(Long id) {

        MovieRequestEntity request =
                movieRequestRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Movie request not found with ID: " + id
                                ));

        return convertToAdminResponse(request);
    }


    // =========================================================
    // APPROVE REQUEST
    // =========================================================

    @Override
    public MovieRequestAdminResponse approveRequest(Long id) {

        MovieRequestEntity request =
                movieRequestRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Movie request not found with ID: " + id
                                ));

        request.setStatus(RequestStatus.APPROVED);
        request.setReviewedAt(LocalDateTime.now());

        MovieRequestEntity saved =
                movieRequestRepository.save(request);

        return convertToAdminResponse(saved);
    }


    // =========================================================
    // REJECT REQUEST
    // =========================================================

    @Override
    public MovieRequestAdminResponse rejectRequest(Long id) {

        MovieRequestEntity request =
                movieRequestRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Movie request not found with ID: " + id
                                ));

        request.setStatus(RequestStatus.REJECTED);
        request.setReviewedAt(LocalDateTime.now());

        MovieRequestEntity saved =
                movieRequestRepository.save(request);

        return convertToAdminResponse(saved);
    }


    // =========================================================
    // ENTITY → DTO
    // =========================================================

    private MovieRequestAdminResponse convertToAdminResponse(
            MovieRequestEntity request) {

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

                .genres(
                        request.getGenres()
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