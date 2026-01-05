package menu;

import java.util.Scanner;
import customer.*;
import util.AppState;

/**
 * Uygulamanın başlangıç ekranını ve ana menü seçeneklerini oluşturur.
 * Kullanıcıların giriş yapma, kayıt olma veya çıkış yapma işlemlerini başlattığı sınıftır.
 */

public class MainMenu {

    private final Scanner sc = new Scanner(System.in);

    public void start() {

        while (true) {

            System.out.println("\n≈≈≈ ANA MENÜ ≈≈≈");

            if (AppState.loggedUserId == -1) {
                System.out.println("1- Giriş Yap");
                System.out.println("2- Kayıt Ol");
                System.out.println("3- Filmleri Gör");
                System.out.println("0- Çıkış");
            } else {
                System.out.println("1- Filmleri Gör");
                System.out.println("2- Rezervasyonlarım");
                System.out.println("3- Çıkış Yap");
                System.out.println("0- Programdan Çık");
            }

            System.out.print("Seçim: ");
            int choice = sc.nextInt();
            sc.nextLine();

            if (AppState.loggedUserId == -1) {
                handleGuestMenu(choice);
            } else {
                handleUserMenu(choice);
            }
        }
    }

    /**
     * Giriş yapmamış kullanıcının ana menü seçeneklerini içeren metot.
     */

    private void handleGuestMenu(int choice) {
        switch (choice) {
            case 1 -> new Login().start();
            case 2 -> new Register().start();
            case 3 -> new MovieMenu().start();
            case 0 -> System.exit(0);
            default -> System.out.println("Geçersiz seçim");
        }
    }

    /**
     * Giriş yapmış kullanıcının ana menü seçeneklerini içeren metot.
     */

    private void handleUserMenu(int choice) {
        switch (choice) {
            case 1 -> new MovieMenu().start();
            case 2 -> new CustomerBookingMenu().start();
            case 3 -> logout();
            case 0 -> System.exit(0);
            default -> System.out.println("Geçersiz seçim");
        }
    }

    /**
     * Giriş yapmış kullanıcının çıkış yapmasını sağlayan metot.
     */

    private void logout() {
        AppState.loggedUserId = -1;
        System.out.println("👋 Çıkış yapıldı.");
    }
}
