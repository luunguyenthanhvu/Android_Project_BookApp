package nlu.hmuaf.android_bookapp.user.home.classess;

import java.io.Serializable;

public class BookB implements Serializable {

    private int resourceid;
    private String name;
    private String price;
    public BookB(int resourceid, String name, String price) {
        this.resourceid = resourceid;
        this.name = name;
        this.price = price;
    }

    public int getResourceid() {
        return resourceid;
    }

    public void setResourceid(int resourceid) {
        this.resourceid = resourceid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
