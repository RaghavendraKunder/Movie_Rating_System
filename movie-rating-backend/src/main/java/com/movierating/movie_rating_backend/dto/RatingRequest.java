package com.movierating.movie_rating_backend.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingRequest {

    @NotNull(message = "Movie ID is required.")
    private Long movieId;

    @NotNull(message = "Rating is required.")
    @DecimalMin(value = "1.0", message = "Rating must be at least 1.")
    @DecimalMax(value = "10.0", message = "Rating cannot exceed 10.")
    private Double rating;

}