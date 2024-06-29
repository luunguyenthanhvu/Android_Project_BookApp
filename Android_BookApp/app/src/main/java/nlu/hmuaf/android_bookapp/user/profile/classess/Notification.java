package nlu.hmuaf.android_bookapp.user.profile.classess;

public class Notification {
    private String title;
    private String content;
    private String timestamp;

    public Notification(String title, String content, String timestamp) {
        this.title = title;
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
