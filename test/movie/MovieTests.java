package movie;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MovieTests {

    //2D Filmlerde Fiyat Kontrolü

    @Test
    void testMovie2DPrice() {
        Movie2D film = new Movie2D(1, "Avatar 2D", 120);

        double beklenenFiyat = 120.0;

        assertEquals(beklenenFiyat, film.getPrice(), "2D film fiyatı yanlış getiriliyor!");
    }

    //3D Filmlerde Fiyat Kontrolü

    @Test
    void testMovie3DPrice() {
        Movie3D film = new Movie3D(2, "Titanic 3D", 150);

        double beklenenFiyat = 180.0;

        assertEquals(beklenenFiyat, film.getPrice(), "3D film fiyatı yanlış getiriliyor!");
    }
}