package com.nytimes.populararticles.api;

import com.nytimes.populararticles.api.response.ArticleEntity;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the {@link Resource}
 */
public class ResourceTest {
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
    public void success() {
        Resource<ArticleEntity> successResource = Resource.success(articleEntity);

        Assert.assertNotNull(successResource);
    }

    @Test
    public void error() {
        Resource<ArticleEntity> errorResource = Resource.error("error", articleEntity);

        Assert.assertNotNull(errorResource);
        Assert.assertEquals("error", errorResource.getMessage());
    }

    @Test
    public void loading() {
        Resource<ArticleEntity> successResource = Resource.loading(articleEntity);

        Assert.assertNotNull(successResource);
    }
}