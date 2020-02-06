package com.to.aboomy.bannersample;

import android.app.Activity;
import android.app.Application;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.Utils;
import com.squareup.leakcanary.LeakCanary;
import com.to.aboomy.statusbar_lib.StatusBarUtil;

/**
 * auth aboom
 * date 2019-12-26
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        initLeakCanary(this);

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                StatusBarUtil.setStatusBarColor(activity, Color.WHITE);
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {

            }
        });
    }

    private void initLeakCanary(Application application) {
        /*---------------  内存泄漏的检测 ---------------*/
        if (!BuildConfig.DEBUG || LeakCanary.isInAnalyzerProcess(application)) {
            return;
        }
        LeakCanary.install(application);
    }

}
