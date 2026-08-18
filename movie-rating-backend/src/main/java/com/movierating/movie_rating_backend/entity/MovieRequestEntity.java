package com.movierating.movie_rating_backend.entity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "movie_requests")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class MovieRequestEntity {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    // ==========================
	    // Movie Information
	    // ==========================

	    @Column(nullable = false, length = 200)
	    private String title;

	    @Column(nullable = false, length = 5000)
	    private String description;

	    @Column(nullable = false)
	    private LocalDate releaseDate;

	    @Column(nullable = false)
	    private Integer duration;

	    @Column(nullable = false, length = 50)
	    private String language;

	    @Column(nullable = false, length = 100)
	    private String director;

	    @Column(nullable = false, length = 500)
	    private String cast;

	    @Column(nullable = false, length = 255)
	    private String posterUrl;

	    @Column(length = 255)
	    private String trailerUrl;

	    // ==========================
	    // Request Information
	    // ==========================

	    @Enumerated(EnumType.STRING)
	    @Column(nullable = false, length = 20)
	    @Builder.Default
	    private RequestStatus status = RequestStatus.PENDING;

	    @Builder.Default
	    @Column(nullable = false)
	    private LocalDateTime requestedAt = LocalDateTime.now();

	    private LocalDateTime reviewedAt;

	    // ==========================
	    // User who submitted request
	    // ==========================

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "user_id", nullable = false)
	    private User user;

	    // ==========================
	    // Genres
	    // ==========================

	    @ManyToMany
	    @JoinTable(
	        name = "movie_request_genres",
	        joinColumns = @JoinColumn(name = "request_id"),
	        inverseJoinColumns = @JoinColumn(name = "genre_id")
	    )
	    @Builder.Default
	    private List<Genre> genres = new ArrayList<>();

	    // ==========================
	    // JPA Callback
	    // ==========================

	    @PrePersist
	    protected void onCreate() {
	        requestedAt = LocalDateTime.now();
	    }
	}
