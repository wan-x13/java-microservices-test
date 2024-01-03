package com.wanlab.microapi1.repository;

import com.wanlab.microapi1.entity.Articles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Articles, Long> {
}
