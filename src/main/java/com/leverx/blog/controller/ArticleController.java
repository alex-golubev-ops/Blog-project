package com.leverx.blog.controller;

import com.leverx.blog.entity.Article;
import com.leverx.blog.entity.Status;
import com.leverx.blog.entity.Tag;
import com.leverx.blog.entity.User;
import com.leverx.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class ArticleController {

    @Autowired
    private ArticleService articleService;



    @GetMapping("/articles")
    public String main(@AuthenticationPrincipal User user, Model model) {
        List<Article> articlesFromDB;
        if(user==null){
            articlesFromDB = articleService.findList(false);
        }
        else {
            articlesFromDB = articleService.findList(true);
        }
        model.addAttribute("articles", articlesFromDB);
        return "articles";
    }

    @PostMapping("/articles")
    public String add(@AuthenticationPrincipal User user, @RequestParam String title,
                      @RequestParam String tags, @RequestParam String text,
                      @RequestParam String status, Model model) {

        Article article = new Article(title, text, Status.valueOf(status), user, LocalDate.now(), LocalDate.now());
        articleService.save(article);
        List<Article> articlesFromDb = articleService.findList(true);
        model.addAttribute("articles", articlesFromDb);
        return "articles";
    }


}
