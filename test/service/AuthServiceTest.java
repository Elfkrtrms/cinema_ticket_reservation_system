package service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Kullanıcı giriş ve kayıt senaryolarını test eder.
 * Yanlış şifre veya olmayan kullanıcı durumlarını kontrol eder.
 */

class AuthServiceTest {

    //Doğru bilgilerle giriş testi
    @Test
    void testLoginSuccess() {
        AuthService authService = new AuthService();

        //Doğru Kullanıcı Bilgisi Girişi Testi

        authService.register("elif", "123");

        boolean sonuc = authService.login("elif", "123");

        assertTrue(sonuc, "Kullanıcı adı ve şifre doğruysa giriş başarılı olmalı.");
    }

    @Test
    void testLoginFailure() {
        AuthService authService = new AuthService();

        //Yanlış Kullanıcı Bilgisi Girişi Testi

        boolean sonuc = authService.login("elif", "yanlissifre");

        assertFalse(sonuc, "Yanlış şifre girildiğinde giriş başarısız olmalı.");
    }
}