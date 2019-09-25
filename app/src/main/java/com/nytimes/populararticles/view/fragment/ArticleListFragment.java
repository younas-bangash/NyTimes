package com.nytimes.populararticles.view.fragment;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nytimes.populararticles.BR;
import com.nytimes.populararticles.R;
import com.nytimes.populararticles.api.Status;
import com.nytimes.populararticles.api.response.ErrorResponse;
import com.nytimes.populararticles.databinding.FragmentArticleListBinding;
import com.nytimes.populararticles.view.base.BaseFragment;
import com.nytimes.populararticles.viewmodel.ArticleListViewModel;

/**
 * Fragment to display list of characters
 */
public class ArticleListFragment extends BaseFragment<ArticleListViewModel> {
    private FragmentArticleListBinding listDataBinding;

    @Override
    protected Class<ArticleListViewModel> getViewModel() {
        return ArticleListViewModel.class;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.loadPopularArticles(7);
        observeLiveData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(getString(R.string.title_popular_article));
    }

    private void observeLiveData() {

        viewModel.isNetworkCallInProgress.observe(this, aBoolean -> {
            if (Boolean.TRUE.equals(aBoolean)) {
                listDataBinding.itemDataLoading.progressBar.setVisibility(View.VISIBLE);
            } else {
                listDataBinding.itemDataLoading.progressBar.setVisibility(View.GONE);
            }
        });

        viewModel.serviceError.observe(this, this::onErrorResponse);

        viewModel.articleListAdapter.itemCallListener.observe(this, articleEntity -> {
            if (null != articleEntity) {
                ArticleListFragmentDirections.ActionArticleListFragmentToArticleDetailFragment articleListFragmentDirections =
                        ArticleListFragmentDirections.actionArticleListFragmentToArticleDetailFragment(articleEntity.getUrl(),
                                articleEntity.getPublishedDate());
                getNavController().navigate(articleListFragmentDirections);
            }
        });

        viewModel.articleListAdapter.dataLoaded.observe(this, aBoolean -> {
            if (Boolean.TRUE.equals(aBoolean)) {
                viewModel.isNetworkCallInProgress.setValue(false);
            }
        });

        viewModel.getArticleListLiveData().observe(this, listResource -> {
            if (null != listResource && (listResource.status == Status.ERROR || listResource.status == Status.SUCCESS)
                    && listResource.getMessage() != null) {
                showInfoDialog(listResource.getMessage(), getString(R.string.unknownError));
            }

            listDataBinding.setResource(listResource);
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        listDataBinding = ((FragmentArticleListBinding) dataBinding);
        initRecyclerView();
        return dataBinding.getRoot();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        listDataBinding.articleListView.setLayoutManager(layoutManager);
        listDataBinding.articleListView.setAdapter(viewModel.articleListAdapter);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_article_list;
    }

    private void onErrorResponse(ErrorResponse serviceError) {
        showInfoDialog(serviceError.getMessage(), serviceError.getMessage());
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        if (null == getActivity()) {
            return;
        }

        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                onQueryTextChange(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                viewModel.articleListAdapter.getFilter().filter(query);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
