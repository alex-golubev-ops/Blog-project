package com.leverx.blog.controller;

import com.leverx.blog.entity.Article;
import com.leverx.blog.entity.Comment;
import com.leverx.blog.entity.CommentDto;
import com.leverx.blog.entity.User;
import com.leverx.blog.service.ArticleService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.Set;

@Controller
public class CommentController {

    private final ArticleService articleService;

    public CommentController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articles/{id}/comments")
    public String getComments(@PathVariable(name = "id") Article article, Model model) {
        Set<Comment> comments = article.getComments();
        model.addAttribute("comments", comments);
        model.addAttribute("article", article);
        return "commentsArticle";
    }

    @PostMapping("/articles/{id}/comments")
    public String addComment(@AuthenticationPrincipal User user, @PathVariable(name = "id") Article article,
                             CommentDto commentDto, Model model) {
        Comment comment = new Comment();
        comment.setMessage(commentDto.getMessage());
        comment.setAuthor(user);
        comment.setCreateAt(LocalDate.now());
        article.getComments().add(comment);
        comment.setArticle(article);
        articleService.save(article);
        Set<Comment> comments = article.getComments();
        model.addAttribute("comments", comments);
        model.addAttribute("article", article);
        return "commentsArticle";
    }

    @PostMapping("/articles/{article_id}/comments/{comment_id}")
    public String deleteComment(@PathVariable(name = "article_id") Article article,
                                @PathVariable(name = "comment_id") Comment comment,
                                @AuthenticationPrincipal User user, Model model) {
        if (article.getAuthor().equals(user) || comment.getAuthor().equals(user)) {
            article.getComments().remove(comment);
            articleService.save(article);
        }
        Set<Comment> comments = article.getComments();
        model.addAttribute("comments", comments);
        model.addAttribute("article", article);
        return "commentsArticle";
    }
}
