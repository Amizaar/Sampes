package com.coprorated.amizaar.myarchitecturedtest.di;

import android.app.Application;

import com.coprorated.amizaar.myarchitecturedtest.App;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Created by amizaar on 03.07.2017.
 */

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AppModule.class,
        MainActivityModule.class
})
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance Builder application(Application application);
        AppComponent build();
    }
    void inject(App application);
}
