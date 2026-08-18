package com.movierating.movie_rating_backend.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieRequestResponse {

    private Long id;

    private String title;

    private String description;

    private LocalDate releaseDate;

    private Integer duration;

    private String language;

    private String director;

    private String cast;

    private String posterUrl;

    private String trailerUrl;

    private Set<String> genres;

    private String status;

    private LocalDateTime requestedAt;

}