package Service;

import java.io.Serializable;
import java.util.Objects;

public abstract class Service implements Serializable {
    private String description;
    private int price;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        return price == service.price && Objects.equals(description, service.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, price);
    }

    @Override
    public String toString() {
        return "Service{" +
                "description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
