package com.leverx.blog.service;

import com.leverx.blog.entity.Article;
import com.leverx.blog.entity.Status;
import com.leverx.blog.entity.User;
import com.leverx.blog.repository.ArticleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    private final TagService tagService;

    public ArticleService(ArticleRepository articleRepository, TagService tagService) {
        this.articleRepository = articleRepository;
        this.tagService = tagService;
    }

    public void save(Article article) {
        articleRepository.save(article);
    }

    public Page<Article> findList(List<Status> status, String filter, Pageable pageable) {
        if (filter != null && !filter.isEmpty()) {
            return articleRepository.findByStatusInAndTags(status, Arrays.asList(filter.split(" ")), pageable);
        }
        return articleRepository.findByStatusIn(status,pageable);

    }

    public Page<Article> findByAuthor(User author, Pageable pageable) {
        return articleRepository.findByAuthor(author, pageable);
    }

    public void update(Article article, boolean access) {
        if (access) {
            articleRepository.save(article);
            tagService.clearGarbage();
        }
    }

    public void delete(Article article, boolean access) {
        if (access) {
            articleRepository.delete(article);
        }
    }
    public Page<Article> findAll(Pageable pageable){
        return articleRepository.findAll(pageable);

    }

}
