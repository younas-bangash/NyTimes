package com.nytimes.populararticles.api;

import com.nytimes.populararticles.api.response.PopularArticleResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * The APIService interface which will contain the semantics of all the REST calls.
 */
public interface ApiService {
    @GET("svc/mostpopular/v2/mostviewed/all-sections/{index}.json")
    Call<PopularArticleResponse> loadPopularArticles(@Path("index") int index);
}
