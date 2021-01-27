package com.leverx.blog.repository;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface CodeRepository {
  void save(String code, String email);
  String findByCode(String code);
  void delete(String code);
}
