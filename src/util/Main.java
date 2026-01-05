package util;

import menu.MainMenu;

/**
 * Uygulamanın çalışmaya başladığı ana sınıftır.
 * Buradan ana menü çağrılır ve program başlatılır.
 */

public class Main {
    public static void main(String[] args) {
        new MainMenu().start();
    }
}
