package ullaf.controller;

public class TableModel1 {
     String book_id;
     String book_name;
     String book_author;
     String book_duration;


    public TableModel1(String book_id, String book_name, String book_author, String book_duration) {
        this.book_id=book_id;
        this.book_name=book_name;
        this.book_author=book_author;
        this.book_duration=book_duration;
    }
    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getBook_author() {
        return book_author;
    }

    public void setBook_author(String book_author) {
        this.book_author = book_author;
    }

    public String getBook_duration() {
        return book_duration;
    }

    public void setBook_duration(String book_duration) {
        this.book_duration = book_duration;
    }

}
