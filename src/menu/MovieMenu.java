package menu;

import movie.Movie;
import service.MovieService;
import java.util.*;

public class MovieMenu {

    private final Scanner sc = new Scanner(System.in);
    private final MovieService movieService = new MovieService();

    public void start() {

        //Film Listesi

        while (true) {

            List<Movie> movies = movieService.getAllMovies();

            System.out.println("\n≈≈≈ FİLM LİSTESİ ≈≈≈");

            for (int i = 0; i < movies.size(); i++) { System.out.println((i + 1) + "- " + movies.get(i)); }

            System.out.println("0- Ana Menüye Dön");
            System.out.print("Film seçiniz: ");

            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 0) return;

            if (choice < 1 || choice > movies.size()) {
                System.out.println("Geçersiz seçim");
                continue;
            }

            Movie selectedMovie = movies.get(choice - 1);

            movieActionMenu(selectedMovie);
        }
    }

    //Seçilen Film Seçenekleri

    private void movieActionMenu(Movie movie) {

        while (true) {
            System.out.println("\nSeçilen Film: " + movie.getTitle());
            System.out.println("1- Film Seanslarını Görüntüle");
            System.out.println("2- Film Listesine Dön");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> new ShowtimeMenu(movie).start();
                case 2 -> { return; }
                default -> System.out.println("Hatalı seçim");
            }
        }
    }
}
