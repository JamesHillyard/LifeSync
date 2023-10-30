package dev.james.lifesync.dao;

import dev.james.lifesync.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleDAO extends JpaRepository<Article, Integer> {

    List<Article> getAllBySection(String section);
}
