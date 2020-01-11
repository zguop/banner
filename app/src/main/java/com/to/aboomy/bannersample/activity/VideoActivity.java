package com.to.aboomy.bannersample.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.kk.taurus.playerbase.assist.RelationAssist;
import com.kk.taurus.playerbase.entity.DataSource;
import com.kk.taurus.playerbase.receiver.ReceiverGroup;
import com.kk.taurus.playerbase.render.AspectRatio;
import com.to.aboomy.banner.Banner;
import com.to.aboomy.banner.HolderCreator;
import com.to.aboomy.bannersample.R;
import com.to.aboomy.bannersample.bean.VideoBean;
import com.to.aboomy.bannersample.util.LoadingCover;
import com.to.aboomy.bannersample.util.PauseCover;
import com.to.aboomy.statusbar_lib.StatusBarUtil;

import java.util.List;

/**
 * auth aboom
 * date 2020-01-11
 */
public class VideoActivity extends AppCompatActivity implements HolderCreator , ViewPager.OnPageChangeListener {

    public static final String VIDEO_URL_01 = "http://jiajunhui.cn/video/kaipao.mp4";
    public static final String VIDEO_URL_02 = "http://jiajunhui.cn/video/kongchengji.mp4";
    public static final String VIDEO_URL_03 = "http://jiajunhui.cn/video/allsharestar.mp4";
    public static final String VIDEO_URL_04 = "http://jiajunhui.cn/video/big_buck_bunny.mp4";
    public static final String VIDEO_URL_05 = "http://jiajunhui.cn/video/trailer.mp4";
    private RelationAssist relationAssist;
    private Banner banner;
    private List<VideoBean> videoList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        StatusBarUtil.setStatusBarColor(this, Color.WHITE);

        banner = findViewById(R.id.banner);
        videoList = com.to.aboomy.bannersample.util.Utils.getVideoList();
        banner.setHolderCreator(this)
                .setAutoPlay(false)
                .setOuterPageChangeListener(this)
                .setPages(videoList);



        relationAssist = new RelationAssist(Utils.getApp());
        relationAssist.setAspectRatio(AspectRatio.AspectRatio_FILL_WIDTH);
        ReceiverGroup receiverGroup = new ReceiverGroup(null);
        receiverGroup.addReceiver("loading",new LoadingCover(this));
        receiverGroup.addReceiver("pause",new PauseCover(this));
        relationAssist.setReceiverGroup(receiverGroup);
    }


    @Override
    public View createView(Context context, final int index, Object o) {
        VideoBean videoBean = (VideoBean) o;
        View inflate = View.inflate(context, R.layout.item_video, null);
        ImageView imageView = inflate.findViewById(R.id.albumImage);
        Glide.with(imageView).load(videoBean.getCover()).into(imageView);
        FrameLayout playerContainer = inflate.findViewById(R.id.playerContainer);
        playerContainer.setTag(videoBean.getPath());
        playerContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPosition(index);
            }
        });

        return inflate;
    }

    private void playPosition(int position) {
        VideoBean videoBean = videoList.get(position);
        FrameLayout container = banner.findViewWithTag(videoBean.getPath());
        relationAssist.attachContainer(container, true);
        relationAssist.setDataSource(new DataSource(videoBean.getPath()));
        relationAssist.play();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        relationAssist.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        relationAssist.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        relationAssist.destroy();
    }
}
