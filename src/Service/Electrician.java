package Service;

import java.io.Serializable;

public class Electrician extends Service implements Serializable {
    public Electrician(String description, int price) {
        this.setDescription(description);
        this.setPrice(price);
    }

    @Override
    public String toString() {
        return "Электрик";
    }
}
