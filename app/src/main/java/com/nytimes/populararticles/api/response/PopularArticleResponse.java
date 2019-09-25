package com.nytimes.populararticles.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Response class for searching the character
 */
public class PopularArticleResponse {

    @SerializedName("results")
    private List<ArticleEntity> popularArticles;

    /**
     * This method return the list of article entities
     * @return List of entities
     */
    public List<ArticleEntity> getPopularArticles() {
        return popularArticles;
    }

    /**
     * This method sets the article entities
     * @param popularArticles - articleslist
     */
    public void setPopularArticles(List<ArticleEntity> popularArticles) {
        this.popularArticles = popularArticles;
    }
}
