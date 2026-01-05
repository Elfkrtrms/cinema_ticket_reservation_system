package customer;

import util.AppState;
import service.AuthService;
import java.util.Scanner;

/**
 * Kullanıcıların üye girişini sağladığı sınıf.
 */

public class Login {

    /**
     * Verilen kullanıcı adı ve şifreyi kontrol ederek giriş işlemini yapar.
     */

    private final Scanner sc = new Scanner(System.in);
    private final AuthService auth = new AuthService();

    public void start() {
        System.out.println("≈≈≈ GİRİŞ YAP ≈≈≈");

        System.out.print("Kullanıcı adı: ");
        String username = sc.nextLine();

        System.out.print("Şifre: ");
        String password = sc.nextLine();

        if (auth.login(username, password)) {
            int userId = auth.getUserId(username);
            AppState.loggedUserId = userId;
            System.out.println("Giriş başarılı!");
        }
        else {
            System.out.println("Hatalı kullanıcı adı veya şifre.");
        }
    }
}
