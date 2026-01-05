package service;

import org.junit.jupiter.api.Test;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

/**
 * BookingService sınıfının iş mantığını doğrulayan test sınıfı.
 * Rezervasyon yapma, kapasite kontrolü (Boundary Analysis) ve iptal işlemlerini test eder.
 */

class BookingServiceTest {

    @Test
    void testBookingFlow() {
        BookingService bookingService = new BookingService();

        //Rezervasyon Başarılı
        int userId = 1;
        int showTimeId = 1;
        int seatNumber = 5;

        bookingService.cancelBooking(userId, showTimeId);

        //Koltuk Rezerve Etme
        boolean isBooked = bookingService.bookSeat(userId, showTimeId, seatNumber);

        //İşlem Doğrulama (başarılı olmalı)
        assertTrue(isBooked, "Boş koltuk başarıyla rezerve edilebilmeli.");

        // Veritabanı Kontrolü
        Set<Integer> occupiedSeats = bookingService.getOccupiedSeats(showTimeId);
        assertTrue(occupiedSeats.contains(seatNumber), "Rezerve edilen koltuk 'Dolu Koltuklar' listesinde olmalı.");


        //Aynı Koltuğu Rezerve Etme
        boolean doubleBook = bookingService.bookSeat(userId, showTimeId, seatNumber);

        // İşlemi Doğrulama (başarısız olmalı)
        assertFalse(doubleBook, "Zaten dolu olan bir koltuk tekrar rezerve edilememeli!");

        bookingService.cancelBooking(userId, showTimeId);
    }

    @Test
    void testInvalidSeatBooking() {
        BookingService bookingService = new BookingService();
        int userId = 1;
        int showTimeId = 1;

        //Negatif Koltuk Numarası Kontrolü
        boolean negatifKoltuk = bookingService.bookSeat(userId, showTimeId, -5);
        assertFalse(negatifKoltuk, "Negatif numaralı koltuk rezerve edilememeli!");

        //Sıfır Numaralı Koltuk Kontrolü
        boolean sifirKoltuk = bookingService.bookSeat(userId, showTimeId, 0);
        assertFalse(sifirKoltuk, "0 numaralı koltuk rezerve edilememeli!");

        //OLmayan Koltuk Kontrolü
        try {
            boolean olmayanKoltuk = bookingService.bookSeat(userId, showTimeId, 9999);
            assertFalse(olmayanKoltuk, "Salonda olmayan koltuk (9999) rezerve edilememeli!");
        } catch (Exception e) {
            assertTrue(true, "Hatalı koltukta sistem exception fırlattı ama çökmedi.");
        }
    }
}