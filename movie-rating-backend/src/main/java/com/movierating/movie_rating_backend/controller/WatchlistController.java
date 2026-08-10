package com.movierating.movie_rating_backend.controller;


import com.movierating.movie_rating_backend.dto.WatchlistResponse;
import com.movierating.movie_rating_backend.service.WatchlistService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/watchlist")
@RequiredArgsConstructor
@CrossOrigin(
	    origins = "http://localhost:4200",
	    allowCredentials = "true"
	)
public class WatchlistController {

    private final WatchlistService watchlistService;

    /**
     * Add Movie to Watchlist
     */
    @PostMapping("/{movieId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<WatchlistResponse> addToWatchlist(
            @PathVariable Long movieId) {

        return new ResponseEntity<>(
                watchlistService.addToWatchlist(movieId),
                HttpStatus.CREATED);
    }

    /**
     * Remove Movie from Watchlist
     */
    @DeleteMapping("/{movieId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<String> removeFromWatchlist(
            @PathVariable Long movieId) {

        watchlistService.removeFromWatchlist(movieId);

        return ResponseEntity.ok(
                "Movie removed from watchlist.");
    }

    /**
     * Get Logged-in User Watchlist
     */
    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<WatchlistResponse>> getWatchlist() {

        return ResponseEntity.ok(
                watchlistService.getWatchlist());
    }

    /**
     * Check if Movie Exists in Watchlist
     */
    @GetMapping("/check/{movieId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Boolean> isMovieInWatchlist(
            @PathVariable Long movieId) {

        return ResponseEntity.ok(
                watchlistService.isMovieInWatchlist(movieId));
    }

    /**
     * Clear Entire Watchlist
     */
    @DeleteMapping("/clear")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<String> clearWatchlist() {

        watchlistService.clearWatchlist();

        return ResponseEntity.ok(
                "Watchlist cleared successfully.");
    }

}