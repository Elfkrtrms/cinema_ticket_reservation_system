package service;

import movie.Movie;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class MovieServiceTest {

    @Test
    void testGetAllMovies() {
        MovieService movieService = new MovieService();

        List<Movie> filmler = movieService.getAllMovies();

        // Liste BAğlantı Kontrolü
        assertFalse(filmler.isEmpty(), "Film listesi boş gelmemeli, veritabanı bağlantısını kontrol et.");

        // En az 1 Film Bağlantısı lazım

        assertTrue(filmler.size() > 0);

        // Film İsmi boş olmamalı
        assertNotNull(filmler.get(0).getTitle(), "Filmin adı null olamaz.");
    }
}