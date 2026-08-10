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
public class UserResponse {

    private Long id;

    private String fullName;

    private String email;

    private String phone;

    private String profilePicture;

    private String role;

    private Boolean enabled;

    private Long totalRatings;

    private Long totalReviews;

    private Long watchlistCount;

    private LocalDateTime joinedAt;

}