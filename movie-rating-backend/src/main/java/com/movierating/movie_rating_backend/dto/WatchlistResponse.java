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
public class WatchlistResponse {

    private Long watchlistId;

    private Long movieId;

    private String movieTitle;

    private String posterUrl;

    private String language;

    private List<String> genres;

    private String certificate;

    private Double averageRating;

    private Integer duration;

    private Integer releaseYear;

    private LocalDateTime addedAt;

}