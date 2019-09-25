package com.nytimes.populararticles.api;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.google.gson.Gson;
import com.google.gson.stream.MalformedJsonException;
import com.nytimes.populararticles.NyTimeApp;
import com.nytimes.populararticles.R;
import com.nytimes.populararticles.api.response.ErrorResponse;

import java.io.IOException;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * This class fetch response from the service or the database
 */

public abstract class NetworkBoundResource<T, V> {
    private final MediatorLiveData<Resource<T>> result = new MediatorLiveData<>();

    @MainThread
    protected NetworkBoundResource() {
        result.setValue(Resource.loading(null));

        // Always load the data from DB initially so that we have
        LiveData<T> dbSource = loadFromDb();

        // Fetch the data from network and add it to the resource
        result.addSource(dbSource, data -> {
            result.removeSource(dbSource);
            if (shouldFetch()) {
                fetchFromNetwork(dbSource);
            } else {
                result.addSource(dbSource, newData -> {
                    if (null != newData)
                        result.setValue(Resource.success(newData));
                });
            }
        });
    }

    /**
     * This method fetches the data from remoted service and save it to local db
     *
     * @param liveDataSource - Database source
     */
    private void fetchFromNetwork(final LiveData<T> liveDataSource) {
        result.addSource(liveDataSource, newData -> result.setValue(Resource.loading(newData)));
        createCall().enqueue(new Callback<V>() {
            @Override
            public void onResponse(@NonNull Call<V> call, @NonNull Response<V> response) {
                result.removeSource(liveDataSource);
                if (response.isSuccessful()) {
                    saveResultAndReInit(response.body());
                } else {
                    handleServiceError(response, liveDataSource);
                }
            }

            @Override
            public void onFailure(@NonNull Call<V> call, @NonNull Throwable t) {
                result.removeSource(liveDataSource);
                result.addSource(liveDataSource, newData -> result.setValue(Resource.error(getCustomErrorMessage(t), newData)));
            }
        });
    }

    private void handleServiceError(@NonNull Response<V> response, final LiveData<T> dbSource) {
        result.removeSource(dbSource);
        try {
            final ErrorResponse errorResponse = new Gson().fromJson(response.errorBody().charStream(), ErrorResponse.class);
            result.setValue(Resource.error(errorResponse.getMessage(), null));
        } catch (Exception ex) {
            result.setValue(Resource.error(getCustomErrorMessage(ex), null));
        }
    }

    private String getCustomErrorMessage(Throwable error) {
        if (error instanceof SocketTimeoutException) {
            return NyTimeApp.getAppContext().getString(R.string.requestTimeOutError);
        } else if (error instanceof MalformedJsonException) {
            return NyTimeApp.getAppContext().getString(R.string.responseMalformedJson);
        } else if (error instanceof IOException) {
            return NyTimeApp.getAppContext().getString(R.string.networkError);
        } else if (error instanceof HttpException) {
            return (((HttpException) error).response().message());
        } else {
            return NyTimeApp.getAppContext().getString(R.string.unknownError);
        }
    }

    @SuppressLint("StaticFieldLeak")
    @MainThread
    private void saveResultAndReInit(V response) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                processServiceResponse(response);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                result.addSource(loadFromDb(), newData -> {
                    if (null != newData)
                        result.setValue(Resource.success(newData));
                });
            }
        }.execute();
    }

    @WorkerThread
    protected abstract void processServiceResponse(V item);

    @MainThread
    private boolean shouldFetch() {
        return true;
    }

    @NonNull
    @MainThread
    protected abstract LiveData<T> loadFromDb();

    @NonNull
    @MainThread
    protected abstract Call<V> createCall();

    public final LiveData<Resource<T>> getAsLiveData() {
        return result;
    }
}
