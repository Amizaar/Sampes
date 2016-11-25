package #.di;

/**
 * Created by Roman Silka on 11/23/16.
 */

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    Context getContext();
}
