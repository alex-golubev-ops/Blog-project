package com.leverx.blog.repository;

import com.leverx.blog.entity.Article;
import com.leverx.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Integer> {

}
