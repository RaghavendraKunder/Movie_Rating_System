package com.movierating.movie_rating_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {

    @NotNull(message = "Movie ID is required.")
    private Long movieId;

    @NotBlank(message = "Review title is required.")
    @Size(max = 100,
            message = "Review title cannot exceed 100 characters.")
    private String title;

    @NotBlank(message = "Review cannot be empty.")
    @Size(min = 20, max = 3000,
            message = "Review must be between 20 and 3000 characters.")
    private String review;

    @Builder.Default
    private Boolean containsSpoiler = false;

}