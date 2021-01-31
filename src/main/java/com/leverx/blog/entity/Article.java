package com.leverx.blog.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Integer id;

    private String title;

    private String text;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    private LocalDate createAt;

    private LocalDate updateAt;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "articles_tags",
            joinColumns = @JoinColumn(name = "article_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id",
                    referencedColumnName = "id"))
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();

    public Article() {
    }

    public Article(String title, String text, Status status) {
        this.title = title;
        this.text = text;
        this.status = status;

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

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

}