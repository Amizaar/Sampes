package com.coprorated.amizaar.myarchitecturedtest.di;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.coprorated.amizaar.myarchitecturedtest.BuildConfig;
import com.coprorated.amizaar.myarchitecturedtest.db.ImageDao;
import com.coprorated.amizaar.myarchitecturedtest.db.ImageDb;
import com.coprorated.amizaar.myarchitecturedtest.api.ImageApiService;
import com.coprorated.amizaar.myarchitecturedtest.util.LiveDataCallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by amizaar on 03.07.2017.
 */

@Module(includes = ViewModelModule.class)
class AppModule {
//    private Context mContext;

/*    public AppModule(Context mContext) {
        this.mContext = mContext;
    }

    @Singleton @Provides
    Context getContext() {
        return mContext;
    }*/

    @Singleton @Provides
    Retrofit provideRetrofit() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttpClientBuilder
                .addInterceptor(logInterceptor)
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    HttpUrl url = request.url().newBuilder()
                            .addQueryParameter("key", BuildConfig.PIXABEY_API_KEY)
//                            .addQueryParameter("image_type", "photo")
                            .build();
                    request = request.newBuilder().url(url).build();
                    return chain.proceed(request);
                });


        return new Retrofit.Builder()
                .baseUrl(BuildConfig.PIXABEY_API)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .client(okHttpClientBuilder.build())
                .build();
    }

    @Singleton @Provides
    ImageApiService provideImageApiService(Retrofit retrofit) {
        return retrofit.create(ImageApiService.class);
    }

    @Singleton @Provides
    ImageDb provideDb(Application application) {
        return Room.databaseBuilder(application, ImageDb.class, "images.db").build();
    }

    @Singleton @Provides
    ImageDao provideImageDao(ImageDb database) {
        return database.imageDao();
    }

}
