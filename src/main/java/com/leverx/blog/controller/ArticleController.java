package com.leverx.blog.controller;

import com.leverx.blog.entity.*;
import com.leverx.blog.repository.ArticleRepository;
import com.leverx.blog.service.ArticleService;
import com.leverx.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final TagService tagService;
    @Autowired
    private ArticleRepository articleRepository;

    public ArticleController(ArticleService articleService, TagService tagService) {
        this.articleService = articleService;
        this.tagService = tagService;
    }

    @GetMapping("/articles")
    public String allArticles(@AuthenticationPrincipal User user,
                              @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                              @RequestParam(defaultValue = "", required = false) String filter,
                              Model model) {
        Page<Article> page;
        if (user == null) {
            page = articleService.findList(Collections.singletonList(Status.PUBLIC), filter, pageable);
        } else {
            page = articleService.findList(Arrays.asList(Status.PUBLIC, Status.DRAFT), filter, pageable);
        }
        model.addAttribute("page", page);
        model.addAttribute("url", "/articles");
        model.addAttribute("filter",filter);
        return "articles";
    }

    @PostMapping("/articles")
    public String add(@AuthenticationPrincipal User user,
                      @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                      ArticleDto articleDto, Model model) {
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
        Page<Article> articlesFromBd = articleService.findAll(pageable);
        model.addAttribute("page", articlesFromBd);
        model.addAttribute("url", "/articles");
        return "articles";
    }

    @GetMapping("/my")
    public String myArticles(@AuthenticationPrincipal User user,
                             @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                             Model model) {
        Page<Article> articlesFromBd = articleService.findByAuthor(user, pageable);
        model.addAttribute("page", articlesFromBd);
        model.addAttribute("url", "/my");
        return "userArticles";
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable(name = "id") Article article,
                             @AuthenticationPrincipal User user,
                             @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                             Model model) {
        if (user.equals(article.getAuthor())) {
            ArticleDto articleDto = new ArticleDto();
            articleDto.setId(article.getId());
            articleDto.setText(article.getText());
            articleDto.setTitle(article.getTitle());
            articleDto.setStatus(article.getStatus());
            articleDto.setTags(tagService.parseToString(article.getTags()));
            model.addAttribute("article", articleDto);
            return "updateArticles";
        }
        Page<Article> articlesFromBd = articleService.findAll(pageable);
        model.addAttribute("page", articlesFromBd);
        model.addAttribute("url", "/articles");
        return "articles";

    }

    @PostMapping("/articles/{id}/update")
    public String updateArticles(@PathVariable(name = "id") Article article, @AuthenticationPrincipal User user,
                                 @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                 ArticleDto articleDto, Model model) {
        article.setTitle(articleDto.getTitle());
        article.setText(articleDto.getText());
        article.setStatus(articleDto.getStatus());
        article.setTags(tagService.parseToTags(articleDto.getTags()));
        article.setUpdateAt(LocalDate.now());
        articleService.update(article, user.equals(article.getAuthor()));
        Page<Article> articlesFromBd = articleService.findAll(pageable);
        model.addAttribute("page", articlesFromBd);
        model.addAttribute("url", "/articles");
        return "articles";

    }

    @PostMapping("/articles/{id}")
    public String deleteArticle(@PathVariable(name = "id") Article article,
                                @AuthenticationPrincipal User user,
//                                @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                Model model) {
        articleRepository.delete(article);
//        articleService.delete(article, user.equals(article.getAuthor()));
//        Page<Article> articlesFromBd = articleService.findList(Status.ALL, pageable);
//        model.addAttribute("page", articlesFromBd);
//        model.addAttribute("url", "/articles");
        return "articles";
    }


}
