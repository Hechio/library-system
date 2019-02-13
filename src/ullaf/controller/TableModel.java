package ullaf.controller;

import java.awt.*;

public class TableModel {
    String book_id;
    String user_id;
    Double fine_id;


    public TableModel(String book_id, String student_id) {
        this.book_id = book_id;
        this.user_id = student_id;
    }



    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Double getFine_id() {
        return fine_id;
    }

    public void setFine_id(Double fine_id) {
        this.fine_id = fine_id;
    }

    public TableModel(String book_id, String user_id, double fine_id) {
        this.book_id = book_id;
        this.user_id = user_id;
        this.fine_id = fine_id;
    }


}
