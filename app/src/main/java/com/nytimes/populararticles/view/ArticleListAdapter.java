package com.nytimes.populararticles.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.nytimes.populararticles.api.Resource;
import com.nytimes.populararticles.api.response.ArticleEntity;
import com.nytimes.populararticles.databinding.ArticleListItemBinding;
import com.nytimes.populararticles.view.base.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * List adapter for character list
 */
public class ArticleListAdapter extends BaseAdapter<RecyclerView.ViewHolder, ArticleEntity> {
    private List<ArticleEntity> articleEntitiesFiltered = new ArrayList<>();
    private List<ArticleEntity> articleEntities = new ArrayList<>();

    @Override
    public int getItemCount() {
        return articleEntitiesFiltered.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ArticleListItemBinding itemBinding = ArticleListItemBinding.inflate(layoutInflater, parent, false);
        return new CharacterItemViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CharacterItemViewHolder characterItemViewHolder = ((CharacterItemViewHolder) holder);
        ArticleEntity articleEntity = articleEntities.get(position);
        characterItemViewHolder.bindView(articleEntity);
    }

    @Override
    public void setData(@NonNull List<ArticleEntity> entities) {
        this.articleEntities = entities;
        this.articleEntitiesFiltered = entities;
        notifyDataSetChanged();
    }

    public class CharacterItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ArticleListItemBinding binding;

        public CharacterItemViewHolder(ArticleListItemBinding binding) {
            super(binding.getRoot());
            itemView.setOnClickListener(this);
            this.binding = binding;
        }

        public void bindView(ArticleEntity item) {
            binding.setItem(item);
        }

        @Override
        public void onClick(View view) {
            itemCallListener.setValue(binding.getItem());
        }
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(@NonNull CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    articleEntitiesFiltered = articleEntities;
                } else {
                    List<ArticleEntity> filteredList = new ArrayList<>();
                    for (ArticleEntity row : articleEntities) {
                        // name match condition. this might differ depending on your requirement
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase())
                                || row.getAuthors().toLowerCase().contains(charString.toLowerCase())
                                || row.getPublishedDate().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    articleEntitiesFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = articleEntitiesFiltered;

                return filterResults;
            }

            @Override
            protected void publishResults(@NonNull CharSequence charSequence, @NonNull FilterResults filterResults) {
                //noinspection unchecked
                articleEntitiesFiltered = (ArrayList<ArticleEntity>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @SuppressWarnings("unchecked")
    @BindingAdapter(value = "resource")
    public static void setResource(RecyclerView recyclerView, Resource resource) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();

        if (adapter == null) {
            return;
        }

        if (resource == null || resource.data == null) {
            return;
        }

        if (adapter instanceof BaseAdapter) {
            List list = (List) resource.data;
            if (!list.isEmpty()) {
                ((BaseAdapter) adapter).dataLoaded.setValue(true);
            }
            ((BaseAdapter) adapter).setData((List) resource.data);
        }
    }
}
