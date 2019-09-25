package com.nytimes.populararticles.api.response;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the {@link ResponseListener}
 */
public class ResponseListenerTest {
    private ResponseListener responseListener;
    private ArticleEntity articleEntity;

    @Before
    public void setUp() {
        responseListener = new ResponseListener() {
            @Override
            public void onSuccess(ArticleEntity data) {
                articleEntity = data;
            }

            @Override
            public void onFailure(String message) {
                articleEntity = null;
            }
        };
    }

    @After
    public void tearDown() {
        responseListener = null;
    }

    @Test
    public void interfaceMethod() {
        responseListener.onSuccess(new ArticleEntity());

        Assert.assertNotNull(articleEntity);

        responseListener.onFailure("error");

        Assert.assertNull(articleEntity);
    }
}