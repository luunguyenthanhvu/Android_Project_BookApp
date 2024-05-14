package nlu.hmuaf.android_bookapp;

public class Book {
    private String nameBook;
    private String author;

    public Book(String nameBook, String author) {
        this.nameBook = nameBook;
        this.author = author;
    }

    public String getNameBook() {
        return nameBook;
    }

    public void setNameBook(String nameBook) {
        this.nameBook = nameBook;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


}
