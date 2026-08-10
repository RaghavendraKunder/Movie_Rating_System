package com.movierating.movie_rating_backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movierating.movie_rating_backend.dto.GenreRequest;
import com.movierating.movie_rating_backend.dto.GenreResponse;
import com.movierating.movie_rating_backend.entity.Genre;
import com.movierating.movie_rating_backend.repository.GenreRepository;
import com.movierating.movie_rating_backend.service.GenreService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;


@Override
public GenreResponse addGenre(GenreRequest request) {

    if (genreRepository.existsByNameIgnoreCase(request.getName())) {
        throw new RuntimeException("Genre already exists.");
    }

    Genre genre = Genre.builder()
            .name(request.getName())
            .build();

    Genre savedGenre = genreRepository.save(genre);

    return GenreResponse.builder()
            .id(savedGenre.getId())
            .name(savedGenre.getName())
            .totalMovies((long) savedGenre.getMovies().size())
            .build();
    }
@Override
@Transactional
public GenreResponse updateGenre(Long id, GenreRequest request) {

    Genre genre = genreRepository.findById(id)
            .orElseThrow(() ->
                    new RuntimeException("Genre not found"));

    if (!genre.getName().equalsIgnoreCase(request.getName())
            && genreRepository.existsByNameIgnoreCase(request.getName())) {
        throw new RuntimeException("Genre already exists");
    }

    genre.setName(request.getName());

    Genre savedGenre = genreRepository.save(genre);

    return GenreResponse.builder()
            .id(savedGenre.getId())
            .name(savedGenre.getName())
            .totalMovies((long) savedGenre.getMovies().size())
            .build();
}
@Override
public void deleteGenre(Long id) {

    Genre genre = genreRepository.findById(id)
            .orElseThrow(() ->
                    new EntityNotFoundException("Genre not found with ID: " + id));

    genreRepository.delete(genre);

}
@Override
@Transactional(readOnly = true)
public GenreResponse getGenreById(Long id) {

    Genre genre = genreRepository.findById(id)
            .orElseThrow(() ->
                    new RuntimeException("Genre not found"));

    return GenreResponse.builder()
            .id(genre.getId())
            .name(genre.getName())
            .totalMovies((long) genre.getMovies().size())
            .build();
}
@Override
@Transactional(readOnly = true)
public List<GenreResponse> getAllGenres() {

    return genreRepository.findAllByOrderByNameAsc()
            .stream()
            .map(genre -> GenreResponse.builder()
                    .id(genre.getId())
                    .name(genre.getName())
                    .totalMovies((long) genre.getMovies().size())
                    .build())
            .collect(Collectors.toList());
}
@Override
@Transactional(readOnly = true)
public List<GenreResponse> searchGenres(String keyword) {

    return genreRepository.findByNameContainingIgnoreCase(keyword)
            .stream()
            .map(genre -> GenreResponse.builder()
                    .id(genre.getId())
                    .name(genre.getName())
                    .totalMovies((long) genre.getMovies().size())
                    .build())
            .collect(Collectors.toList());
}
}