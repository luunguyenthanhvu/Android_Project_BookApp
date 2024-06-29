package nlu.hmuaf.android_bookapp.user.cart_user.beans;

public class Book_Images {
    private long bookimagesId;
    private long bookId;
    private String url;

    public Book_Images(long bookimagesId, long bookId, String url) {
        this.bookimagesId = bookimagesId;
        this.bookId = bookId;
        this.url = url;
    }
    public Book_Images(){

    }

    public long getBookimagesId() {
        return bookimagesId;
    }

    public void setBookimagesId(long bookimagesId) {
        this.bookimagesId = bookimagesId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
