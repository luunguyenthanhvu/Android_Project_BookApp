package nlu.hmuaf.android_bookapp.user.profile.classess;

public class Review {
    private String title;
    private String content;
    private String author;
    private String bookTitle;

    public Review(String title, String content, String author, String bookTitle) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.bookTitle = bookTitle;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public String getBookTitle() {
        return bookTitle;
    }
}
