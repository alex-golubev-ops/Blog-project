package com.leverx.blog.service;

import com.leverx.blog.entity.Article;
import com.leverx.blog.entity.Comment;
import com.leverx.blog.repository.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void save(Comment comment){
        commentRepository.save(comment);
    }

    public Page<Comment> findAllByArticle(Article article, Pageable pageable){
        return commentRepository.findAllByArticle(article,pageable);
    }


}
