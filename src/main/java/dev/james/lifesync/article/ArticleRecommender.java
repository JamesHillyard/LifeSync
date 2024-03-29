package dev.james.lifesync.article;

import dev.james.lifesync.constants.Sections;
import dev.james.lifesync.database.ArticleService;
import dev.james.lifesync.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

//TODO: This should provide intelligent article lists
@Component
public class ArticleRecommender {

    final ArticleService articleService;

    @Autowired
    public ArticleRecommender(ArticleService articleService) {
        this.articleService = articleService;
    }

    public List<Article> getSleepArticles() {
        return articleService.getArticlesForSection(String.valueOf(Sections.Sleep));
    }

    public List<Article> getExerciseArticles() {
        return articleService.getArticlesForSection(String.valueOf(Sections.Exercise));
    }

    public List<Article> getNutritionArticles() {
        return articleService.getArticlesForSection(String.valueOf(Sections.Nutrition));
    }
}
