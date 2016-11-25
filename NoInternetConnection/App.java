package #

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.orm.SugarContext;

import #.di.AppComponent;
import #.di.AppModule;
import #.di.DaggerAppComponent;

public class App extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
