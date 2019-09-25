package com.nytimes.populararticles.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nytimes.populararticles.api.response.ArticleEntity;
import com.nytimes.populararticles.api.response.ResponseListener;
import com.nytimes.populararticles.repository.ArticleRepository;

import javax.inject.Inject;

/**
 * viewModel for the {@link com.nytimes.populararticles.view.fragment.ArticleDetailFragment}
 */
public class ArticleDetailsViewModel extends ViewModel {
    private String url;
    private ArticleRepository articleRepository;
    public final MutableLiveData<ArticleEntity> articleEntityMutableLiveData = new MutableLiveData<>();
    public final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    @Inject
    ArticleDetailsViewModel(ArticleRepository artRepository) {
        this.articleRepository = artRepository;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void loadArticleDetails() {
        if (null != articleRepository) {
            articleRepository.loadArticleDetails(url, new ResponseListener() {
                @Override
                public void onSuccess(ArticleEntity data) {
                    articleEntityMutableLiveData.setValue(data);
                }

                @Override
                public void onFailure(String message) {
                    // Send event to UI to show thw error
                    errorMessage.setValue(message);
                }
            });
        }
    }

    public String getUrl() {
        return url;
    }
}
