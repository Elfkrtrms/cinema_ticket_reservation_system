package movie;

import java.util.*;

public class ShowTime {

    //Seans Detayları

    private List<Integer> seats = new ArrayList<>();
    private int id;
    private String showTime;
    private String hall;
    private Movie movie;

    public ShowTime(int id, String showTime, String hall, Movie movie) {
        this.id = id;
        this.showTime = showTime;
        this.hall = hall;
        this.movie = movie;
    }

    public int getId() {return id;}

    public String getShowTime() {return showTime;}

    public String getHall() {return hall;}

    public Movie getMovie() { return movie;}

    public void addSeat(int seat) { seats.add(seat); }

    public List<Integer> getSeats() { return seats; }

    @Override
    public String toString() {
        return "🎬 " + movie.getTitle() +
                " | Tarih/Saat: " + showTime +
                " | Salon: " + hall +
                " | Tür: " + movie.getType() +
                " | 💺 Koltuklar: " + seats;
    }
}
