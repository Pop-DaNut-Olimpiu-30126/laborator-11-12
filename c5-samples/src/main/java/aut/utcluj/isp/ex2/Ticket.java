package aut.utcluj.isp.ex2;

/**
 * @author stefan
 */
public class Ticket {
    private String id;
    private String customerName;
    private Double price;

    public Ticket(String id, String customerName, Double price) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Double getPrice() {
        return price;
    }
}
