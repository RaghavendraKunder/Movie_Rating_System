package com.movierating.movie_rating_backend.controller;

import com.movierating.movie_rating_backend.dto.MovieRequest;
import com.movierating.movie_rating_backend.dto.MovieRequestAdminResponse;
import com.movierating.movie_rating_backend.dto.MovieResponse;
import com.movierating.movie_rating_backend.service.MovieRequestService;
import com.movierating.movie_rating_backend.service.MovieService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
@CrossOrigin(
	    origins = "http://localhost:4200",
	    allowCredentials = "true"
)
public class MovieController {
    private final MovieService movieService;
    private final MovieRequestService movieRequestService;

    /* Add a new movie
     Only ADMIN can add movies*/

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MovieResponse> addMovie(@Valid @RequestBody MovieRequest request){
        MovieResponse movie = movieService.addMovie(request);
        return new ResponseEntity<>(movie, HttpStatus.CREATED);
    }

    //Get all movies
    @GetMapping
    public ResponseEntity<List<MovieResponse>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    //Get movie by ID
    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> getMovieById(@PathVariable Long id){
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    /*Update movie
     * ADMIN only*/
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MovieResponse> updateMovie(@PathVariable Long id,@Valid @RequestBody MovieRequest request){
        return ResponseEntity.ok(
                movieService.updateMovie(id, request));
    }

    /*Delete movie
     * ADMIN only*/
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteMovie(@PathVariable Long id){
        movieService.deleteMovie(id);
        return ResponseEntity.ok("Movie deleted successfully.");
    }

    //Search movies
    @GetMapping("/search")
    public ResponseEntity<List<MovieResponse>> searchMovie(@RequestParam String keyword){
        return ResponseEntity.ok(movieService.searchMovie(keyword));
    }

    //Get movies by genre
    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<MovieResponse>> getMoviesByGenre(@PathVariable String genre){
        return ResponseEntity.ok(movieService.getMoviesByGenre(genre));
    }

    //Get Top Rated Movies
    @GetMapping("/top-rated")
    public ResponseEntity<List<MovieResponse>> topRatedMovies() {
        return ResponseEntity.ok(movieService.getTopRatedMovies());
    }

    //Get Latest Movies
    @GetMapping("/latest")
    public ResponseEntity<List<MovieResponse>> latestMovies() {
        return ResponseEntity.ok(movieService.getLatestMovies());
    }
    
    @PutMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MovieRequestAdminResponse> approveRequest(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                movieRequestService.approveRequest(id)
        );
    }
    
    @PutMapping("/{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MovieRequestAdminResponse> rejectRequest(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                movieRequestService.rejectRequest(id)
        );
    }

}