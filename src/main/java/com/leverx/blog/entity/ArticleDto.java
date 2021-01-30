package com.leverx.blog.entity;

public class ArticleDto {

    private Integer id;

    private String title;

    private String text;

    private Status status;

    private String tags;

    public ArticleDto() {
    }

    public ArticleDto(String title, String text, Status status, String tags) {
        this.title = title;
        this.text = text;
        this.status = status;
        this.tags = tags;
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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
