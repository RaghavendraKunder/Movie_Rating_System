package com.movierating.movie_rating_backend.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingResponse {

    private Long ratingId;

    private Long movieId;

    private String movieTitle;

    private String posterUrl;

    private Integer releaseYear;

    private List<String> genres;

    private Long userId;

    private String userName;

    private Double rating;

    private Double averageRating;

    private Long totalRatings;

    private LocalDateTime ratedAt;

}