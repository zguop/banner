package com.to.aboomy.bannersample;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.kk.taurus.playerbase.config.PlayerConfig;
import com.kk.taurus.playerbase.config.PlayerLibrary;
import com.squareup.leakcanary.LeakCanary;

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

        //初始化播放器
        PlayerConfig.setUseDefaultNetworkEventProducer(true);
        PlayerLibrary.init(this);
    }

    private void initLeakCanary(Application application) {
        /*---------------  内存泄漏的检测 ---------------*/
        if (!BuildConfig.DEBUG || LeakCanary.isInAnalyzerProcess(application)) {
            return;
        }
        LeakCanary.install(application);
    }

}
