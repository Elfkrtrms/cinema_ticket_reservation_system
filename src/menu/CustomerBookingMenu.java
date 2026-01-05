package menu;

import movie.ShowTime;
import service.BookingService;
import util.AppState;
import java.util.*;

public class CustomerBookingMenu {

    private final Scanner sc = new Scanner(System.in);
    private final BookingService bookingService = new BookingService();

    public void start() {

        if (AppState.loggedUserId == -1) {
            System.out.println("❌ Giriş yapmadan bu alanı kullanamazsınız.");
            return;
        }

        //Kullanıcı Rezervasyon Görüntüleme

        while (true) {

            System.out.println("\n≈≈≈ REZERVASYONLARIM ≈≈≈");
            System.out.println("1- Aktif Seanslarım");
            System.out.println("2- Geçmiş Seanslarım");
            System.out.println("0- Ana Menü");
            System.out.print("Seçim: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> showActiveBookings();
                case 2 -> showPastBookings();
                case 0 -> { return; }
                default -> System.out.println("Geçersiz seçim");
            }
        }
    }

    //Aktif Seans Görüntüleme

    private void showActiveBookings() {

        List<ShowTime> list =
                bookingService.getUserBookings(AppState.loggedUserId, true);

        if (list.isEmpty()) {
            System.out.println("Aktif seansınız yok.");
            return;
        }

        System.out.println("\n≈≈≈ AKTİF SEANSLARIM ≈≈≈");

        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + "- " + list.get(i));
        }

        cancelBooking(list);
    }

    //Seans İptal Etme

    private void cancelBooking(List<ShowTime> bookings) {

        System.out.println("0- Geri");
        System.out.print("İptal edilecek seans: ");

        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 0) return;

        if (choice < 1 || choice > bookings.size()) {
            System.out.println("❌ Geçersiz seçim");
            return;
        }

        ShowTime selected = bookings.get(choice - 1);

        //İptal Onayı

        System.out.println("\n≈≈≈ İPTAL ONAYI ≈≈≈");
        System.out.println("🎬 Film: " + selected.getMovie().getTitle());
        System.out.println("📅 Tarih/Saat: " + selected.getShowTime());
        System.out.println("🏛 Salon: " + selected.getHall());
        System.out.println("💺 Koltuklar: " + selected.getSeats());

        String confirm;

        while (true) {
            System.out.print("❓ Bu seansı iptal etmek istiyor musunuz? (E/H): ");
            confirm = sc.nextLine().trim().toUpperCase();

            if (confirm.equals("E") || confirm.equals("H")) {
                break;
            }

            System.out.println("❌ Hatalı giriş! Lütfen sadece E veya H giriniz.");
        }

        if (confirm.equals("H")) {
            System.out.println("≈≈≈ İptal işlemi vazgeçildi. ≈≈≈");
            return;
        }

        boolean success = bookingService.cancelBooking(
                AppState.loggedUserId,
                selected.getId()
        );

        if (success) {
            System.out.println("✅ Rezervasyon iptal edildi.");
        } else {
            System.out.println("❌ İptal işlemi başarısız.");
        }
    }

    //Geçmiş Seans Görüntüleme

    private void showPastBookings() {

        List<ShowTime> list = bookingService.getUserBookings(AppState.loggedUserId, false);

        if (list.isEmpty()) {
            System.out.println("Geçmiş seans bulunamadı.");
            return;
        }

        System.out.println("\n≈≈≈ GEÇMİŞ SEANSLARIM ≈≈≈");

        for (ShowTime s : list) { System.out.println("- " + s); }
    }
}
