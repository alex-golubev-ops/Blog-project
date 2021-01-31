package com.leverx.blog.controller;

import com.leverx.blog.entity.Article;
import com.leverx.blog.entity.Comment;
import com.leverx.blog.entity.CommentDto;
import com.leverx.blog.entity.User;
import com.leverx.blog.service.ArticleService;
import com.leverx.blog.service.CommentService;
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
import java.time.LocalDate;

@Controller
public class CommentController {

    private final ArticleService articleService;

    private final CommentService commentService;

    public CommentController(ArticleService articleService, CommentService commentService) {
        this.articleService = articleService;
        this.commentService = commentService;
    }

    @GetMapping("/articles/{id}/comments")
    public String getComments(@PathVariable(name = "id") Article article,
                              @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                              Model model) {
        Page<Comment> comments = commentService.findAllByArticle(article, pageable);
        model.addAttribute("page", comments);
        model.addAttribute("article", article);
        model.addAttribute("url", "articles/" + article.getId() + "/comments");
        return "commentsArticle";
    }

    @PostMapping("/articles/{id}/comments")
    public String addComment(@AuthenticationPrincipal User user, @PathVariable(name = "id") Article article,
                             @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                             CommentDto commentDto, Model model) {
        Comment comment = new Comment();
        comment.setMessage(commentDto.getMessage());
        comment.setAuthor(user);
        comment.setCreateAt(LocalDate.now());
        article.getComments().add(comment);
        comment.setArticle(article);
        articleService.save(article);
        Page<Comment> comments = commentService.findAllByArticle(article, pageable);
        model.addAttribute("page", comments);
        model.addAttribute("article", article);
        model.addAttribute("url", "articles/" + article.getId() + "/comments");
        return "commentsArticle";
    }

    @PostMapping("/articles/{article_id}/comments/{comment_id}")
    public String deleteComment(@PathVariable(name = "article_id") Article article,
                                @PathVariable(name = "comment_id") Comment comment,
                                @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                @AuthenticationPrincipal User user, Model model) {
        if (article.getAuthor().equals(user) || comment.getAuthor().equals(user)) {
            article.getComments().remove(comment);
            articleService.save(article);
        }
        Page<Comment> comments = commentService.findAllByArticle(article, pageable);
        model.addAttribute("page", comments);
        model.addAttribute("article", article);
        model.addAttribute("url", "articles/" + article.getId() + "/comments");
        return "commentsArticle";
    }
}
