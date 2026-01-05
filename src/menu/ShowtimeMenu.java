package menu;

import movie.*;
import service.ShowTimeService;
import java.util.*;
import util.AppState;
import customer.Login;

/**
 * Bir film seçildikten sonra, o filmin seanslarını listeleyen ve kullanıcıdan seçim yapmasını isteyen sınıftır.
 */

public class ShowtimeMenu {

    private final Movie movie;
    private final Scanner sc = new Scanner(System.in);
    private final ShowTimeService showTimeService = new ShowTimeService();

    public ShowtimeMenu(Movie movie) {
        this.movie = movie;
    }

    public void start() {

        //Film Seansları Gösterimi

        List<ShowTime> showtimes = showTimeService.getShowtimesByMovieId(movie.getId());

        System.out.println("\n≈≈≈ " + movie.getTitle() + " SEANSLARI ≈≈≈");

        if (showtimes.isEmpty()) {
            System.out.println("Bu film için seans bulunamadı.");
            return;
        }

        for (int i = 0; i < showtimes.size(); i++) { System.out.println((i + 1) + "- " + showtimes.get(i)); }

        System.out.println("0- Film menüsüne dön");
        System.out.print("Seçim: ");

        if (!sc.hasNextInt()) {
            sc.nextLine();
            System.out.println("Geçersiz giriş!");
            return;
        }

        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 0) return;

        if (choice < 1 || choice > showtimes.size()) {
            System.out.println("Geçersiz seçim");
            return;
        }

        ShowTime selected = showtimes.get(choice - 1);

        if (AppState.loggedUserId == -1) {
            System.out.println("❌ Rezervasyon için giriş yapmalısınız.");
            System.out.println("1- Giriş Yap");
            System.out.println("2- Geri Dön");

            int secim = sc.nextInt();
            sc.nextLine();

            if (secim == 1) { new Login().start(); }

            return;
        }
        new SeatMenu(selected).start();
    }

}
