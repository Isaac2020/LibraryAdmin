package com.gdgminna.android.futminnalibraryadmin.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

public class Book {

    public String uid;
    public String postedBy;
    public String title;
    public String author;
    public String ispnNumber;
    public Long dateAndTime;
    public String detail;
    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();



    public Book() {
        // Default constructor required for calls to DataSnapshot.getValue(Book.class)
    }

    public Book (String uid, String postedBy, String title, String author, String ispnNumber, String detail, Long dateAndTime) {
        this.uid = uid;
        this.postedBy = postedBy;
        this.title = title;
        this.author = author;
        this.ispnNumber = ispnNumber;
        this.detail = detail;
        this.dateAndTime = dateAndTime;
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("postedby", postedBy);
        result.put("title", title);
        result.put("author", author);
        result.put("ispnNumber", ispnNumber);
        result.put("detail", detail);
        result.put("dateAndTime", ServerValue.TIMESTAMP);
        result.put("starCount", starCount);
        result.put("stars", stars);

        return result;
    }
}
