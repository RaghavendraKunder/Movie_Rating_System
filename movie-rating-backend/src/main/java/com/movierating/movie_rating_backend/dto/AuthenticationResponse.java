package com.movierating.movie_rating_backend.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {

    private String token;

    private String tokenType;

    private Long userId;

    private String fullName;

    private String email;

    private String role;

}
