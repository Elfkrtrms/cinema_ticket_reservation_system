package service;

import movie.ShowTime;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * ShowTimeService sınıfının metotlarını test eden sınıf.
 * Veritabanından seans bilgilerinin (tarih, saat) doğru çekilip çekilmediğini doğrular.
 */

class ShowTimeServiceTest {

    @Test
    void testGetShowtimesByMovie() {
        ShowTimeService showTimeService = new ShowTimeService();

        int movieId = 1;

        List<ShowTime> seanslar = showTimeService.getShowtimesByMovieId(movieId);

        assertNotNull(seanslar, "Seans listesi null dönmemeli.");

        if (!seanslar.isEmpty()) {
            for (ShowTime st : seanslar) {
                assertEquals(movieId, st.getMovie().getId(), "Gelen seans yanlış filme ait!");
            }
        }
    }
}