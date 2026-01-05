package movie;

import util.Bookable;

/**
 * Rezervasyon işlemlerini temsil eden sınıftır.
 * Rezervasyon yapma ve iptal etme metotlarını içerir.
 */

public class Booking implements Bookable {

    @Override public boolean book() {
        return true;
    }

    @Override public boolean cancel() {
        return true;
    }
}