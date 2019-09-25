package com.nytimes.populararticles.api.response;

/**
 * Interface for the article details listener
 */
public interface ResponseListener {
    void onSuccess(ArticleEntity data);

    void onFailure(String message);
}
