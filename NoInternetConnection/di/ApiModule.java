package #.di;

import android.content.Context;

import #.api.IChatClientApi;
import #.api.LiveNetworkMonitor;
import #.api.NetworkMonitor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

/**
 * Created by Roman Silka on 11/23/16.
 */

@Module
public class ApiModule {
    private String mBaseUrl;

    public ApiModule(String baseUrl) {
        mBaseUrl = baseUrl;
    }

    @Provides
    @ActivityScope
    NetworkMonitor provideNetworkMonitor(Context context){
        return new LiveNetworkMonitor(context);
    }

    @Provides
    @ActivityScope
    Retrofit provideRetrofit(NetworkMonitor networkMonitor){
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.addInterceptor(chain -> {
            if (networkMonitor.isConnected()) {
                return chain.proceed(chain.request());
            } else {
                throw new NoNetworkException();
            }
        });

        return new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory
                        .createWithScheduler(Schedulers.io()))
                .client(okHttpClientBuilder.build())
                .build();
    }

    @Provides
    @ActivityScope
    IChatClientApi provideChatClientApi(Retrofit retrofit){
        return retrofit.create(IChatClientApi.class);
    }

    @Provides
    @Singleton
    String provideUrl(){
        return mBaseUrl;
    }
}
