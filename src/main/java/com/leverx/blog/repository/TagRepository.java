package com.leverx.blog.repository;

import com.leverx.blog.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

  Optional<Tag> findByName(String name);


}
