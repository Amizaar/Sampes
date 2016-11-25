package #.di;

/**
 * Created by Roman Silka on 11/23/16.
 */

import android.content.Context;

import #.api.IChatClientApi;
import #.login.LoginActivity;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Component(modules = {ApiModule.class}, dependencies = {AppComponent.class})
@ActivityScope
public interface ApiComponent {
    void inject(LoginActivity loginActivity);

    Context getContext();
    Retrofit getRetrofitObject();
    IChatClientApi getChatClientApi();
}
