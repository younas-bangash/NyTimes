package com.nytimes.populararticles.repository;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.nytimes.populararticles.api.ApiService;
import com.nytimes.populararticles.api.NetworkBoundResource;
import com.nytimes.populararticles.api.Resource;
import com.nytimes.populararticles.api.response.ArticleEntity;
import com.nytimes.populararticles.api.response.PopularArticleResponse;
import com.nytimes.populararticles.api.response.ResponseListener;
import com.nytimes.populararticles.data.ArticleDao;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;

/**
 * Repository that is used for character list
 */
public class ArticleRepository {
    private final ArticleDao articleDao;
    private final ApiService apiService;

    @Inject
    public ArticleRepository(ArticleDao dao, ApiService service) {
        this.articleDao = dao;
        this.apiService = service;
    }

    /**
     *  loadPopularArticles from the db/network
     * @param index
     * @return
     */
    public LiveData<Resource<List<ArticleEntity>>> loadPopularArticles(int index) {
        return new NetworkBoundResource<List<ArticleEntity>, PopularArticleResponse>() {

            @Override
            protected void processServiceResponse(PopularArticleResponse response) {
                if (null != response) {
                    articleDao.saveCharacter(response.getPopularArticles());
                }
            }

            @NonNull
            @Override
            protected LiveData<List<ArticleEntity>> loadFromDb() {
                return articleDao.loadCharacter();
            }

            @NonNull
            @Override
            protected Call<PopularArticleResponse> createCall() {
                return apiService.loadPopularArticles(index);
            }

        }.getAsLiveData();
    }

    /**
     * This method fetches the HTML content from the url and parses it and fills the model
     * @param url url to be fetched
     * @param responseListener callback
     */
    @SuppressLint("CheckResult")
    public void loadArticleDetails(@NonNull String url, @NonNull ResponseListener responseListener) {
        ArticleEntity articleDetails = new ArticleEntity();
        Observable.fromCallable(() -> {
            Document document = Jsoup.connect(url).get();
            articleDetails.setTitle(document.title());
            articleDetails.setContent(document.select("p").text());
            return false;
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> responseListener.onSuccess(articleDetails),
                        (error -> responseListener.onFailure(error.getMessage())));

    }
}
