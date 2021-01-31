package com.leverx.blog.repository;

import com.leverx.blog.entity.Article;
import com.leverx.blog.entity.Status;
import com.leverx.blog.entity.Tag;
import com.leverx.blog.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

    List<Article> findByTags(Tag tag);

    Page<Article> findByStatusIn(List<Status> status, Pageable pageable);

    @Query("select a from Article a inner join a.tags t where a.status in(?1) and t.name in (?2)")
    Page<Article> findByStatusInAndTags(List<Status> status, List<String> tags, Pageable pageable);

    Page<Article> findAll(Pageable pageable);

    Page<Article> findByAuthor(User author, Pageable pageable);

}
