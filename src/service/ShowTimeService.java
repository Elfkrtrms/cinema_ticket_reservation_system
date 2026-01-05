package service;

import movie.*;
import database.DataBase;
import java.sql.*;
import java.util.*;

/**
 * Filmlere ait seans bilgilerini yöneten servis sınıfı.
 * Veritabanından belirli bir filme ait seansları çeker ve listeler.
 */

public class ShowTimeService {

    /**
     * Filmlere ait seans bilgilerini gösteren metot.
     */

    public List<ShowTime> getShowtimesByMovieId(int movieId) {

        List<ShowTime> list = new ArrayList<>();

        String sql = """
        SELECT s.id,
               s.date || ' ' || s.time AS show_time,
               s.hall,
               m.id AS movie_id,
               m.title AS movie_title,
               m.duration,
               s.session_type
        FROM showtimes s
        JOIN movies m ON s.movie_id = m.id
        WHERE s.movie_id = ?
          AND datetime(s.date || ' ' || s.time) >= datetime('now','localtime')
        ORDER BY s.date, s.time
    """;

        try (Connection c = DataBase.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, movieId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Movie movie;
                if ("3D".equalsIgnoreCase(rs.getString("session_type"))) {
                    movie = new Movie3D(
                            rs.getInt("movie_id"),
                            rs.getString("movie_title"),
                            rs.getInt("duration")
                    );
                } else {
                    movie = new Movie2D(
                            rs.getInt("movie_id"),
                            rs.getString("movie_title"),
                            rs.getInt("duration")
                    );
                }

                list.add(new ShowTime(
                        rs.getInt("id"),
                        rs.getString("show_time"),
                        rs.getString("hall"),
                        movie
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
