package menu;

import java.util.Scanner;
import customer.Login;
import customer.Register;
import util.AppState;

public class MainMenu {

    private final Scanner sc = new Scanner(System.in);

    //Ana Menü Görüntüleme

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

    //Giriş Yapmamış Kullanıcı Menüsü

    private void handleGuestMenu(int choice) {
        switch (choice) {
            case 1 -> new Login().start();
            case 2 -> new Register().start();
            case 3 -> new MovieMenu().start();
            case 0 -> System.exit(0);
            default -> System.out.println("Geçersiz seçim");
        }
    }

    //Giriş Yapmış Kullanıcı Menüsü

    private void handleUserMenu(int choice) {
        switch (choice) {
            case 1 -> new MovieMenu().start();
            case 2 -> new CustomerBookingMenu().start();
            case 3 -> logout();
            case 0 -> System.exit(0);
            default -> System.out.println("Geçersiz seçim");
        }
    }

    //Çıkş Yapma

    private void logout() {
        AppState.loggedUserId = -1;
        System.out.println("👋 Çıkış yapıldı.");
    }
}
