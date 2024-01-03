package com.wanlab.microapi1.controllers;


import com.wanlab.microapi1.dto.ArticleDto;
import com.wanlab.microapi1.entity.Articles;
import com.wanlab.microapi1.services.ArticlesService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/articles")
public class ArticleController {
    private final ArticlesService articlesService;
    private final ModelMapper modelMapper;

    public ArticleController(ArticlesService articlesService) {
        this.articlesService = articlesService;
        this.modelMapper = new ModelMapper();
    }

    @PostMapping
    public Articles save( @RequestBody ArticleDto articleDto) {
        Articles articles = modelMapper.map(articleDto, Articles.class);
        return articlesService.save(articles);
    }
    @GetMapping
    public List<Articles> findAll() {
        return articlesService.findAll();
    }
    @GetMapping("{id}")
    public Articles findById( @PathVariable String id) {
        return articlesService.findById(Long.valueOf(id));
    }
    @DeleteMapping("{id}")
    public void deleteById(@PathVariable String id) {
        articlesService.deleteById(Long.valueOf(id));
    }
    @PutMapping
    public Articles update(@RequestBody  ArticleDto articleDto ) {
        Articles articles = modelMapper.map(articleDto, Articles.class);
        return articlesService.update(articles);
    }
}
