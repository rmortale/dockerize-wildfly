package com.example.demo.resources.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Book {
    
    @Id
    private String id;
    private String title;

    
    public Book(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    

}
