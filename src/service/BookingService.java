package service;

import database.DataBase;
import java.sql.*;
import java.util.*;
import movie.*;

/**
 * Rezervasyon işlemlerini yöneten servis sınıfı.
 * Koltuğun dolu olup olmadığını kontrol eder ve veritabanına kaydeder.
 */

public class BookingService {

    /**
     * Koltuğun rezerve edilmeye uygun olup olmadığını kontrol eden metot.
     */

    public boolean bookSeat(int userId, int showtimeId, int seatNumber) {

        // Koltuk numarası 0 veya olamaz

        if (seatNumber <= 0 || seatNumber > 30) {
            System.out.println("❌ Hata: Geçersiz koltuk numarası! (1-30 arası olmalı)");
            return false;
        }

        //Geçmiş Seans Kontrolü

        if (isShowtimeInPast(showtimeId)) {
            System.out.println("❌ Geçmiş bir seans için rezervasyon yapılamaz!");
            return false;
        }

        String checkSql = """
            SELECT 1 FROM bookings
            WHERE showtime_id = ? AND seat_number = ?
        """;

        String insertSql = """
            INSERT INTO bookings (user_id, showtime_id, seat_number)
            VALUES (?, ?, ?)
        """;

        try (Connection c = DataBase.connect()) {

            // Koltuk Doluluk Kontrolü

            try (PreparedStatement check = c.prepareStatement(checkSql)) {
                check.setInt(1, showtimeId);
                check.setInt(2, seatNumber);

                ResultSet rs = check.executeQuery();
                if (rs.next()) {
                    System.out.println("❌ Bu koltuk zaten dolu!");
                    return false;
                }
            }

            // Rezervasyon Ekleme
            try (PreparedStatement insert = c.prepareStatement(insertSql)) {
                insert.setInt(1, userId);
                insert.setInt(2, showtimeId);
                insert.setInt(3, seatNumber);
                insert.executeUpdate();
                return true;
            }
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    /**
     * Koltuğun rezerve edilmeye uygun olup olmadığını kontrol eden metot.
     */

    public Set<Integer> getOccupiedSeats(int showtimeId) {

        Set<Integer> seats = new HashSet<>();
        String sql = "SELECT seat_number FROM bookings WHERE showtime_id = ?";

        try (Connection c = DataBase.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, showtimeId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                seats.add(rs.getInt("seat_number"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return seats;
    }

    /**
     * Rezerve edilen seansları gösteren metot.
     */

    public List<ShowTime> getUserBookings(int userId, boolean onlyFuture) {

        Map<Integer, ShowTime> map = new HashMap<>();

        String sql = """
        SELECT
            s.id AS showtime_id,
            s.date || ' ' || s.time AS show_time,
            s.hall,
            m.id AS movie_id,
            m.title AS movie_title,
            m.duration,
            s.session_type,
            b.seat_number
        FROM bookings b
        JOIN showtimes s ON b.showtime_id = s.id
        JOIN movies m ON s.movie_id = m.id
        WHERE b.user_id = ?
    """;

        //Geçmiş Randevu Gösterimi Engelleme

        if (onlyFuture) {
            sql += " AND datetime(s.date || ' ' || s.time) >= datetime('now','localtime')";
        } else {
            sql += " AND datetime(s.date || ' ' || s.time) < datetime('now','localtime')";
        }

        sql += " ORDER BY s.date, s.time";

        try (Connection c = DataBase.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int showtimeId = rs.getInt("showtime_id");

                ShowTime showTime = map.get(showtimeId);

                if (showTime == null) {

                    int movieId = rs.getInt("movie_id");
                    String title = rs.getString("movie_title");
                    int duration = rs.getInt("duration");
                    String type = rs.getString("session_type");

                    Movie movie;
                    if ("3D".equalsIgnoreCase(type)) {
                        movie = new Movie3D(movieId, title, duration);
                    } else {
                        movie = new Movie2D(movieId, title, duration);
                    }

                    showTime = new ShowTime(
                            showtimeId,
                            rs.getString("show_time"),
                            rs.getString("hall"),
                            movie
                    );

                    map.put(showtimeId, showTime);
                }

                showTime.addSeat(rs.getInt("seat_number"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>(map.values());
    }

     /**
     * Rezerve edilmiş aktif bir seanstı iptal eden metot.
     */

    public boolean cancelBooking(int userId, int showtimeId) {

        String sql = "DELETE FROM bookings WHERE user_id = ? AND showtime_id = ?";

        try (Connection c = DataBase.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, showtimeId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Seeansın geçmişte kalıp kalmadığını kontrol eden metot.
     */

    private boolean isShowtimeInPast(int showtimeId) {

        String sql = """
            SELECT 1
            FROM showtimes
            WHERE id = ?
            AND datetime(date || ' ' || time) < datetime('now','localtime')
        """;

        try (Connection c = DataBase.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, showtimeId);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }
}
