package com.to.aboomy.bannersample;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * auth aboom
 * date 2019-12-26
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initLeakCanary(this);
    }

    private void initLeakCanary(Application application) {
        /*---------------  内存泄漏的检测 ---------------*/
        if (!BuildConfig.DEBUG || LeakCanary.isInAnalyzerProcess(application)) {
            return;
        }
        LeakCanary.install(application);
    }

}
