package com.wanlab.microapi1.repository;

import com.wanlab.microapi1.entity.Articles;

import java.util.List;

public interface ArticlePort {
    List<Articles> findAll();
    Articles findById(Long id);
    Articles save(Articles article);
    void deleteById(Long id);
    Articles update(Articles article);
}
