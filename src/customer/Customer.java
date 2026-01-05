package customer;

public class Customer {
    private int id;
    private String username;

    public Customer(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public int getId() { return id; }

    public String getUsername() { return username; }

    @Override
    public String toString() {
        return "Müşteri [ID=" + id + ", İsim=" + username + "]";
    }
}