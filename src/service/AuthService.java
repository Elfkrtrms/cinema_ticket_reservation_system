package service;

import database.DataBase;
import java.sql.*;

/**
 * Kullanıcı kimlik doğrulama ve kayıt işlemlerini yöneten servis sınıfıdır.
 * Kullanıcı girişini doğrular ve yeni üye kaydı oluşturur.
 */

public class AuthService {

    public boolean register(String username, String password) {
        if (username == null || username.isBlank()
                || password == null || password.isBlank()) {
            throw new IllegalArgumentException("Alanlar boş olamaz");
        }
        return DataBase.addUser(username, password);
    }

    public boolean login(String username, String password) {
        return DataBase.checkLogin(username, password);
    }

    public int getUserId(String username) {

        String sql = "SELECT id FROM users WHERE username = ?";

        try (Connection c = DataBase.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

}

