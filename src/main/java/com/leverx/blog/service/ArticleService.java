package com.leverx.blog.service;

import com.leverx.blog.entity.Article;
import com.leverx.blog.entity.Status;
import com.leverx.blog.repository.ArticleRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void save(Article article) {
        articleRepository.save(article);
    }

    public List<Article> findList(Status status){
        if(status==Status.ALL){
            return articleRepository.findAll();
        }
        else {
            return articleRepository.findAll().stream().filter(e->e.getStatus().equals(Status.PUBLIC))
                    .collect(Collectors.toList());
        }
    }

    public void delete(Article article){
        articleRepository.delete(article);
    }



}
