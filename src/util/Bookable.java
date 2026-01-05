package util;

/**
 * Bilet alma ve iptal etme özelliklerini zorunlu kılan arayüz.
 */

public interface Bookable {
    boolean book();
    boolean cancel();
}
