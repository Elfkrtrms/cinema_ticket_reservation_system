package movie;

/**
 * 2D filmlerin işlemlerini temsil eden sınıf.
 * Film tipi ve fiyatı getiren metotlar içerir.
 */

public class Movie2D extends Movie {

    public Movie2D(int id, String title, int duration) { super(id, title, duration);}

    @Override
    public double getPrice() { return 120.0; }

    @Override
    public String getType() { return "2D"; }
}
