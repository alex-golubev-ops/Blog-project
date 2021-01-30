package com.leverx.blog.service;

import com.leverx.blog.entity.Article;
import com.leverx.blog.entity.Tag;
import com.leverx.blog.repository.ArticleRepository;
import com.leverx.blog.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TagService {

    private final TagRepository tagRepository;

    private final ArticleRepository articleRepository;

    public TagService(TagRepository tagRepository, ArticleRepository articleRepository) {
        this.tagRepository = tagRepository;
        this.articleRepository = articleRepository;
    }

    public Set<Tag> parseToTags(String tagsString) {
        String[] tags = tagsString.trim().split(" ");
        Set<Tag> result = new HashSet<>();
        for (String tag : tags) {
            Optional<Tag> tagFromBd = tagRepository.findByName(tag);
            tagFromBd.ifPresentOrElse(result::add, () -> {
                result.add(new Tag(tag));
            });
        }
        return result;
    }

    public String parseToString(Set<Tag> tags) {
        StringBuilder buffer = new StringBuilder();
        for (Tag tag : tags) {
            buffer.append(tag.toString()).append(" ");
        }
        return buffer.toString();
    }

    public void clearGarbage() {
        List<Tag> allTags = tagRepository.findAll();
        for (Tag tag : allTags) {
            List<Article> byTags = articleRepository.findByTags(tag);
            if (byTags.size() == 0) {
                tagRepository.delete(tag);
            }
        }
    }
}
