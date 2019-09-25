package com.nytimes.populararticles.api.response;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Test class for the {@link PopularArticleResponse}
 */
public class PopularArticleResponseTest {
    private PopularArticleResponse popularArticleResponse;

    @Before
    public void setUp() {
        popularArticleResponse = new PopularArticleResponse();
    }

    @After
    public void tearDown() {
        popularArticleResponse = null;
    }

    @Test
    public void popularArticles() {
        popularArticleResponse.setPopularArticles(new ArrayList<>());

        Assert.assertEquals(0, popularArticleResponse.getPopularArticles().size());
    }
}