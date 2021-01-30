package com.leverx.blog.entity;

public class CommentDto {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CommentDto() {
    }

    public CommentDto(String message) {
        this.message = message;
    }
}
