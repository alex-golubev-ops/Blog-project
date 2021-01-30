package com.leverx.blog.controller;

import com.leverx.blog.entity.Article;
import com.leverx.blog.entity.ArticleDto;
import com.leverx.blog.entity.Status;
import com.leverx.blog.entity.Tag;
import com.leverx.blog.entity.User;
import com.leverx.blog.service.ArticleService;
import com.leverx.blog.service.TagService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final TagService tagService;

    public ArticleController(ArticleService articleService, TagService tagService) {
        this.articleService = articleService;
        this.tagService = tagService;
    }

    @GetMapping("/articles")
    public String allArticles(@AuthenticationPrincipal User user, Model model) {
        List<Article> articlesFromDB;
        if (user == null) {
            articlesFromDB = articleService.findList(Status.PUBLIC);
        } else {
            articlesFromDB = articleService.findList(Status.ALL);
        }
        model.addAttribute("articles", articlesFromDB);
        return "articles";
    }

    @PostMapping("/articles")
    public String add(@AuthenticationPrincipal User user, ArticleDto articleDto, Model model) {
        Article article = new Article();
        article.setTitle(articleDto.getTitle());
        article.setStatus(articleDto.getStatus());
        article.setText(articleDto.getText());
        article.setAuthor(user);
        article.setCreateAt(LocalDate.now());
        article.setUpdateAt(LocalDate.now());
        Set<Tag> tagForSave = tagService.parseToTags(articleDto.getTags());
        article.setTags(tagForSave);
        articleService.save(article);
        List<Article> articlesFromDb = articleService.findList(Status.ALL);
        model.addAttribute("articles", articlesFromDb);
        return "articles";
    }

    @GetMapping("/my")
    public String myArticles(@AuthenticationPrincipal User user, Model model) {
        Set<Article> articles = user.getArticles();
        model.addAttribute("articles", articles);
        return "userArticles";
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable(name = "id") Article article, Model model) {
        ArticleDto articleDto = new ArticleDto();
        articleDto.setId(article.getId());
        articleDto.setText(article.getText());
        articleDto.setTitle(article.getTitle());
        articleDto.setStatus(article.getStatus());
        articleDto.setTags(tagService.parseToString(article.getTags()));
        model.addAttribute("article", articleDto);
        return "updateArticles";
    }

    @PostMapping("/articles/{id}/")
    public String updateArticles(@PathVariable(name = "id") Article article, ArticleDto articleDto,
                                 Model model){
        article.setTitle(articleDto.getTitle());
        article.setText(articleDto.getText());
        article.setStatus(articleDto.getStatus());
        article.setTags(tagService.parseToTags(articleDto.getTags()));
        article.setUpdateAt(LocalDate.now());
        articleService.save(article);
        tagService.clearGarbage();
        List<Article> articlesFromDb = articleService.findList(Status.ALL);
        model.addAttribute("articles", articlesFromDb);
        return "articles";

    }

    @PostMapping("/articles/{id}")
    public String deleteArticle(@PathVariable(name = "id")Article article, Model model){
        articleService.delete(article);
        List<Article> articlesFromBd = articleService.findList(Status.ALL);
        model.addAttribute("articles",articlesFromBd);
        return "articles";
    }


}
