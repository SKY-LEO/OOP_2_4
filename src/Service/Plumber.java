package Service;

import java.io.Serializable;
import java.util.Comparator;

public class Plumber extends Service implements Serializable {
    public Plumber(String description, int price) {
        this.setDescription(description);
        this.setPrice(price);
    }

    @Override
    public String toString() {
        return "Водопроводчик";
    }
}
