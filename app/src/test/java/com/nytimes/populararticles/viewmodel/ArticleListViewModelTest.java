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

import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Test class for {@link ArticleListViewModel}
 */
@RunWith(PowerMockRunner.class)
public class ArticleListViewModelTest {
    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Mock
    private ArticleDao articleDao;

    @Mock
    private ApiService apiService;

    private ArticleListViewModel articleListViewModel;

    @Before
    public void setUp() {
        articleListViewModel = new ArticleListViewModel(new ArticleRepository(articleDao, apiService));
    }

    @After
    public void tearDown() {
        articleListViewModel = null;
    }

    @Test
    public void loadPopularArticles() {
        when(articleDao.loadCharacter()).thenReturn(null);

        articleListViewModel.loadPopularArticles(7);

        Assert.assertTrue(articleListViewModel.isNetworkCallInProgress.getValue());

        Assert.assertNull( articleListViewModel.getArticleListLiveData().getValue().data);
    }
}