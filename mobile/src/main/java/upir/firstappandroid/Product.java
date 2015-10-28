package upir.firstappandroid;

import java.io.Serializable;

/**
 * Created by vital on 27/10/2015.
 */
public class Product implements Serializable {
    private String name;
    private Double price;
    private long id;
    private boolean check;


    public Product(String name, Double price) {
        this.name = name;
        this.price = price;
        check = false;
    }

    public Product(String name, Double price, long id) {
        this.name = name;
        this.price = price;
        this.id = id;
        this.check = false;
    }

    public Product() {
        check = false;
    }


    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
