package com.to.aboomy.bannersample.util;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.blankj.utilcode.util.SizeUtils;
import com.kk.taurus.playerbase.event.OnPlayerEventListener;
import com.kk.taurus.playerbase.receiver.BaseCover;
import com.kk.taurus.playerbase.touch.OnTouchGestureListener;
import com.to.aboomy.bannersample.R;

/**
 * auth aboom
 * date 2019-11-13
 */
public class PauseCover extends BaseCover implements OnTouchGestureListener {

    private boolean isRenderStart;
    public static final int ON_PAUSE = 101;
    public static final int RENDER_START = 102;

    public PauseCover(Context context) {
        super(context);
        getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestResume(null);
                setStartState(false);
            }
        });
    }

    @Override
    protected View onCreateCoverView(Context context) {
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setVisibility(View.GONE);
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(R.mipmap.ic_video_start);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(SizeUtils.dp2px(56f), SizeUtils.dp2px(56f));
        params.gravity = Gravity.CENTER;
        frameLayout.addView(imageView, params);
        return frameLayout;
    }

    @Override
    public void onPlayerEvent(int eventCode, Bundle bundle) {
        if (OnPlayerEventListener.PLAYER_EVENT_ON_START == eventCode) {
            setStartState(false);
        }
        // 播放器开始渲染视频流时
        if (OnPlayerEventListener.PLAYER_EVENT_ON_VIDEO_RENDER_START == eventCode) {
            isRenderStart = true;
            notifyReceiverEvent(RENDER_START, null);
        }
        // 接收到播放停止的事件
        if (OnPlayerEventListener.PLAYER_EVENT_ON_STOP == eventCode) {
            isRenderStart = false;
        }
    }

    @Override
    public void onErrorEvent(int eventCode, Bundle bundle) {

    }

    @Override
    public void onReceiverEvent(int eventCode, Bundle bundle) {

    }

    @Override
    public void onSingleTapUp(MotionEvent event) {
        if (!isRenderStart) {
            return;
        }
        requestPause(null);
        setStartState(true);
        notifyReceiverEvent(ON_PAUSE, null);
    }

    @Override
    public void onDoubleTap(MotionEvent event) {

    }

    @Override
    public void onDown(MotionEvent event) {

    }

    @Override
    public void onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

    }

    @Override
    public void onEndGesture() {

    }

    private void setStartState(boolean show) {
        setCoverVisibility(show ? View.VISIBLE : View.GONE);
    }

    public int getCoverLevel() {
        return levelMedium(3);
    }

}
