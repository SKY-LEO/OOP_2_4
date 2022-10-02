package Service;

import java.io.Serializable;

public class Cleaner extends Service implements Serializable {
    public Cleaner(String description, int price) {
        this.setDescription(description);
        this.setPrice(price);
    }

    @Override
    public String toString() {
        return "Уборщик";
    }
}
