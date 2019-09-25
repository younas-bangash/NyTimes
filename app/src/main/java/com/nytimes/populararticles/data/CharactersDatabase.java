package com.nytimes.populararticles.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.nytimes.populararticles.api.response.ArticleEntity;

/**
 * Database class for the Characters
 */
@Database(entities = {ArticleEntity.class}, version = 1)
public abstract class CharactersDatabase extends RoomDatabase {
    public abstract ArticleDao characterDao();
}
