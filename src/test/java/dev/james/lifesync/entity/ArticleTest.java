package dev.james.lifesync.entity;

import dev.james.lifesync.constants.Sections;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ArticleTest {

    @Test
    public void testCreateArticle() {
        Article article = new Article("TestName", "https://www.bbc.co.uk/test/article", "Sleep", "test,article");
        assertEquals("TestName", article.getName());
        assertEquals("https://www.bbc.co.uk/test/article", article.getUrl());
        assertEquals(String.valueOf(Sections.Sleep), article.getSection());
        assertEquals(List.of("test", "article"), article.getTags());
        assertEquals("BBC", article.getSource());
    }

    @Test
    public void testNoArgsConstructor() {
        Article article = new Article();

        assertNull(article.getName());
        assertNull(article.getUrl());
        assertNull(article.getSection());

        try {
            article.getTags();
            fail("Tags should be null");
        } catch (NullPointerException ignored) {
            // This is considered the success event
        }

        try {
            article.getSource();
            fail("Source should be null");
        } catch (NullPointerException ignored) {
            // This is considered the success event
        }
    }

    @Test
    public void testGettersAndSetters() {
        Article article = new Article();
        article.setId(-1);
        article.setName("James");
        article.setSection("Sleep");
        article.setTags("j,a,m,e,s");
        article.setUrl("https://www.bbc.co.uk/test");

        assertEquals(-1, article.getId());
        assertEquals("James", article.getName());
        assertEquals("Sleep", article.getSection());
        assertEquals(List.of("j","a","m","e","s"), article.getTags());

        assertEquals("BBC", article.getSource());
    }

    @Test
    public void testEqualsAndHashCode() {
        Article article1 = new Article("James", "https://www.bbc.co.uk/test", "Sleep", "j,a,m,e,s");
        Article article2 = new Article("James", "https://www.bbc.co.uk/test", "Sleep", "j,a,m,e,s");

        assertEquals(article1, article2);
        assertTrue(article1.equals(article2));
        assertTrue(article2.equals(article1));
        assertEquals(article2, article1);
        assertEquals(article1.hashCode(), article2.hashCode());
    }

    @Test
    public void testNotEquals() {
        Article article1 = new Article("James", "https://www.bbc.co.uk/test", "Sleep", "j,a,m,e,s");
        Article article2 = new Article("NotJames", "https://www.bbc.co.uk/test", "Sleep", "j,a,m,e,s");

        assertNotEquals(article1, article2);
        assertNotEquals(article2, article1);
        assertFalse(article1.equals(article2));
        assertFalse(article2.equals(article1));
        assertNotEquals(article1.hashCode(), article2.hashCode());
    }

    @Test
    public void testEqualsWithDifferentObject() {
        Article article = new Article("James", "https://www.bbc.co.uk/test", "Sleep", "j,a,m,e,s");
        String string = "not an article!";

        assertFalse(article.equals(string));
    }

    @Test
    public void testEqualsOnSameObject() {
        Article article = new Article("James", "https://www.bbc.co.uk/test", "Sleep", "j,a,m,e,s");
        assertTrue(article.equals(article));
    }

    @Test
    public void testGetSourceInvalidURLArticle() {
        Article article = new Article();
        article.setUrl("iminvalid");
        assertNull(article.getSource());
    }

}