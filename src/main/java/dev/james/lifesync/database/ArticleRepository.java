package dev.james.lifesync.database;

import dev.james.lifesync.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Integer> {

    List<Article> getAllBySection(String section);
}
