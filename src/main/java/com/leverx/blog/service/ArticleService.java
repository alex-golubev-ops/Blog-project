package com.leverx.blog.service;

import com.leverx.blog.entity.Article;
import com.leverx.blog.entity.Status;
import com.leverx.blog.entity.User;
import com.leverx.blog.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public void save(Article article) {
        articleRepository.save(article);
    }
    public List<Article> findList(boolean draftAndPublic){
        if(draftAndPublic){
            return articleRepository.findAll();
        }
        else {
            return articleRepository.findAll().stream().filter(e->e.getStatus().equals(Status.PUBLIC))
                    .collect(Collectors.toList());
        }
    }



}
