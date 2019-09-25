package com.nytimes.populararticles.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nytimes.populararticles.api.Resource;
import com.nytimes.populararticles.api.response.ArticleEntity;
import com.nytimes.populararticles.api.response.ErrorResponse;
import com.nytimes.populararticles.repository.ArticleRepository;
import com.nytimes.populararticles.view.ArticleListAdapter;

import java.util.List;

import javax.inject.Inject;

/**
 * ViewModel for the CharacterListFragment
 */

public class ArticleListViewModel extends ViewModel {
    public final MutableLiveData<Boolean> isNetworkCallInProgress = new MutableLiveData<>();
    public final ArticleListAdapter articleListAdapter = new ArticleListAdapter();
    private LiveData<Resource<List<ArticleEntity>>> articleList;
    public final MutableLiveData<ErrorResponse> serviceError = new MutableLiveData<>();
    private ArticleRepository repository;

    @Inject
    public ArticleListViewModel(ArticleRepository repository) {
        this.repository = repository;
        isNetworkCallInProgress.setValue(false);
    }

    public void loadPopularArticles(int index){
        isNetworkCallInProgress.setValue(true);
        articleList = repository.loadPopularArticles(index);
    }

    public LiveData<Resource<List<ArticleEntity>>> getArticleListLiveData() {
        return articleList;
    }
}
