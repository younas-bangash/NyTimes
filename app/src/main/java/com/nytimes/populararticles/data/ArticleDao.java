package com.nytimes.populararticles.data;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.nytimes.populararticles.api.response.ArticleEntity;

import java.util.List;


/**
 * Dao class fro reading data from database
 */

@Dao
public interface ArticleDao {

    @Query("SELECT * FROM articles")
    LiveData<List<ArticleEntity>> loadCharacter();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveCharacter(List<ArticleEntity> articleEntityList);
}
