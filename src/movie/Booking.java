package movie;

import util.Bookable;

public class Booking implements Bookable {

    @Override public boolean book() {
        return true;
    }

    @Override public boolean cancel() {
        return true;
    }
}