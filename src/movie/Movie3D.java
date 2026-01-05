package movie;

public class Movie3D extends Movie {

    public Movie3D(int id, String title, int duration) {
        super(id, title, duration);
    }

    @Override
    public double getPrice() {
        return 180.0;
    }

    @Override
    public String getType() {
        return "3D";
    }
}
