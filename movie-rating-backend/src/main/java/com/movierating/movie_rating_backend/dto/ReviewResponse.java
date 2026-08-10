package com.movierating.movie_rating_backend.dto;


import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {

    private Long reviewId;

    private Long movieId;

    private String movieTitle;

    private Long userId;

    private String userName;

    private String title;

    private String review;

    private Boolean containsSpoiler;

    private Long likes;

    private Long dislikes;

    private Double userRating;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Boolean editable;

}