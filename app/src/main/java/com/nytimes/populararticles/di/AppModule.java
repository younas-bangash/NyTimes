package com.nytimes.populararticles.di;

import android.app.Application;

import androidx.room.Room;

import com.nytimes.populararticles.api.ApiConstants;
import com.nytimes.populararticles.api.ApiService;
import com.nytimes.populararticles.api.RequestInterceptor;
import com.nytimes.populararticles.data.ArticleDao;
import com.nytimes.populararticles.data.CharactersDatabase;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The application module which provides app wide instances of various components
 */
@Module(includes = ViewModelModule.class)
public class AppModule {

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(RequestInterceptor interceptor) {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.connectTimeout(ApiConstants.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
        okHttpClient.readTimeout(ApiConstants.READ_TIMEOUT, TimeUnit.MILLISECONDS);
        okHttpClient.writeTimeout(ApiConstants.WRITE_TIMEOUT, TimeUnit.MILLISECONDS);
        okHttpClient.addInterceptor(interceptor);
        okHttpClient.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        return okHttpClient.build();
    }

    @Provides
    @Singleton
    ApiService provideRetrofit(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.SERVICES_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(ApiService.class);
    }

    @Provides
    @Singleton
    RequestInterceptor provideInterceptor() {
        return new RequestInterceptor();
    }

    @Provides
    @Singleton
    CharactersDatabase provideCharactersDatabase(Application application) {
        return Room.databaseBuilder(application, CharactersDatabase.class, "articleDatabase.db").build();
    }

    @Provides
    @Singleton
    ArticleDao provideArticleDao(CharactersDatabase database) {
        return database.characterDao();
    }
}
