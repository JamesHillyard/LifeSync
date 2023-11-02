package dev.james.lifesync.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
public class Article {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "url")
    private String url;
    @Basic
    @Column(name = "section")
    private String section;
    @Basic
    @Column(name = "tags")
    private String tags;

    public Article() {

    }

    public Article(String name, String url, String section, String tags) {
        this.name = name;
        this.url = url;
        this.section = section;
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public List<String> getTags() {
        return List.of(tags.split(","));
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return id == article.id && Objects.equals(name, article.name) && Objects.equals(url, article.url) && Objects.equals(section, article.section) && Objects.equals(tags, article.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, url, section, tags);
    }

    // The source is determined from the URL to prevent an additional database field
    public String getSource() {
        // This pattern gets content between two . characters
        Pattern pattern = Pattern.compile("\\.(.*?)\\.");

        Matcher matcher = pattern.matcher(url);

        if (matcher.find()) {
            return matcher.group(1).toUpperCase();
        }
        return null;
    }
}
