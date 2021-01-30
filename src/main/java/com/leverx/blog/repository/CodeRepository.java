package com.leverx.blog.repository;

public interface CodeRepository {
    void save(String code, String email);

    String findByCode(String code);

    void delete(String code);
}
