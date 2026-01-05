package customer;

import service.AuthService;
import java.util.Scanner;

/**
 * Kullanıcıların üye kaydını sağladığı sınıf.
 */

public class Register {

    private final Scanner sc;
    private final AuthService auth;

    /**
     * Verilen kullanıcı adı ve şifreyi alarak Database'e kaydeder.
     */

    public Register() {
        sc = new Scanner(System.in);
        auth = new AuthService();
    }

    public void start() {
        System.out.println("≈≈≈ Kayıt Sistemi ≈≈≈");

        System.out.print("Kullanıcı adı: ");
        String username = sc.nextLine();

        System.out.print("Şifre: ");
        String password = sc.nextLine();

        try {
            if (auth.register(username, password)) {
                System.out.println("Kayıt başarılı!");
            } else {
                System.out.println("Kullanıcı zaten mevcut.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
