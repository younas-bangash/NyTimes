package com.nytimes.populararticles.api.response;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Test class for the {@link ArticleEntity}
 */
public class ArticleEntityTest {
    private ArticleEntity articleEntity;

    @Before
    public void setUp() {
        articleEntity = new ArticleEntity();
    }

    @After
    public void tearDown() {
        articleEntity = null;
    }

    @Test
    public void content() {
        articleEntity.setContent("content");

        Assert.assertEquals("content", articleEntity.getContent());
    }

    @Test
    public void id() {
        articleEntity.setId(100);

        Assert.assertEquals(100, articleEntity.getId());
    }

    @Test
    public void title() {
        articleEntity.setTitle("title");

        Assert.assertEquals("title", articleEntity.getTitle());
    }

    @Test
    public void authors() {
        articleEntity.setAuthors("authors");

        Assert.assertEquals("authors", articleEntity.getAuthors());
    }

    @Test
    public void publishedDate() {
        articleEntity.setPublishedDate("publishedDate");

        Assert.assertEquals("publishedDate", articleEntity.getPublishedDate());
    }

    @Test
    public void url() {
        articleEntity.setUrl("url");

        Assert.assertEquals("url", articleEntity.getUrl());
    }
}