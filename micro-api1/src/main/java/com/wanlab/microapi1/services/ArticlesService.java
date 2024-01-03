package com.wanlab.microapi1.services;

import com.wanlab.microapi1.entity.Articles;
import com.wanlab.microapi1.exception.ConflictException;
import com.wanlab.microapi1.exception.NotFoundException;
import com.wanlab.microapi1.repository.ArticlePort;
import com.wanlab.microapi1.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ArticlesService implements ArticlePort {

    private final ArticleRepository articleRepository;

    public ArticlesService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }
    @Override
    public List<Articles> findAll() {
        return articleRepository.findAll();
    }

    @Override
    public Articles findById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Article not found with id: " + id));
    }

    @Override
    public Articles save(Articles article) {
        if(articleRepository.existsById(article.getId())) {
            throw new ConflictException("Article already exists with id: " + article.getId());
        }
        return articleRepository.save(article);
    }

    @Override
    public void deleteById(Long id) {
        if(!articleRepository.existsById(id)) {
            throw new NotFoundException("Article not found with id: " + id);
        }
        articleRepository.deleteById(id);

    }

    @Override
    public Articles update(Articles article) {
        if(!articleRepository.existsById(article.getId())) {
            throw new NotFoundException("Article not found with id: " + article.getId());
        }
        return articleRepository.save(article);
    }
}
