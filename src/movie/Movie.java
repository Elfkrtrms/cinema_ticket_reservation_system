package movie;

public abstract class Movie {

    protected int id;
    protected String title;
    protected int duration;

    //Film Detayları

    public Movie(int id, String title, int duration) {
        this.id = id;
        this.title = title;
        this.duration = duration;
    }

    public int getId() { return id; }

    public String getTitle() { return title; }

    public int getDuration() { return duration; }

    public abstract double getPrice();

    public abstract String getType();

    @Override
    public String toString() {
        return title +
                " (" + duration + " dk, " +
                getType() +
                ", " + getPrice() + " TL)";
    }
}
