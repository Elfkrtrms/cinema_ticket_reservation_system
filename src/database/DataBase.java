package database;

import java.sql.*;

public class DataBase {

    private static final String URL = "jdbc:sqlite:DataBase.db";

    static {
        createUserTable();
        createMovieTable();
        createShowtimeTable();
        createBookingTable();
    }

    //DataBase'e Bağlanma

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    //TABLOLAR

    //Kullanıcı Tablosu Ekleme

    private static void createUserTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT UNIQUE NOT NULL,
                password TEXT NOT NULL
            );
        """;
        execute(sql);
    }

    //Film Ekleme

    private static void createMovieTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS movies (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT NOT NULL,
                duration INTEGER NOT NULL,
                type TEXT NOT NULL CHECK (type IN ('2D','3D'))
            );
        """;
        execute(sql);
    }

    //Seans Ekleme

    private static void createShowtimeTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS showtimes (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                movie_id INTEGER NOT NULL,
                date TEXT NOT NULL,
                time TEXT NOT NULL,
                hall TEXT NOT NULL,
                FOREIGN KEY(movie_id) REFERENCES movies(id)
            );
        """;
        execute(sql);
    }

    //Reservasyon

    private static void createBookingTable() {
        String sql = """
        CREATE TABLE IF NOT EXISTS bookings (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            user_id INTEGER NOT NULL,
            showtime_id INTEGER NOT NULL,
            seat_number INTEGER NOT NULL,
            created_at TEXT DEFAULT CURRENT_TIMESTAMP,
            UNIQUE(showtime_id, seat_number),
            FOREIGN KEY(user_id) REFERENCES users(id),
            FOREIGN KEY(showtime_id) REFERENCES showtimes(id)
        );
    """;

        execute(sql);
    }

    private static void execute(String sql) {
        try (Connection c = connect();
             Statement s = c.createStatement()) {
            s.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //KULLANICI İŞLEMLERİ

    //Kullanıcı Ekleme

    public static boolean addUser(String username, String password) {

        String sql = "INSERT INTO users(username, password) VALUES (?, ?)";

        try (Connection c = connect();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            return false;
        }
    }

    //Giriş Kontrolü

    public static boolean checkLogin(String username, String password) {

        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection c = connect();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    //Tüm Filmleri Seçme

    public static ResultSet selectAllMovies(Connection c) throws SQLException {
        String sql = "SELECT * FROM movies";
        Statement s = c.createStatement();
        return s.executeQuery(sql);
    }

}
