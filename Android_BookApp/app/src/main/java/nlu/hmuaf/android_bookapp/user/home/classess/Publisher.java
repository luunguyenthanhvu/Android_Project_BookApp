package nlu.hmuaf.android_bookapp.user.home.classess;

public class Publisher {
    int resourceid;
    String name ;
    String address;
    String hotLine;

    public Publisher(int resourceid, String name, String address, String hotLine) {
        this.resourceid = resourceid;
        this.name = name;
        this.address = address;
        this.hotLine = hotLine;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHotLine() {
        return hotLine;
    }

    public void setHotLine(String hotLine) {
        this.hotLine = hotLine;
    }
}
