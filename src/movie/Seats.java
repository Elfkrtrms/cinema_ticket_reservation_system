package movie;

/**
 * Sinema salonundaki fiziksel koltuk yapısını modelleyen sınıf.
 * Koltuk numarası, sırası ve doluluk durumu gibi verileri taşır.
 */

public class Seats {
    private int number;       // Koltuk Numarası (Örn: 5)
    private boolean isOccupied; // Dolu mu? (True/False)

    public Seats(int number, boolean isOccupied) {
        this.number = number;
        this.isOccupied = isOccupied;
    }

    public int getNumber() { return number; }

    public boolean isOccupied() { return isOccupied;}

    public void setOccupied(boolean occupied) {isOccupied = occupied;}

    @Override
    public String toString() {
        return "Koltuk No: " + number + (isOccupied ? " (Dolu)" : " (Boş)");
    }
}