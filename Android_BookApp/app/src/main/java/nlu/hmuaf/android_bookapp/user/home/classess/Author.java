package nlu.hmuaf.android_bookapp.user.home.classess;

public class Author {
    private int  resourceid;
    private String name;
    private int age;
    public Author(int resourceid, String name, int age) {
        this.resourceid = resourceid;
        this.name = name;
        this.age = age;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
