package service;

import database.DataBase;
import movie.*;
import java.sql.*;
import java.util.*;

public class MovieService {

    //Film Verilerini Alma

    public List<Movie> getAllMovies() {

        List<Movie> movies = new ArrayList<>();

        try (Connection c = DataBase.connect();
             ResultSet rs = DataBase.selectAllMovies(c)) {

            while (rs.next()) {

                int id = rs.getInt("id");
                String title = rs.getString("title");
                int duration = rs.getInt("duration");
                String type = rs.getString("type");

                Movie movie;

                if (type.equalsIgnoreCase("3D")) {
                    movie = new Movie3D(id, title, duration);
                } else {
                    movie = new Movie2D(id, title, duration);
                }

                movies.add(movie);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return movies;
    }
}
