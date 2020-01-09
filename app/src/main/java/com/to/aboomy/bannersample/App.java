package com.to.aboomy.bannersample;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.to.aboomy.bannersample.util.AlertToast;

/**
 * auth aboom
 * date 2019-12-26
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AlertToast.init(this);
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
