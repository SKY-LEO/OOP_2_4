package Service;

import java.io.Serializable;

public class GarbageCollection extends Service implements Serializable {
    public GarbageCollection(String description, int price) {
        this.setDescription(description);
        this.setPrice(price);
    }

    @Override
    public String toString() {
        return "Утилизация";
    }
}
