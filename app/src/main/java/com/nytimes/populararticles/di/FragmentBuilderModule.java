package com.nytimes.populararticles.di;

import com.nytimes.populararticles.view.fragment.ArticleDetailFragment;
import com.nytimes.populararticles.view.fragment.ArticleListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * This builder provides android injector service to fragments
 */
@SuppressWarnings("unused")
@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract ArticleListFragment contributeArticleListFragment();

    @ContributesAndroidInjector
    abstract ArticleDetailFragment contributeArticleDetailFragment();
}
