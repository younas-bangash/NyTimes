package com.nytimes.populararticles.di;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.nytimes.populararticles.viewmodel.ArticleDetailsViewModel;
import com.nytimes.populararticles.viewmodel.ArticleListViewModel;
import com.nytimes.populararticles.viewmodel.ViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Inject dependencies via constructor injection
 */
@SuppressWarnings("unused")
@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ArticleListViewModel.class)
    abstract ViewModel bindsCharacterListViewModel(ArticleListViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ArticleDetailsViewModel.class)
    abstract ViewModel bindsArticleDetailsViewModel(ArticleDetailsViewModel viewModel);

    @Binds
    abstract ViewModelProvider.Factory bindsViewModelFactory(ViewModelFactory viewModelFactory);
}
