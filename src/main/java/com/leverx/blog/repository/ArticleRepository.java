package com.leverx.blog.repository;

import com.leverx.blog.entity.Article;
import com.leverx.blog.entity.Status;
import com.leverx.blog.entity.Tag;
import com.leverx.blog.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Integer> {

   List<Article> findByTags(Tag tag);

   Page<Article> findByStatus(Status status, Pageable pageable);

   Page<Article> findAll(Pageable pageable);

   Page<Article> findByAuthor(User author, Pageable pageable);
}
