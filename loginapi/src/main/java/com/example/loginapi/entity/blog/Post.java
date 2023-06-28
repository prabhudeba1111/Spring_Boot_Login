package com.example.loginapi.entity.blog;

import jakarta.persistence.*;

@Entity
@Table
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String content;

    public Post(){
    }
    public Post(String title, String content){
        this.title = title;
        this.content = content;
    }
    public long getId(){
        return id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
