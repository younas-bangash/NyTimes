package com.nytimes.populararticles.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.nytimes.populararticles.api.ApiService;
import com.nytimes.populararticles.data.ArticleDao;
import com.nytimes.populararticles.repository.ArticleRepository;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Test class for {@link ArticleDetailsViewModel}
 */
@RunWith(PowerMockRunner.class)
public class ArticleDetailsViewModelTest {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Mock
    private ArticleDao articleDao;
    @Mock
    private ApiService apiService;
    private ArticleDetailsViewModel articleDetailsViewModel;

    @Before
    public void setUp(){
        articleDetailsViewModel = new ArticleDetailsViewModel(new ArticleRepository(articleDao, apiService));

    }

    @After
    public void tearDown() {
        articleDetailsViewModel = null;
    }

    @Test
    public void loadArticleDetails() {
        articleDetailsViewModel.setUrl("articleDetailUrl");

        Assert.assertEquals("articleDetailUrl", articleDetailsViewModel.getUrl());
    }
}