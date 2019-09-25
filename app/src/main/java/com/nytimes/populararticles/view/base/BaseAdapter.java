package com.nytimes.populararticles.view.base;

import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/***
 * Base class for RecyclerView adapter
 */

public abstract class BaseAdapter<T extends RecyclerView.ViewHolder, D> extends RecyclerView.Adapter<T> {
    public final MutableLiveData<D> itemCallListener = new MutableLiveData<>();
    public final MutableLiveData<Boolean> dataLoaded = new MutableLiveData<>();

    public abstract void setData(List<D> data);
}
