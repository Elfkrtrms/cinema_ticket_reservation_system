package menu;

import movie.*;
import service.BookingService;
import util.AppState;
import java.util.*;

/**
 * Filmlerin seans seçenekleri ve koltuk rezervasyonu sağlayan sınıftır.
 */

public class SeatMenu {

    private final List<Integer> selectedSeats = new ArrayList<>();
    private final ShowTime showTime;
    private final BookingService bookingService = new BookingService();
    private final Scanner sc = new Scanner(System.in);

    public SeatMenu(ShowTime showTime) {
        this.showTime = showTime;
    }

    public void start() {

        Movie movie = showTime.getMovie();
        double price = movie.getPrice();

        /**
         * Seçilen film seansının koltuk rezervasyonu sağlayan metot.
         */

        System.out.println("\n≈≈≈ SEANS DETAYI ≈≈≈");
        System.out.println("Film: " + movie.getTitle());
        System.out.println("Tür: " + movie.getType());
        System.out.println("Tarih/Saat: " + showTime.getShowTime());
        System.out.println("Salon: " + showTime.getHall());
        System.out.println("Bilet Ücreti: " + price + " TL");
        System.out.println("≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈");

        while (true) {

            drawSeats();

            System.out.println("\n Seçilen Koltuklar: " + selectedSeats);
            System.out.println(" Toplam Tutar: " + (selectedSeats.size() * price) + " TL");

            System.out.print("Koltuk seç (1-30), 0=Onayla, x=İptal: ");
            String input = sc.nextLine().trim();

            //Koltuk Rezervasyon Onayı

            if (input.equals("0")) {
                confirmBooking(price);
                return;
            }

            //Koltuk Rezervasyon İptali

            if (input.equalsIgnoreCase("x")) {
                System.out.println(" İşlem iptal edildi.");
                return;
            }

            //Kullanıcı Koltuk Seçimi

            try {
                int seat = Integer.parseInt(input);

                if (seat < 1 || seat > 30) {
                    System.out.println("❌ Geçersiz koltuk numarası.");
                    continue;
                }

                if (selectedSeats.contains(seat)) {
                    System.out.println("⚠ Bu koltuğu zaten seçtin.");
                    continue;
                }

                selectedSeats.add(seat);

            } catch (NumberFormatException e) {
                System.out.println("❌ Geçersiz giriş!");
            }
        }
    }

    /**
     * Film seansının mevcut koltuklarını gösteren metot.
     */

    private void drawSeats() {

        Set<Integer> occupied = bookingService.getOccupiedSeats(showTime.getId());

        System.out.println("\n🔴💺 Dolu | 🟢💺 Boş\n");

        for (int i = 1; i <= 30; i++) {

            if (occupied.contains(i)) {System.out.printf("🔴💺(%02d) ", i);}
            else {System.out.printf("🟢💺(%02d) ", i);}

            if (i % 6 == 0) System.out.println();
        }
    }

    /**
     * Seçilen koltuğun rezervastonunu onatlayan metot..
     */

    private void confirmBooking(double price) {

        if (selectedSeats.isEmpty()) {
            System.out.println("❌ Hiç koltuk seçmediniz.");
            return;
        }

        double total = selectedSeats.size() * price;

        System.out.println("\n≈≈≈ REZERVASYON ÖZETİ ≈≈≈");
        System.out.println("🎬 Film: " + showTime.getMovie().getTitle());
        System.out.println("📅 Seans: " + showTime.getShowTime());
        System.out.println("🎟 Koltuklar: " + selectedSeats);
        System.out.println("💳 Toplam Ücret: " + total + " TL");
        System.out.println("≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈");

        System.out.print("Onaylıyor musunuz? (E/H): ");
        String answer = sc.next();

        if (!answer.equalsIgnoreCase("E")) {
            System.out.println("❌ Rezervasyon iptal edildi.");
            return;
        }

        for (int seat : selectedSeats) {
            bookingService.bookSeat(
                    AppState.loggedUserId,
                    showTime.getId(),
                    seat
            );
        }

        System.out.println("✅ Rezervasyon başarıyla tamamlandı!");
    }

}
