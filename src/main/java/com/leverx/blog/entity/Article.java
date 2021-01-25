package com.leverx.blog.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String title;

    private String text;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    private LocalDate createAt;

    private LocalDate updateAt;

    public Article() {
    }

    public Article(String title, String text, Status status,
                   User author, LocalDate createAt, LocalDate updateAt) {
        this.title = title;
        this.text = text;
        this.status = status;
        this.author = author;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthorId(User author) {
        this.author = author;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public LocalDate getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDate updateAt) {
        this.updateAt = updateAt;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
