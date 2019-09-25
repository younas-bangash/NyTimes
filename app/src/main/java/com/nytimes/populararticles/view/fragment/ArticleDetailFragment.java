package com.nytimes.populararticles.view.fragment;


import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.nytimes.populararticles.BR;
import com.nytimes.populararticles.R;
import com.nytimes.populararticles.databinding.FragmentArticleDetailBinding;
import com.nytimes.populararticles.view.base.BaseFragment;
import com.nytimes.populararticles.viewmodel.ArticleDetailsViewModel;

/**
 * The article list fragment which is responsible for showing the article details
 */
public class ArticleDetailFragment extends BaseFragment<ArticleDetailsViewModel> {

    @Override
    protected Class<ArticleDetailsViewModel> getViewModel() {
        return ArticleDetailsViewModel.class;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_article_detail;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(getString(R.string.article_detils));
        FragmentArticleDetailBinding fragmentArticleListBinding = (FragmentArticleDetailBinding) dataBinding;

        viewModel.setUrl(ArticleDetailFragmentArgs.fromBundle(getArguments()).getArticleUrl());
        viewModel.loadArticleDetails();

        viewModel.articleEntityMutableLiveData.observe(this, articleEntity -> {
            if (null != articleEntity) {
                fragmentArticleListBinding.textPublishedDate.setText(ArticleDetailFragmentArgs.fromBundle(getArguments()).getArticlePublishDate());
                fragmentArticleListBinding.loadingProgress.setVisibility(View.GONE);
            }
        });

        viewModel.errorMessage.observe(this, message -> {
            fragmentArticleListBinding.loadingProgress.setVisibility(View.GONE);
            if (message == null && getActivity() != null) {
                fragmentArticleListBinding.textContent.setText(getActivity().getString(R.string.networkError));
            } else {
                fragmentArticleListBinding.textContent.setText(message);
            }
        });
    }
}
