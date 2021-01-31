package com.leverx.blog.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    @ManyToMany
    @JoinTable(name = "tags_articles",
            joinColumns = @JoinColumn(name = "tag_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "article_id",
                    referencedColumnName = "id"))
    private Set<Article> articles = new HashSet<>();

    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(id, tag.id) &&
                Objects.equals(name, tag.name) &&
                Objects.equals(articles, tag.articles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, articles);
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    @Override
    public String toString() {
        return name;
    }
}
