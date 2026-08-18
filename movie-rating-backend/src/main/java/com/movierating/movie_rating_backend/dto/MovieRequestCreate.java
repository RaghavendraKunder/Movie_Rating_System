package com.movierating.movie_rating_backend.dto;

import java.time.LocalDate;
import java.util.Set;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
public class MovieRequestCreate {

    @NotBlank(message = "Movie title is required.")
    @Size(max = 150, message = "Movie title cannot exceed 150 characters.")
    private String title;

    @NotBlank(message = "Description is required.")
    @Size(max = 3000, message = "Description cannot exceed 3000 characters.")
    private String description;

    @NotNull(message = "Release date is required.")
    private LocalDate releaseDate;

    @NotNull(message = "Duration is required.")
    @Min(value = 1, message = "Duration must be at least 1 minute.")
    @Max(value = 500, message = "Duration cannot exceed 500 minutes.")
    private Integer duration;

    @NotBlank(message = "Language is required.")
    private String language;

    @NotEmpty(message = "Select at least one genre.")
    private Set<Long> genreIds;

    @NotBlank(message = "Director name is required.")
    private String director;

    @NotBlank(message = "Cast is required.")
    private String cast;

    @NotBlank(message = "Poster URL is required.")
    private String posterUrl;

    private String trailerUrl;
}