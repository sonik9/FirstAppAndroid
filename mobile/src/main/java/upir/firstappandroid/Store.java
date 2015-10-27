package upir.firstappandroid;

import java.io.Serializable;

/**
 * Created by vital on 27/10/2015.
 */
public class Store implements Serializable {
    private String name;
    private Double price;
    private int id;


    public Store(String name, Double price, int id) {
        this.name = name;
        this.price = price;
        this.id = id;
    }

    public Store() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
